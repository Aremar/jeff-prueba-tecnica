package jeff.technical.test.recommendationservice.model.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.math.BigDecimal;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record TailoredProduct(Integer id, String vertical, String product, String productType, BigDecimal price) {
}
