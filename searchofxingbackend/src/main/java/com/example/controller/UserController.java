package com.example.controller;

import com.example.pojo.PageQuery;
import com.example.pojo.User;
import com.example.pojo.vo.CommonVo;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/searchApi/user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 查询单个用户
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping
    public User getUser(Long id) throws Exception {
        if(id!=null)
        {
            User user = userService.getUserById(id);
            if(user==null)
                throw new Exception("找不到此用户相关信息");
            return user;
        }
        else
            throw new Exception("用户Id不存在");
    }


    /**
     * 查询所有用户信息
     */
    @PostMapping("/all")
    public CommonVo getUserList(@RequestBody PageQuery pageQuery)
    {
        CommonVo commonVo = new CommonVo();
        List<User> userList = null;
        try {
           userList  = userService.getAllUserList(pageQuery);
           commonVo.setUserList(userList);
            if(userList == null)
                throw new Exception("程序执行异常");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return commonVo;
    }
}
