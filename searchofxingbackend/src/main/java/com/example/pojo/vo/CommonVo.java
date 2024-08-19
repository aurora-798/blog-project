package com.example.pojo.vo;

import com.example.pojo.Picture;
import com.example.pojo.Post;
import com.example.pojo.User;
import lombok.Data;

import java.util.List;

/**
 * Tab导航栏统一结果返回类
 */
@Data
public class CommonVo {
    private List<Picture> pictureList;
    private List<User> userList;
    private List<Post> postList;
    private List<PostVo> postVoList;
}
