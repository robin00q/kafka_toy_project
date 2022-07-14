package me.sjlee.product.infra.out.persistence.sales_product.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sjlee.product.domain.models.SalesOption;
import me.sjlee.product.domain.models.SalesProduct;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class SalesProductDataModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_product_id")
    private Long id;

    @Column(name = "sales_product_name", nullable = false)
    private String name;

    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "sales_product_id")
    private List<SalesOptionDataModel> salesOptions = new ArrayList<>();

    @Builder
    public SalesProductDataModel(Long id, String name, Long sellerId, List<SalesOptionDataModel> salesOptions) {
        this.id = id;
        this.name = name;
        this.sellerId = sellerId;
        this.salesOptions = salesOptions;
    }

    public static SalesProductDataModel from(SalesProduct salesProduct) {
        List<SalesOptionDataModel> optionDataModels = salesProduct.getSalesOptions().stream()
                .map(SalesOptionDataModel::from)
                .collect(Collectors.toList());

        return SalesProductDataModel.builder()
                .id(salesProduct.getId())
                .name(salesProduct.getName())
                .sellerId(salesProduct.getSellerId())
                .salesOptions(optionDataModels)
                .build();
    }

    public SalesProduct toEntity() {
        List<SalesOption> options = salesOptions.stream()
                .map(SalesOptionDataModel::toEntity)
                .collect(Collectors.toList());

        return new SalesProduct(id, name, sellerId, options);
    }
}
