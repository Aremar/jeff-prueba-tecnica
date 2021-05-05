package jeff.technical.test.recommendationservice.productscoreservice;

import jeff.technical.test.recommendationservice.productscoreservice.service.ProductScoreRestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.matchers.TimeToLive;
import org.mockserver.matchers.Times;
import org.mockserver.model.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {7077})
@SpringBootTest
public class ProductScoreServiceIntegrationTests {

    @Autowired
    ProductScoreRestClient productScoreRestClient;

    private MockServerClient client;

    @BeforeEach
    public void init(MockServerClient client) {
        this.client = client;
    }

    @Test
    public void shouldRetrieveJuniorProductScores() throws IOException {

        createExpectationsForProductScoreServiceEndpoint();

        assertEquals(9, productScoreRestClient.getProductScoreByIdAndPersona(1, "JUNIOR").score());

    }


    private void createExpectationsForProductScoreServiceEndpoint() throws IOException {
        client
            .when(
                request()
                    .withMethod("GET")
                    .withPath("/products/1/score")
                    .withQueryStringParameters(Parameter.param("persona", "JUNIOR")),
                    Times.exactly(1),
                    TimeToLive.exactly(TimeUnit.SECONDS, 60L),
                    1
            )
            .respond(
                response()
                    .withStatusCode(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(new String (Files.readAllBytes(ResourceUtils.getFile("classpath:jsonfiles/ProductScoreServiceByProduct200.json").toPath())))
            );

        client
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/products/1/score")
                                .withQueryStringParameters(Parameter.param("persona", "JUNIOR")),
                        Times.exactly(1),
                        TimeToLive.exactly(TimeUnit.SECONDS, 60L),
                        2
                        )
                .respond(
                        response()
                                .withStatusCode(500)
                                .withReasonPhrase("Totally intentional error")
                );

        client
            .when(
                request()
                    .withMethod("GET")
                    .withPath("/scores"),
                    Times.exactly(1),
                    TimeToLive.exactly(TimeUnit.SECONDS, 60L),
                    1
            )
            .respond(
                response()
                    .withStatusCode(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(new String (Files.readAllBytes(ResourceUtils.getFile("classpath:jsonfiles/ProductScoreService200.json").toPath())))
            );
        client
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/scores"),
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
