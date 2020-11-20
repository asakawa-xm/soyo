package com.soyo.kurosaki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebPageController {

    @RequestMapping("index")
    public String goHomePage(){
//        System.out.println("跳转到索引页");
        return "index";
    }

    @RequestMapping("homePage")
    public String homePage(){
//        System.out.println("跳转到首页");
        return "homePage";
    }

    @RequestMapping("worksShow")
    public String worksShow(){
//        System.out.println("跳转到正片页");
        return "worksShow";
    }

    @RequestMapping("album")
    public String myPhoto(){
//        System.out.println("跳转到我的页");
        return "album";
    }

}
