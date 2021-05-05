package jeff.technical.test.recommendationservice.customerservice;

import jeff.technical.test.recommendationservice.common.exceptions.NotFoundException;
import jeff.technical.test.recommendationservice.customerservice.service.CustomerServiceRestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.matchers.TimeToLive;
import org.mockserver.matchers.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {7076})
@SpringBootTest
public class CustomerServiceIntegrationTests {

    @Autowired
    CustomerServiceRestClient restClient;

    private MockServerClient client;

    @BeforeEach
    public void init(MockServerClient client) {
        this.client = client;
    }

    @Test
    public void shouldRetrieveCustomerData() throws IOException {
        createExpectationsForCustomerServiceEndpoint();
        assertEquals("Lawerence Marquardt II", restClient.getCustomerInformationById(1).name());
    }

    @Test
    public void shouldRetrieveNoCustomerData() throws IOException {
        createExpectationsForCustomerNotFound();
        assertThrows(NotFoundException.class, () -> {
            restClient.getCustomerInformationById(1);
        });
    }

    private void createExpectationsForCustomerNotFound() throws IOException {
        client
            .when(
                    request()
                            .withMethod("GET")
                            .withPath("/customers/1"),
                    Times.exactly(1),
                    TimeToLive.exactly(TimeUnit.SECONDS, 60L),
                    1
            )
            .respond(
                    response()
                            .withStatusCode(404)
                            .withBody(new String (Files.readAllBytes(ResourceUtils.getFile("classpath:jsonfiles/CustomerService404.json").toPath())))
            );

    }

    private void createExpectationsForCustomerServiceEndpoint() throws IOException {
        client
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/customers/1"),
                                Times.exactly(1),
                                TimeToLive.exactly(TimeUnit.SECONDS, 60L),
                                1
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody(new String (Files.readAllBytes(ResourceUtils.getFile("classpath:jsonfiles/CustomerService200.json").toPath())))
                );

        client
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/customers/1"),
                        Times.exactly(1),
                        TimeToLive.exactly(TimeUnit.SECONDS, 60L),
                        2
                )
                .respond(
                        response()

                                .withStatusCode(500)
                                .withReasonPhrase("Totally intentional error")
                );
    }
}
