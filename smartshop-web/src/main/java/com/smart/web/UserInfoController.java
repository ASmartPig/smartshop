package com.smart.web;

import com.smart.pojo.User;
import com.smart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserInfoController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public ModelAndView register(){
        User user = new User("123","root","root","test");
        List list = userService.getUserList();
        //Integer integer = userService.registerUser(user);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        mv.addObject("userList", list);
        return mv;
    }
}
