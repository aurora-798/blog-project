package com.example.service;

import com.example.pojo.PageQuery;
import com.example.pojo.User;

import java.util.List;

public interface UserService {

    /**
     * 查询单个用户接口
     * @param id
     * @return
     */
    User getUserById(Long id);

    /**
     * 查询所有用户接口
     */
    List<User> getAllUserList(PageQuery pageQuery) throws Exception;

    /**
     * 用户登录校验
     * @param user
     * @return
     */
    User CheckUserLogin(User user);
}
