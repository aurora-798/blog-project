package com.example.pojo.vo;

import lombok.Data;

/**
 * Tab导航栏请求接受类
 */
@Data
public class CommonRequest {
    private String text;
    private String key;
    private int pageNum;
    private int pageSize;
}
