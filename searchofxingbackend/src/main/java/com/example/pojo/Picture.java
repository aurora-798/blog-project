package com.example.pojo;

import com.example.pojo.vo.CommonVo;
import lombok.Data;

/**
 * 实时显示图片，无需存储到数据库中(好听好听好听~~~~)
 */
@Data
public class Picture {
    private String title;
    private String url;
}
