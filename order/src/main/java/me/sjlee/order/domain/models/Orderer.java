package me.sjlee.order.domain.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter @EqualsAndHashCode
public class Orderer {

    @Column(name = "orderer_id")
    private Long id;

    @Column(name = "orderer_name")
    private String name;

    protected Orderer() {}

    public Orderer(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
