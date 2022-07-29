package com.market.ddanggyeo.user.dto;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String zipCode;
    private String addressLine1;
    private String addressLine2;
}
