package com.market.ddanggyeo.user.dto;

import javax.persistence.*;

@Entity
public class UserDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String username;
    private String phone;
    @Embedded
    private Address address;
}
