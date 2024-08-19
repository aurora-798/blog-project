package com.example.controller;

import cn.hutool.json.JSONUtil;
import com.example.config.Result;
import com.example.pojo.PageQuery;
import com.example.pojo.Post;
import com.example.pojo.vo.*;
import com.example.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/searchApi/post")
/**
 * 帖子Controller
 */
public class PostController {
    @Autowired
    private PostService postService;

    /**
     * 分页返回所有的帖子列表
     *
     * @param pageQuery
     * @return
     * @throws Exception
     */
    @PostMapping("/page")
    public CommonVo getPagePostList(@RequestBody PageQuery pageQuery) throws Exception {
        List<PostVo> postList = postService.getPageVoPostList(pageQuery);
        if (postList == null)
            throw new Exception("获取帖子数据失败");

        CommonVo commonVo = new CommonVo();
        commonVo.setPostVoList(postList);
        return commonVo;
    }

    /**
     * 获取所有的帖子列表
     *
     * @return
     */
    @PostMapping("/all")
    public List<PostVo> getAllPostList() {
        List<PostVo> allPost = postService.getAllPost();
        if (allPost == null || allPost.size() == 0) {
            log.error("获取所有的帖子列表为空，无法显示帖子列表");
        }
        return allPost;
    }

    /**
     * 更新帖子点赞数
     *
     * @return
     */
    @PostMapping("/updateThumbNum")
    public ThumbVo updateThumbNum(@RequestBody Post post) {
//        System.out.println(post);
        Long id = post.getId();
        if (id == null)
            log.error("帖子id为空，帖子不存在");
        ThumbVo thumbVo = postService.updateThumbNum(post);
        return thumbVo;
    }


    /**
     * 更新帖子收藏数
     *
     * @return
     */
    @PostMapping("/updateFavourNum")
    public FavourVo updateFavourNum(@RequestBody Post post) {
//        System.out.println(post);
        Long id = post.getId();
        if (id == null)
            log.error("帖子id为空，帖子不存在");
        FavourVo favourVo = postService.updateFavourNum(post);
        return favourVo;
    }


    /**
     * 根据帖子id查询帖子及帖子的相关信息
     */
    @GetMapping("/getPost")
    public Result<PostVo> getPostVo(Long id) {
        PostVo postvo = postService.getPostById(id);
        if (postvo == null)
            return Result.error("error");
        return Result.success(postvo, "ok");
    }

    /**
     * 添加帖子
     *
     * @param post
     * @return
     */
    @PostMapping("/AddPost")
    public Result<String> AddPost(@RequestBody PostVo post) {
//        log.info("post对象接受情况:" + post);
        if (post == null)
            return Result.error("输入数据不符合规范或系统错误,请联系管理员处理");
        String res = postService.AddPost(post);
        if (res.equals("Error"))
            return Result.error("输入数据不符合规范或系统错误,请联系管理员处理");
        else if (res.equals("ParamError")) {
            Result<String> error = Result.error("帖子存在未填写信息，不予提交");
            return error;
        }
        Result<String> result = Result.success(null, res);
        return result;
    }

    /**
     * 更新帖子
     * @param post
     * @return
     */
    @PutMapping("/PutPost")
    public Result<String> PutPost(@RequestBody PostVo post)
    {
        if (post == null)
            return Result.error("输入数据不符合规范或系统错误,请联系管理员处理");
        String res = postService.UpdatePost(post);
        if (res.equals("Error"))
            return Result.error("输入数据不符合规范或系统错误,请联系管理员处理");
        else if (res.equals("ParamError")) {
            Result<String> error = Result.error("帖子存在未填写信息，不予提交");
            return error;
        }
        Result<String> result = Result.success(null, res);
        return result;
    }


    /**
     * 删除帖子
     *
     * @param post
     * @return
     */
    @DeleteMapping("/delete")
    public Result<String> deletePost(@RequestBody Post post) {
        Integer code = postService.DeleteById(post);

        if (code == 1)
            return Result.success();
        return Result.error("删除失败");
    }

}
