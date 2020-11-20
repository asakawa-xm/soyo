package com.soyo.kurosaki.dao;

import com.soyo.kurosaki.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserDao {

    User findByAccount(String account);

}
