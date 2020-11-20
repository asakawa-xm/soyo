package com.soyo.kurosaki.service;

import com.soyo.kurosaki.bean.User;
import com.soyo.kurosaki.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    UserDao userDao;

    /**
     * 通过账号查找用户信息
     * @param account
     * @return
     */
    public User findByAccount(String account) {
        return userDao.findByAccount(account);
    }
}
