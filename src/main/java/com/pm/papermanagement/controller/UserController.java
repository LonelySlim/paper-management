package com.pm.papermanagement.controller;

import com.pm.papermanagement.common.entity.User;
import com.pm.papermanagement.common.model.ReturnValue;
import com.pm.papermanagement.common.model.param.UserParam;
import com.pm.papermanagement.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
//@CrossOrigin
public class UserController {

    UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @PostMapping(value = "/api/user/login",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ReturnValue userLogin(@RequestBody UserParam userParam){
        //Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userParam.getUsername(), userParam.getPassword());
        token.setRememberMe(true);
        try {
            SecurityUtils.getSubject().login(token);
            System.out.println(SecurityUtils.getSubject().getPrincipal().toString());
            return ReturnValue.generateSuccess(null);
        } catch (AuthenticationException e) {
            return ReturnValue.generate("600","登陆失败",null);
        }
    }

    @PostMapping(value = "/api/user/register",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public  ReturnValue userRegister(@RequestBody UserParam userParam){
        Md5Hash hash = new Md5Hash(userParam.getPassword(),"salt",4);
        userParam.setPassword(hash.toString());
        userMapper.addUser(userParam);
        User user = userMapper.selectUserByUsername(userParam.getUsername());
        return ReturnValue.generateSuccess(user);
    }

    @PostMapping(value = "/api/user/logout")
    public ReturnValue userLogout(){
        SecurityUtils.getSubject().logout();
        return ReturnValue.generateSuccess(null);
    }

    @GetMapping(value = "/api/user/haha")
    public String haha(){
        return "haha";
    }
}
