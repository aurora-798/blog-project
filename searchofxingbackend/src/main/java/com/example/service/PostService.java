package com.example.service;


import com.example.pojo.PageQuery;
import com.example.pojo.Post;
import com.example.pojo.vo.FavourVo;
import com.example.pojo.vo.PostVo;
import com.example.pojo.vo.PostVoString;
import com.example.pojo.vo.ThumbVo;
import javafx.geometry.Pos;

import java.util.List;

/**
 * 帖子Service
 */
public interface PostService {

    /**
     * 返回所有的帖子列表
     * @return
     */
    List<PostVo> getAllPost();

    /**
     * 分页返回帖子列表
     * @param pageQuery
     * @return
     * @throws Exception
     */
    List<Post> getPagePostList(PageQuery pageQuery) throws Exception;


    /**
     * 分页返回视图帖子列表
     * @param pageQuery
     * @return
     * @throws Exception
     */
    List<PostVo> getPageVoPostList(PageQuery pageQuery) throws Exception;

    /**
     * 更新帖子点赞数
     */
    ThumbVo updateThumbNum(Post post);

    /**
     * 更新帖子收藏数
     * @param post
     * @return
     */
    FavourVo updateFavourNum(Post post);

    /**
     * 根据帖子Id返回帖子以及帖子点赞收藏信息
     * @param id
     * @return
     */
    PostVo getPostById(Long id);

    /**
     * 添加帖子
     * @param post
     * @return
     */
    String AddPost(PostVo post);

    /**
     * 删除帖子
     * @param post
     * @return
     */
    Integer DeleteById(Post post);

    /**
     * 更新帖子
     * @param post
     * @return
     */
    String UpdatePost(PostVo post);

}
