package com.example.pojo;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 帖子点赞确认表
 */
@Data
public class Thumb implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 帖子 id
     */
    private Long postId;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 是否点赞
     */
    private Integer flag;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;
}