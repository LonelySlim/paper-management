package com.pm.papermanagement.realm;

import com.pm.papermanagement.common.entity.User;
import com.pm.papermanagement.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyRealm extends AuthorizingRealm {
    UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //String name = authenticationToken.getPrincipal().toString();
        String name = ((UsernamePasswordToken)authenticationToken).getUsername();
        User user = userMapper.selectUserByUsername(name);
        if(user != null){
            AuthenticationInfo info = new SimpleAuthenticationInfo(
                    user.getUsername(),
                    user.getPassword(),
                    ByteSource.Util.bytes("salt"),
                    this.getName()
            );
            return info;
        }
        return null;
    }
}
