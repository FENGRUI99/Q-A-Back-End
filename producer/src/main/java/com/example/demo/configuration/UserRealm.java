package com.example.demo.configuration;


import com.example.demo.service.service.HelloService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;


public class UserRealm extends AuthorizingRealm{
    @Autowired
    HelloService helloService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        String pwd = helloService.PswByid(userToken.getUsername());

        if (pwd == null){
            return null;
        }
        return new SimpleAuthenticationInfo("",pwd,"");
    }
}
