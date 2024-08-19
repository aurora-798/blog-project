package com.example.service.impl;

import cn.hutool.json.JSONUtil;
import com.example.pojo.PageQuery;
import com.example.pojo.Picture;
import com.example.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
 * 图片资源实现类
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
    /**
     * 分页获取所有图片实现方法
     * @param pageQuery
     * @return
     */
    public List<Picture> GetPictureList(PageQuery pageQuery) {
        int pageNum = pageQuery.getPageNum();
        int pageSize = pageQuery.getPageSize();
        String search = pageQuery.getText();
        List<Picture> pictures = getPictures(search,pageNum,pageSize);
        if(pictures == null || pictures.size()==0)
            log.error("图片资源为空，无法返回图片资源信息");
        return pictures;
    }


    /**
     * 分页获取图片具体实现
     * @param search
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<Picture> getPictures(String search,int pageIndex,int pageSize)
    {
        // 创建一个信任所有证书的 SSL 上下文
        TrustManager[] trustAllCertificates = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCertificates, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        } catch (Exception e) {
            log.error("证书验证失败");
        }

// 使用Jsoup进行请求
        //与目标网页建立链接,获取HTML文档
        Document document = null;
//        int Index = (pageIndex - 1) * pageSize;
        if(search == null || search == "")
            search = "风景";
        try {
            document = Jsoup.connect(String.format("https://cn.bing.com/images/search?q=%s&form=HDRSC2&first=%d",search,pageIndex)).get();
        } catch (IOException e) {
            log.error("与目标网页建立链接失败");
        }
//		System.out.println(document);
        if(document == null)
            log.error("document文档获取失败，所以图片无法正常加载,请检查相关设置");
        //从HTML文档中搜索图片
        Elements newsHeadlines = document.select(".iuscp.isv");
        if(newsHeadlines == null)
            log.error("HTML文档不存在图片文件，无法加载图片");
        //遍历并提取图片和图片标题
        List<Picture> pictureList = new ArrayList<>();
        for(int i=0;i< newsHeadlines.size();i++)
        {
            //提取图片元素
            Element element = newsHeadlines.get(i);
            if(element == null)
                log.error("图片元素获取失败");
            //提取url
            String e = element.select(".iusc").get(0).attr("m");
            Map<String,Object> UrlMap = JSONUtil.toBean(e,Map.class);
            String url = (String) UrlMap.get("murl");
            if(url == null)
                log.error("url提取失败");
            //提取标题
            String title = element.select(".inflnk").get(0).attr("aria-label");
            if(title == null)
                log.error("标题提取失败");
//			System.out.println(url);
//			System.out.println(title);
            Picture picture = new Picture();
            picture.setTitle(title);
            picture.setUrl(url);
            pictureList.add(picture);

            if(pictureList.size() >= pageSize)
                break;
        }
        return pictureList;
    }


}
