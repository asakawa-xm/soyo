package com.soyo.kurosaki.bean;

import lombok.Data;

@Data
public class User {

    private Integer id;
    private String name;
    private String account;
    private String password;
    private Character sex;

}
