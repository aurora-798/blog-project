package com.example.datasource;

import com.example.pojo.PageQuery;
import com.example.pojo.Post;
import com.example.pojo.User;
import com.example.pojo.vo.CommonVo;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户资源接入本地数据源
 */
@Component
@Slf4j
public class UserDataSource implements DataSource {


    @Autowired
    private UserService userService;

    /**
     * 实现父类制定规范,也就是两个适配接口的过程
     *
     * @param text
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    public CommonVo GetSource(String text, int pageNum, int pageSize, String category) throws Exception {
        //category作为图片的保留，然后可使用此信息进行扩展
        PageQuery pageQuery = new PageQuery();
        pageQuery.setText(text);
        pageQuery.setPageNum(pageNum);
        pageQuery.setPageSize(pageSize);

        List<User> UserList = userService.getAllUserList(pageQuery);

        for (int i = 0; i < UserList.size(); i++) {
            User user = UserList.get(i);
            user.setUserName("");
            user.setUserPassword("");
            user.setToken("");
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            user.setUserRole("");
        }
        CommonVo commonVo = new CommonVo();
        commonVo.setUserList(UserList);
        return commonVo;
    }
}
