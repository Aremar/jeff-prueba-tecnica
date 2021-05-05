package jeff.technical.test.recommendationservice.productscoreservice;

import jeff.technical.test.recommendationservice.productscoreservice.impl.ProductScoreServiceImpl;
import jeff.technical.test.recommendationservice.productscoreservice.model.entity.ProductScoresEntity;
import jeff.technical.test.recommendationservice.productscoreservice.model.repository.ProductScoresRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {ProductScoreServiceImpl.class})
public class ProductScoreServiceTests {

    @MockBean
    ProductScoresRepository repository;

    @InjectMocks
    ProductScoreServiceImpl serviceImpl;

    @Test
    public void shouldGetRecommendedProductScoreIds(){

        Mockito.when(repository.findByPersona(Mockito.anyString())).thenReturn(List.of(new ProductScoresEntity(1, "JUNIOR", 6), new ProductScoresEntity(1, "JUNIOR", 9)));

        assertEquals(2, serviceImpl.getProductAndScoresForPersona("JUNIOR").size());

    }

}
