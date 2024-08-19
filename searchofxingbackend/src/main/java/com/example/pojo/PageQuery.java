package com.example.pojo;

import lombok.Data;

/**
 * 图片分页查询封装类
 */
@Data
public class PageQuery {

    private String text;

    private int pageNum;

    private int pageSize;

    private String category;

}
