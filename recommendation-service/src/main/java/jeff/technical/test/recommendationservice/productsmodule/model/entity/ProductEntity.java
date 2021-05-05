package jeff.technical.test.recommendationservice.productsmodule.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name="T_PRODUCTS_TPROD")
public class ProductEntity {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "VERTICAL")
    private String vertical;

    @Column(name = "PRODUCT")
    private String product;

    @Column(name = "PRODUCT_TYPE")
    private String productType;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "MARGIN")
    private BigDecimal margin;
}
