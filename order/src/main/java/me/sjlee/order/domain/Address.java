package me.sjlee.order.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Address {

    private final String address1;
    private final String address2;
    private final String zipcode;

    public Address(String address1, String address2, String zipcode) {
        validate(address1, address2, zipcode);
        this.address1 = address1;
        this.address2 = address2;
        this.zipcode = zipcode;
    }

    private void validate(String address1, String address2, String zipcode) {
        if (address1 == null || address2 == null || zipcode == null) {
            throw new IllegalStateException("[Address] 주소값은 비어있을 수 없습니다.");
        }
    }
}
