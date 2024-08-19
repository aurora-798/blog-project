package com.example.pojo.vo;

import com.example.pojo.Post;
import lombok.Data;

/**
 * 点赞数vo
 */
@Data
public class ThumbVo {

    /**
     * 用户Id
     */
    private Long userId;



    /**
     * 帖子 id
     */
    private Long postId;


    /**
     * 是否点赞
     */
    private Integer flag;


    /**
     * 帖子点赞数
     */
    private int thumbNum;


}
