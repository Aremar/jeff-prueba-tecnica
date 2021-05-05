package jeff.technical.test.recommendationservice.controller;

import jeff.technical.test.recommendationservice.common.exceptions.NotFoundException;
import jeff.technical.test.recommendationservice.model.dto.Recommendation;
import jeff.technical.test.recommendationservice.model.dto.TailoredProduct;
import jeff.technical.test.recommendationservice.service.RecommendationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecommendationController.class)
public class RecommendationControllerTests {

    @MockBean
    RecommendationService service;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void shouldGetRecommendationSuccessResponse() throws Exception {
        Mockito.when(service.getRecommendedProductsForUser(1, Optional.empty(), Optional.empty())).thenReturn(getMockRecommendation());
        mockMvc.perform(get("/recommend/1")).andExpect(status().isOk()).andExpect(content().string(containsString("{\"clientId\":1,\"recommendedProducts\":[{\"id\":1,\"vertical\":\"Fit\",\"product\":\"HIIT class\",\"productType\":\"Service\",\"price\":4.99}]}")));
    }

    @Test
    public void shouldGetRecommendationNotFoundResponse() throws Exception {
        Mockito.when(service.getRecommendedProductsForUser(1, Optional.empty(), Optional.empty())).thenThrow(new NotFoundException("So lonely here alone..."));
        mockMvc.perform(get("/recommend/1")).andExpect(status().is4xxClientError()).andExpect(content().string(containsString("\"status\":404,\"error\":\"Not Found\",\"message\":\"So lonely here alone...\",\"path\":\"/recommended/1\"}")));
    }

    @Test
    public void shouldGetRecommendationErrorResponse() throws Exception {
        Mockito.when(service.getRecommendedProductsForUser(1, Optional.empty(), Optional.empty())).thenThrow(new RuntimeException("Everything falls apart"));
        mockMvc.perform(get("/recommend/1")).andExpect(status().is5xxServerError()).andExpect(content().string(containsString("\"status\":500,\"error\":\"Internal Server Error\",\"message\":\"Everything falls apart\",\"path\":\"/recommended/1\"}")));
    }

    private Recommendation getMockRecommendation() {
        return new Recommendation(1, List.of(
                new TailoredProduct(1, "Fit", "HIIT class", "Service", BigDecimal.valueOf(4.99))
        ));
    }
}
