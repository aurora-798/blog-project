package com.example.service;

import com.example.pojo.PageQuery;
import com.example.pojo.Picture;

import java.util.List;

/**
 * 图片资源Service
 */
public interface ImageService {

    /**
     * 分页获取所有图片
     * @param pageQuery
     * @return
     */
    List<Picture> GetPictureList(PageQuery pageQuery);
}
