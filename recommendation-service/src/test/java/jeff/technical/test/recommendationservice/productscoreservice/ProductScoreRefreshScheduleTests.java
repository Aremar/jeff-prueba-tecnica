package jeff.technical.test.recommendationservice.productscoreservice;

import jeff.technical.test.recommendationservice.productscoreservice.model.dto.ScoresApiResponse;
import jeff.technical.test.recommendationservice.productscoreservice.model.entity.ProductScoreId;
import jeff.technical.test.recommendationservice.productscoreservice.model.entity.ProductScoresEntity;
import jeff.technical.test.recommendationservice.productscoreservice.model.repository.ProductScoresRepository;
import jeff.technical.test.recommendationservice.productscoreservice.service.ProductScoreRestClient;
import jeff.technical.test.recommendationservice.productscoreservice.service.ProductScoresRefreshScheduler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {ProductScoresRefreshScheduler.class})
public class ProductScoreRefreshScheduleTests {

    @MockBean
    ProductScoreRestClient restClient;

    @MockBean
    ProductScoresRepository repository;

    @InjectMocks
    ProductScoresRefreshScheduler scheduler;

    @Test
    public void shouldDoRefresh() {
        ArgumentCaptor<ProductScoresEntity> captor = ArgumentCaptor.forClass(ProductScoresEntity.class);

        Mockito.when(restClient.getAllProductScores()).thenReturn(List.of(new ScoresApiResponse(1, "JUNIOR", 8), new ScoresApiResponse(1, "SENIOR", 7)));

        Mockito.when(repository.findById(new ProductScoreId(1, "JUNIOR"))).thenReturn(Optional.of(new ProductScoresEntity(1, "JUNIOR", 4)));
        Mockito.when(repository.findById(new ProductScoreId(1, "SENIOR"))).thenReturn(Optional.empty());

        Mockito.when(repository.save(captor.capture())).thenReturn(new ProductScoresEntity());

        scheduler.refreshProductScoresData();

        assertThat(captor.getAllValues(), containsInAnyOrder(
                hasProperty("score", is(8)),
                hasProperty("score", is(7))
        ));

    }
}
