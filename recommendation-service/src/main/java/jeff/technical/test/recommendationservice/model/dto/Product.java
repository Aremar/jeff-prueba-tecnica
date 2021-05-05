package jeff.technical.test.recommendationservice.model.dto;

import java.math.BigDecimal;

public record Product(Integer id, String vertical, String product, String productType, BigDecimal price, BigDecimal margin) {
}
