package com.example.controller;

import com.example.pojo.PageQuery;
import com.example.pojo.Picture;
import com.example.pojo.vo.CommonVo;
import com.example.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/searchApi/picture")
@Slf4j
/**
 * 图片资源Controller
 */
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping("/all")
    public CommonVo getPictureList(@RequestBody PageQuery imagePageQuery)
    {
        List<Picture> pictureList = imageService.GetPictureList(imagePageQuery);
        if(pictureList==null || pictureList.size()==0)
            log.error("图片列表为空，无法正确显示图片");

        CommonVo commonVo = new CommonVo();
        commonVo.setPictureList(pictureList);
        return commonVo;
    }
}
