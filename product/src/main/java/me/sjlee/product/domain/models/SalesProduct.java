package me.sjlee.product.domain.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@EqualsAndHashCode(of = "id")
public class SalesProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_product_id")
    private Long id;

    @Column(name = "sales_product_name")
    private String name;

    @Column(name = "seller_id")
    private Long sellerId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "sales_product_id")
    private List<SalesOption> salesOptions;

    protected SalesProduct() {
    }

    public SalesProduct(String name, Long sellerId) {
        this.name = name;
        this.sellerId = sellerId;
    }
}
