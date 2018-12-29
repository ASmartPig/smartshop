package com.smart.web;

import com.smart.common.exceptions.ServerException;
import com.smart.common.utils.IDCreateUtil;
import com.smart.exception.UserOptionServerMsgConstants;
import com.smart.mock.Result;
import com.smart.pojo.User;
import com.smart.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
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
            User user = new User(IDCreateUtil.createUUID(),account,password,account);
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
       }catch (Exception e){
            return Result.fail("200",e.getMessage());
       }
        return Result.success("success");
    }
}
