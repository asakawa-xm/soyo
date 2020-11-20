package com.soyo.kurosaki.controller;

import com.soyo.kurosaki.bean.Json;
import com.soyo.kurosaki.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 通过账号查找用户信息
     * @param account
     * @param password
     * @return
     */
    @RequestMapping(value = "login",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Json login(String account, String password) {
        Json json = new Json();
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(account, password));
            json.setSuccessful(true);
            json.setMsg("登陆成功");
            json.setData(SecurityUtils.getSubject().getSession().getAttribute("name"));
            return json;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            json.setSuccessful(false);
            json.setMsg("账号或密码错误");
            return json;
        }
    }

    /**
     * 获取加密后的新密码
     * @param password
     * @return
     */
    @RequestMapping(value = "getPassword",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getPassword(String password) {
        ByteSource salt = ByteSource.Util.bytes(password);
        return new SimpleHash("MD5", password, salt, 1024).toHex();
    }
}
