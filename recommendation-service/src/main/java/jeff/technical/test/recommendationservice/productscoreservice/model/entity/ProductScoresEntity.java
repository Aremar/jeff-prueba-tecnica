package jeff.technical.test.recommendationservice.productscoreservice.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@IdClass(ProductScoreId.class)
@Table(name="T_PRODUCT_SCORES_PERSONA_TPSP")
public class ProductScoresEntity {

    @Id
    @Column(name = "ID")
    Integer productId;

    @Id
    @Column(name = "PERSONA")
    String persona;

    @Column(name = "SCORE")
    Integer score;
}
