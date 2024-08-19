package com.example.pojo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 帖子表
 */
@Data
public class Post {
    /**
     * 帖子id
     */
    private Long id;
    /**
     * 帖子分类
     */
    private String category;
    /**
     * 帖子标题
     */
    private String title;

    /**
     * 帖子简介
     */
    private String introduce;
    /**
     * 帖子内容
     */
    private String content;
    /**
     * 帖子标签
     */
    private String tags;

    /**
     * 帖子头像url
     */
    private String url;
    /**
     * 帖子点击数
     */
    private int thumbNum;
    /**
     * 帖子收藏数
     */
    private int favourNum;
    /**
     * 创建帖子Id
     */
    private Long userId;
    /**
     * 帖子创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 帖子修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    /**
     * 帖子是否删除
     */
    private Integer isDelete;

    /**
     * 帖子是否点赞
     */
    private Integer isThumb;

    /**
     * 帖子是否收藏
     */
    private Integer isFavour;
}
