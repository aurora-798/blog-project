package com.example.datasource;

import com.example.mapper.FavourMapper;
import com.example.mapper.PostMapper;
import com.example.mapper.ThumbMapper;
import com.example.pojo.Favour;
import com.example.pojo.PageQuery;
import com.example.pojo.Post;
import com.example.pojo.Thumb;
import com.example.pojo.vo.CommonVo;
import com.example.pojo.vo.FavourVo;
import com.example.pojo.vo.PostVo;
import com.example.pojo.vo.ThumbVo;
import com.example.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 帖子数据源接入本地数据源
 */
@Component
@Slf4j
public class PostDataSource implements DataSource {

    @Autowired
    private PostService postService;

    /**
     * * 实现父类制定规范,也就是两个适配接口的过程
     * @param text
     * @param pageNum
     * @param pageSize
     * @return
     */
    public CommonVo GetSource(String text, int pageNum, int pageSize,String category){

        //使用category字段来区分返回post表中不同分类的数据

        PageQuery pageQuery = new PageQuery();
        pageQuery.setText(text);
        pageQuery.setPageNum(pageNum);
        pageQuery.setPageSize(pageSize);
        pageQuery.setCategory(category);

        List<PostVo> pagePostList = null;
        try {
            pagePostList = postService.getPageVoPostList(pageQuery);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        CommonVo commonVo = new CommonVo();
        commonVo.setPostVoList(pagePostList);
        return commonVo;
    }
}
