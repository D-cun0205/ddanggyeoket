package com.market.ddanggyeo.user.dto

import javax.persistence.Embeddable

@Embeddable
class Address(zipCode: String, addressLine1: String, addressLine2: String) {
    var zipCode: String = zipCode
    var addressLine1: String = addressLine1
    var addressLine2: String = addressLine2
}