package com.example.datasource;

import cn.hutool.json.JSONUtil;
import com.example.pojo.PageQuery;
import com.example.pojo.Picture;
import com.example.pojo.vo.CommonVo;
import com.example.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 图片数据接入本地数据源
 */
@Slf4j
@Component
public class ImageDataSource implements DataSource {

    @Autowired
    private ImageService imageService;

    /**
     * 实现父类制定规范,也就是两个适配接口的过程
     *
     * @param text
     * @param pageNum
     * @param pageSize
     * @return
     */
    public CommonVo GetSource(String text, int pageNum, int pageSize, String category) {
//        category作为图片的保留，然后可使用此信息进行扩展
        PageQuery pageQuery = new PageQuery();
        pageQuery.setText(text);
        pageQuery.setPageNum(pageNum);
        pageQuery.setPageSize(pageSize);
        List<Picture> pictureList = imageService.GetPictureList(pageQuery);

        CommonVo commonVo = new CommonVo();
        commonVo.setPictureList(pictureList);
        return commonVo;
    }
}
