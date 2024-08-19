package com.example.mapper;

import com.example.pojo.PageQuery;
import com.example.pojo.Post;
import com.example.pojo.vo.PostVo;
import javafx.geometry.Pos;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 帖子Mapper层
 */
@Mapper
public interface PostMapper {
    /**
     * 分页返回帖子列表
     */
//    @Select("select * from post where isDelete=0")
    List<Post> getPagePostList(PageQuery pageQuery);


    /**
     * 返回帖子列表所有数据
     *
     * @return
     */
    @Select("select id, title,introduce,content, tags, thumbNum, favourNum, url,userId, createTime, updateTime, isDelete,category " +
            "from post where isDelete=0 order by updateTime desc")
    List<Post> getPostList();

    /**
     * 插入帖子
     *
     * @param post
     * @return
     */
    @Insert("insert into post(title,content,tags,userId,createTime,updateTime)" +
            "values (#{title},#{content},#{tags},#{userId},#{createTime},#{updateTime})")
    boolean AddOnePost(Post post);


    /**
     * 更新帖子点赞数
     *
     * @param post
     * @return
     */
    @Update("update post set thumbNum=#{thumbNum},isThumb = #{isThumb} where id=#{id}")
    int updateThumbNum(Post post);


    /**
     * 更新帖子收藏数
     *
     * @param post
     * @return
     */
    @Update("update post set favourNum=#{favourNum},isFavour = #{isFavour} where id=#{id}")
    int updateFavourNum(Post post);

    /**
     * 根据帖子id获取视图帖子数据
     *
     * @param id
     * @return
     */
    @Select("select * from post where id = #{id}")
    PostVo getOnePostVo(Long id);

    /**
     * 插入一条帖子数据
     *
     * @param post
     * @return
     */
    Integer InsertPost(Post post);

    /**
     * 删除帖子
     *
     * @param id
     * @return
     */
    @Update("update post set isDelete = 1 where id = #{id}")
    Integer deletePostById(Long id);

    /**
     * 根据帖子id获取帖子
     *
     * @param id
     * @return
     */
    @Select("select id, title, content,introduce,tags, thumbNum, favourNum, url," +
            "userId, createTime, updateTime, isDelete,category,isThumb,isFavour from post where id = #{id}")
    Post getOnePost(Long id);

    /**
     * 根据帖子Id更新帖子
     *
     * @param post
     * @return
     */
    @Update("update post set title = #{title},introduce = #{introduce},content = #{content}," +
            "tags=#{tags},thumbNum=#{thumbNum},favourNum=#{favourNum},category=#{category} where id = #{id}")
    Integer updatePostById(Post post);

    @Update("update post set isThumb = #{isThumb},thumbNum = #{thumbNum} where id = #{id}")
    Integer updateLikeById(Post post);

    @Update("update post set isFavour = #{isFavour},favourNum = #{favourNum} where id = #{id}")
    Integer updateStarById(Post post);
}
