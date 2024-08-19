package com.example.mapper;

import com.example.pojo.Favour;
import com.example.pojo.Thumb;
import org.apache.ibatis.annotations.*;

/**
 * 帖子收藏表mapper
 */
@Mapper
public interface FavourMapper {
    /**
     * 根据用户Id和帖子Id来确定这一条帖子数据
     */
    @Select("select id, postId, userId, flag, createTime, updateTime,isDelete " + "from favour where userId=#{userId} and postId=#{postId}")
    Favour getFavour(Favour favour);


    /**
     * 根据帖子id删除帖子表
     * @param favour
     * @return
     */
    @Select("select id, postId, userId, flag, createTime, updateTime,isDelete " + "from favour where postId=#{postId}")
    Favour getFavourByPostId(Favour favour);

    /**
     * 更新该表状态
     */
    @Update("update favour set flag = #{flag} where userId=#{userId} and postId=#{postId}")
    int updateFavour(Favour favour);

    /**
     * 插入帖子收藏表数据
     *
     * @param favour
     * @return
     */
    @Insert("insert into favour(postId,userId,flag,createTime,updateTime,isDelete) " + " values(#{postId},#{userId},#{flag},#{createTime},#{updateTime},#{isDelete})")
    Integer InsertFavour(Favour favour);

    /**
     * 删除帖子收藏表
     *
     * @param id
     * @return
     */
    @Update("update favour set isDelete = 1 where postId = #{id}")
    Integer deleteFavourByPostId(Long id);
}
