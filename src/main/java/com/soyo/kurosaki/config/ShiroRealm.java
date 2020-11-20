package com.soyo.kurosaki.config;

import com.soyo.kurosaki.bean.User;
import com.soyo.kurosaki.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.findByAccount(token.getUsername());
        if (user != null) {
            SecurityUtils.getSubject().getSession().setAttribute("name", user.getName());
            SecurityUtils.getSubject().getSession().setAttribute("account", user.getAccount());
            //盐值
            ByteSource credentialsSalt = ByteSource.Util.bytes(user.getAccount());
            return new SimpleAuthenticationInfo(user.getAccount(), user.getPassword(), credentialsSalt, getName());
        } else {
            throw new AuthenticationException("用户不存在");
        }
    }
}
