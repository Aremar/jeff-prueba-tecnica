package jeff.technical.test.recommendationservice.model.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record Recommendation(Integer clientId, List<TailoredProduct> recommendedProducts) {
}
