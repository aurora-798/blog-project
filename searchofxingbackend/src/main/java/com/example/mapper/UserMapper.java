package com.example.mapper;

import com.example.pojo.PageQuery;
import com.example.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 设置用户接口
     * @param user
     */
    void setUsers(User user);


    /**
     * 根据Id查询用户接口
     */
    @Select("select * from user where id = #{id} and isDelete = 0")
    User getUserById(Long id);


    /**
     * 查询所有用户接口
     */
//    @Select("select  * from user where isDelete = 0")
    List<User> getUserList(PageQuery pageQuery);


    /**
     * 根据用户名查询用户
     * @param user
     * @return
     */
    @Select("select * from user where userAccount = #{userAccount} and isDelete = 0")
    User getUserByName(User user);
}
