package com.example.design;

import com.example.datasource.DataSource;
import com.example.datasource.ImageDataSource;
import com.example.datasource.PostDataSource;
import com.example.datasource.UserDataSource;
import com.example.pojo.PageQuery;
import com.example.pojo.Picture;
import com.example.pojo.Post;
import com.example.pojo.User;
import com.example.pojo.vo.CommonRequest;
import com.example.pojo.vo.CommonVo;
import com.example.registory.DatasourceReGiStory;
import com.example.service.ImageService;
import com.example.service.PostService;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 门面模式：在这里使用门面模式可以使前端不用关心后端是怎么实现该业务逻辑的，前端只需传入一个参数key即可
 * 适配器模式: 通过接入不同的数据源来实现不同的功能,但是却实现了同一个数据源，通过父类数据源来调用子类不同的行为
 * 注册器模式: 用于减少if else分支
 */
@Component
@Slf4j
public class Facade {
    @Autowired
    private DatasourceReGiStory datasourceReGiStory;
    /**
     * 门面模式
     * 适配器模式
     * 注册器模式(本质上是单例模式)
     * @param commonRequest
     * @return
     * @throws Exception
     */
    public CommonVo getFacade(CommonRequest commonRequest) throws Exception {
        String key = commonRequest.getKey();
        int pageSize = commonRequest.getPageSize();
        int pageNum = commonRequest.getPageNum();
        String text = commonRequest.getText();

        DataSource dataSource = null;
        //分流查询并返回对应的数据,思想就是门面模式
        DataSource datasource = datasourceReGiStory.getDataSourceByType(key);
        CommonVo commonVo = datasource.GetSource(text, pageNum, pageSize,key);
        return commonVo;
    }
}




//封装一个CommonVo类作为post、picture、user的父类，用于统一返回
//    public CommonVo getOldFacade(CommonRequest commonRequest)
//    {
//        CommonVo commonVo = new CommonVo();
//        String key = commonRequest.getKey();
//        int pageSize = commonRequest.getPageSize();
//        int pageNum = commonRequest.getPageNum();
//        String text = commonRequest.getText();
//        //构造查询条件
//        PageQuery pageQuery = new PageQuery();
//        pageQuery.setText(text);
//        pageQuery.setPageSize(pageSize);
//        pageQuery.setPageNum(pageNum);
//
//        //分流查询并返回对应的数据,思想就是门面模式
//        if("post".equals(key))
//        {
//            try {
//                List<Post> pagePostList = postService.getPagePostList(pageQuery);
//                commonVo.setPostList(pagePostList);
//                return commonVo;
//            } catch (Exception e) {
//                log.error("返回common帖子列表出错");
//                throw new RuntimeException(e);
//            }
//        }
//        else if("picture".equals(key))
//        {
//            List<Picture> pictureList = imageService.GetPictureList(pageQuery);
//            commonVo.setPictureList(pictureList);
//            return commonVo;
//        }
//        else if("user".equals(key))
//        {
//            try {
//                List<User> allUserList = userService.getAllUserList(pageQuery);
//                commonVo.setUserList(allUserList);
//                return commonVo;
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return null;
//    }
