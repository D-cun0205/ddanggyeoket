package com.market.ddanggyeo.user.dto

import javax.persistence.*

@Entity
class UserDto(email: String, username: String, phone: String, address: Address) {

    @Id
    var email: String = email

    var username: String? = username

    var phone: String? = phone

    @get:Embedded
    var address: Address? = address
}