package com.example.mapper;

import com.example.pojo.Favour;
import com.example.pojo.Post;
import com.example.pojo.Thumb;
import javafx.geometry.Pos;
import org.apache.ibatis.annotations.*;

/**
 * 帖子点赞表mapper
 */
@Mapper
public interface ThumbMapper {
    /**
     * 根据用户Id和帖子Id来确定这一条帖子数据
     */
    @Select("select id, postId, userId, flag, createTime, updateTime " +
            "from thumb where userId=#{userId} and postId=#{postId}")
    Thumb getThumb(Thumb thumb);

    /**
     * 根据帖子id删除帖子点赞表
     * @param thumb
     * @return
     */
    @Select("select id, postId, userId, flag, createTime, updateTime " +
            "from thumb where postId=#{postId}")
    Thumb getThumbByPostId(Thumb thumb);


    /**
     * 更新该表状态
     */
    @Update("update thumb set flag = #{flag} where userId=#{userId} and postId=#{postId}")
    int updateThumb(Thumb thumb);

    /**
     * 插入帖子点赞表数据
     *
     * @param thumb
     * @return
     */
    @Insert("insert into thumb(postId,userId,flag,createTime,updateTime,isDelete) values(#{postId},#{userId},#{flag},#{createTime},#{updateTime},#{isDelete})")
    Integer InsertThumb(Thumb thumb);


    /**
     * 删除帖子点赞表
     * @param id
     * @return
     */
    @Update("update thumb set isDelete = 1 where postId = #{id}")
    Integer deleteThumbByPostId(Long id);
}
