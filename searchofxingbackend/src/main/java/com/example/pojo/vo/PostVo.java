package com.example.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Post列表返回类，目前和Post唯一的不同就是把tags的类型改为了List<String>,方便前端做展示
 */
@Data
public class PostVo {
    /**
     * 帖子id
     */
    private Long id;

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
    private List<String> tags;

    /**
     * 帖子头像url
     */
    private String url;
    /**
     * 帖子点击数
     */
    private Integer thumbNum;
    /**
     * 帖子收藏数
     */
    private Integer favourNum;
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

    private Integer isThumb;

    private Integer isFavour;
}
