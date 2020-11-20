package com.soyo.kurosaki.bean;

import lombok.Data;

@Data
public class Album {

    private Integer id;
    private String name;
    private String account;
    private String permission;//权限。0：公开，1：私密

}
