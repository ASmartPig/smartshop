package com.smart.web;

import com.alibaba.fastjson.JSON;
import com.smart.common.exceptions.ServerException;
import com.smart.common.utils.IDCreateUtil;
import com.smart.common.utils.MD5Util;
import com.smart.exception.UserOptionServerMsgConstants;
import com.smart.mock.Result;
import com.smart.pojo.User;
import com.smart.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Slf4j
@Controller
public class UserInfoController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    @ResponseBody
    public Result<String> register(@RequestParam(name = "account") String account, @RequestParam(name = "password") String password){
        try {
            if(StringUtils.isEmpty(account) || StringUtils.isEmpty(password)){
                throw new ServerException(UserOptionServerMsgConstants.USER_PARAM_IS_NULL);
            }
            if(userService.isExistAccount(account)){
                throw new ServerException(UserOptionServerMsgConstants.USER_ACCOUNT_IS_EXIST);
            }
            User user = new User(IDCreateUtil.createUUID(),account, MD5Util.md5Create(password),account);
            userService.registerUser(user);
        }catch (Exception e){
            return Result.fail("200",e.getMessage());
        }
        return Result.success("success");
    }

    @RequestMapping("/login")
    @ResponseBody
    public Result<String> login(@RequestParam(value = "account") String account, @RequestParam(value = "password") String password){
       try {
           log.info("login start ...");
           Subject subject = SecurityUtils.getSubject();
           UsernamePasswordToken token = new UsernamePasswordToken(account, password);
           //执行认证操作.
           subject.login(token);

           if(StringUtils.isEmpty(account) || StringUtils.isEmpty(password)){
               throw new ServerException(UserOptionServerMsgConstants.USER_PARAM_IS_NULL);
           }
          if(!userService.isExistAccount(account)){
              throw new ServerException(UserOptionServerMsgConstants.USER_ACCOUNT_IS_NOT_EXIST);
          }
          User user = userService.getUserByAccount(account);
          if (!password.equals(user.getPassword())){
              throw new ServerException(UserOptionServerMsgConstants.PASSWORD_IS_INCORRECT);
          }
       }catch (AuthenticationException ae){
           log.info("AuthenticationException异常信息：",ae);
           return Result.fail("200", JSON.toJSONString(ae));
       } catch (Exception e){
           log.info("Exception异常信息：",e);
            return Result.fail("200", JSON.toJSONString(e));
       }
        return Result.success("success");
    }
}
