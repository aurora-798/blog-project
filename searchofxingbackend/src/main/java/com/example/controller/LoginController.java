package com.example.controller;

import com.example.config.Result;
import com.example.exception.ServiceException;
import com.example.pojo.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/searchApi")
public class LoginController {

    @Autowired
    private UserService userService;


    /**
     * 用户登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Result LoginIn(@RequestBody User user) {
        if (user == null)
            throw new ServiceException("401","请登录");
        User usr = userService.CheckUserLogin(user);
        if(usr == null)
            throw new ServiceException("401","用户名或密码错误");
        return Result.success(usr,"ok");
    }

}
