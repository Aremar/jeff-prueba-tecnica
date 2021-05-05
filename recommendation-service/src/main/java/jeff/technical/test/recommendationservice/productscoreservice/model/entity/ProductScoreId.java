package jeff.technical.test.recommendationservice.productscoreservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductScoreId implements Serializable {

    Integer productId;

    String persona;
}
