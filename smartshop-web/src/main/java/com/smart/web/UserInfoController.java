package com.smart.web;

import com.smart.common.exceptions.ServerException;
import com.smart.exception.UserOptionServerMsgConstants;
import com.smart.mock.Result;
import com.smart.pojo.User;
import com.smart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserInfoController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public Result<String> register(@RequestParam(value = "account") String account, @RequestParam(value = "password") String password){
        try {
            if(StringUtils.isEmpty(account) || StringUtils.isEmpty(password)){
                throw new ServerException(UserOptionServerMsgConstants.USER_PARAM_IS_NULL);
            }
            if(userService.isExistAccount(account)){
                throw new ServerException(UserOptionServerMsgConstants.USER_ACCOUNT_IS_EXIST);
            }
        }catch (Exception e){
            return Result.fail("200",e.getMessage());
        }
        return Result.success("success");
    }

    @RequestMapping("/login")
    public Result<String> login(@RequestParam(value = "account") String account, @RequestParam(value = "password") String password){
       try {
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
