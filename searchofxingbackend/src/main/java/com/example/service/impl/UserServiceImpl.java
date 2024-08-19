package com.example.service.impl;

import com.example.exception.ServiceException;
import com.example.mapper.UserMapper;
import com.example.pojo.PageQuery;
import com.example.pojo.User;
import com.example.service.UserService;
import com.example.tools.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User getUserById(Long id) {
        User user = userMapper.getUserById(id);

        //查询用户为空
        if(user == null)
            try {
                throw new Exception();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        return user;
    }

    /**
     * 查询所有用户接口
     * @return
     */
    public List<User> getAllUserList(PageQuery pageQuery) throws Exception {
        List<User> userList = userMapper.getUserList(pageQuery);
        if(userList == null)
            throw new Exception("程序执行异常");
        return userList;
    }

    /**
     * 用户登录校验
     * @param user
     * @return
     */
    public User CheckUserLogin(User user) {

        if(user == null)
            throw new ServiceException("请登录");

        User usr = userMapper.getUserByName(user);

        if(usr == null)
            throw new ServiceException("401","用户名或密码错误");

        //从数据库中获取该用户账号密码以及用户Id
        Long userId = usr.getId();
        String Password = usr.getUserPassword();
        String Account = usr.getUserAccount();
        if(userId == null || Password == null)
            throw new ServiceException("401","用户名或密码错误");

        //获取用户输入的账号密码
        String userAccount = user.getUserAccount();
        String userPassword = user.getUserPassword();
        if(!Account.equals(userAccount) || !Password.equals(userPassword))
            throw new ServiceException("401","用户名或密码错误");

        //为当前用户生成token
        String token = TokenUtils.CreateToken(userId.toString(), Password);
        usr.setToken(token);
        usr.setUserPassword("隐藏");
        return usr;
    }
}
