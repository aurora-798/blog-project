package com.example.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.mapper.FavourMapper;
import com.example.mapper.PostMapper;
import com.example.mapper.ThumbMapper;
import com.example.pojo.Favour;
import com.example.pojo.PageQuery;
import com.example.pojo.Post;
import com.example.pojo.Thumb;
import com.example.pojo.vo.FavourVo;
import com.example.pojo.vo.PostVo;
import com.example.pojo.vo.PostVoString;
import com.example.pojo.vo.ThumbVo;
import com.example.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.internal.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 帖子Service实现层
 */
@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private ThumbMapper thumbMapper;

    @Autowired
    private FavourMapper favourMapper;


    /**
     * 返回所有列表数据
     */
    public List<PostVo> getAllPost() {
        List<Post> postList = postMapper.getPostList();
        if (postList == null || postList.size() == 0) {
            log.error("获取所有帖子列表时为空，无法显示帖子列表");
        }
        List<PostVo> postVoList = postList.stream()
                .map(post -> {
                    PostVo postVo = new PostVo();
                    BeanUtils.copyProperties(post, postVo);

                    String tags = post.getTags();
                    List<String> tagList = JSONUtil.toList(tags, String.class);
                    postVo.setTags(tagList);
                    return postVo;
                }).collect(Collectors.toList());
        return postVoList;
    }

    /**
     * 分页返回列表数据
     *
     * @param pageQuery
     * @return
     * @throws Exception
     */
    @Override
    public List<Post> getPagePostList(PageQuery pageQuery) throws Exception {
        int pageNum = pageQuery.getPageNum();
        int pageSize = pageQuery.getPageSize();
        //设置当前页码下表,因为使用limit分页时第一个参数代表数据下表(从0开始),第二个参数代表查询条数
        pageQuery.setPageNum((pageNum - 1) * pageSize);
        List<Post> postList = postMapper.getPagePostList(pageQuery);
        if (postList == null)
            throw new Exception("查询帖子数据失败");
        return postList;
    }

    /**
     * 视图版分页返回列表数据
     *
     * @param pageQuery
     * @return
     * @throws Exception
     */
    public List<PostVo> getPageVoPostList(PageQuery pageQuery) throws Exception {
        List<Post> postList = getPagePostList(pageQuery);

        List<PostVo> postVoList = postList.stream()
                .map(post -> {
                    PostVo postVo = new PostVo();
                    BeanUtils.copyProperties(post, postVo);
                    String tags = post.getTags();
                    List<String> tagsList = JSONUtil.toList(tags, String.class);
                    postVo.setTags(tagsList);
                    return postVo;
                }).collect(Collectors.toList());

        return postVoList;
    }

    /**
     * 更新帖子点赞数
     *
     * @param post
     * @return
     */
    @Transactional
    public ThumbVo updateThumbNum(Post post) {
        ThumbVo thumbVo = new ThumbVo();
        thumbVo.setUserId(post.getUserId());
        thumbVo.setPostId(post.getId());

        Long postId = post.getId();
        Long userId = post.getUserId();
        if (postId == null)
            log.error("帖子Id为空，帖子不存在");
        if (userId == null)
            log.error("帖子用户不存在...");

        Post onePost = postMapper.getOnePost(postId);
        if (onePost == null)
            log.error("数据库中不存在该帖子~~");

        //未点击
        if (onePost.getIsThumb() == 0)
            post.setThumbNum(post.getThumbNum() + 1);
            //点击了
        else
            post.setThumbNum(post.getThumbNum() - 1);

        Integer flag = (onePost.getIsThumb() ^ 1);
        //异或运算，0^1=1 1^1=0

        //设置帖子的返回信息状态
        thumbVo.setFlag(flag);
        thumbVo.setThumbNum(post.getThumbNum());


        //更新帖子点赞确认表状态
        post.setIsThumb(flag);
        Integer res = postMapper.updateLikeById(post);
        if (res == 0) {
            log.error("帖子点赞状态更新确认失败");
            return null;
        }
        return thumbVo;
    }

    /**
     * 更新帖子收藏数
     *
     * @param post
     * @return
     */
    @Override
    public FavourVo updateFavourNum(Post post) {
        FavourVo favourVo = new FavourVo();
        favourVo.setUserId(post.getUserId());
        favourVo.setPostId(post.getId());

        Long postId = post.getId();
        Long userId = post.getUserId();
        if (postId == null)
            log.error("帖子Id为空，帖子不存在");
        if (userId == null)
            log.error("帖子用户不存在...");

        Post onePost = postMapper.getOnePost(postId);
        if (onePost == null)
            log.error("数据库中不存在该帖子~~");

        //未点击
        if (onePost.getIsFavour() == 0)
            post.setFavourNum(post.getFavourNum() + 1);
            //点击了
        else
            post.setFavourNum(post.getFavourNum() - 1);

        Integer flag = (onePost.getIsFavour() ^ 1);
        //异或运算，0^1=1 1^1=0

        //设置帖子的返回信息状态
        favourVo.setFlag(flag);
        favourVo.setFavourNum(post.getFavourNum());


        //更新帖子点赞确认表状态
        post.setIsFavour(flag);
        Integer res = postMapper.updateStarById(post);
        if (res == 0) {
            log.error("帖子收藏状态更新确认失败");
            return null;
        }
        return favourVo;
    }

    /**
     * 根据帖子id获取帖子
     *
     * @param id
     * @return
     */
    @Transactional
    public PostVo getPostById(Long id) {
        Post post = postMapper.getOnePost(id);
        if (post == null)
            return null;
        PostVo postVo = new PostVo();
        BeanUtils.copyProperties(post, postVo);
        String tags = post.getTags();
        List<String> tagsList = JSONUtil.toList(tags, String.class);
        postVo.setTags(tagsList);
        postVo.setFavourNum(postVo.getFavourNum());
        postVo.setThumbNum(postVo.getThumbNum());
        if (post == null)
            return null;
        return postVo;
    }

    /**
     * 添加帖子
     *
     * @param postvo
     * @return
     */
    @Transactional
    public String AddPost(PostVo postvo) {
        if (postvo == null)
            return "Error";
        Post post = new Post();

        //参数校验
        String title = postvo.getTitle();
        List<String> tags = postvo.getTags();
        String content = postvo.getContent();
        String introduce = postvo.getIntroduce();
        String category = postvo.getCategory();
        if (StringUtil.isBlank(title) || StringUtil.isBlank(content) ||
                StringUtil.isBlank(introduce) || tags == null || tags.size() == 0 || category == null)
            return "ParamError";

        //分类必须是这三种的一种
        if (!(("front".equals(category) || "backend".equals(category) || "post".equals(category))))
            return "ParamError";


        postvo.setFavourNum(0);
        postvo.setThumbNum(0);
        postvo.setUserId(1L);
        postvo.setUrl("https://img.58tg.com/up/allimg/tx28/03102023357064.png");
        postvo.setCreateTime(LocalDateTime.now());
        postvo.setUpdateTime(LocalDateTime.now());
        postvo.setIsDelete(0);

        BeanUtils.copyProperties(postvo, post);

        List<String> postTags = postvo.getTags();
        StringBuilder strTags = new StringBuilder("[");
        for (int i = 0; i < postTags.size(); i++) {
            String tag = postTags.get(i);
//            strTags.append("\"");
            strTags.append(tag);
//            strTags.append("\"");
            if (i != postTags.size() - 1)
                strTags.append(",");
        }
        String tagList = strTags.append("]").toString();
        post.setTags(tagList);

        //设置是否点赞是否收藏为未点赞未收藏
        post.setIsFavour(0);
        post.setIsThumb(0);

//        在xml的配置文件中设置了返回主键id的设置，当插入数据后通过实体类的id可以获取到主键id
        Integer postLine = postMapper.InsertPost(post);
        if (postLine != 1) {
//            log.error("插入帖子失败,请尝试重新插入");
            return "Error";
        }
        Thumb thumb = new Thumb();
        thumb.setUserId(1L);
        thumb.setFlag(0);
        thumb.setPostId(post.getId());
        thumb.setCreateTime(LocalDateTime.now());
        thumb.setUpdateTime(LocalDateTime.now());
        thumb.setIsDelete(0);
        Integer thumbLine = thumbMapper.InsertThumb(thumb);
        if (thumbLine != 1) {
//            log.error("插入帖子失败,请尝试重新插入");
            return "Error";
        }

        Favour favour = new Favour();
        favour.setUserId(1L);
        favour.setFlag(0);
        favour.setPostId(post.getId());
        favour.setCreateTime(LocalDateTime.now());
        favour.setUpdateTime(LocalDateTime.now());
        favour.setIsDelete(0);

        Integer favourLine = favourMapper.InsertFavour(favour);
        if (favourLine != 1) {
//            log.error("插入帖子失败,请尝试重新插入");
            return "Error";
        }
        return "恭喜你，添加成功";
    }

    /**
     * 删除帖子
     *
     * @param post
     * @return
     */
    @Transactional
    public Integer DeleteById(Post post) {
        //1. 校验帖子
        Long postId = post.getId();
        if (postId == null)
            return 0;
        PostVo postVo = postMapper.getOnePostVo(postId);
        if (postVo.getId() == null)
            return 0;

        Integer line = null;

        //2. 校验点赞表,如果存在则删除
        Thumb thumb = new Thumb();
        thumb.setPostId(postVo.getId());
        Thumb mapperThumb = thumbMapper.getThumbByPostId(thumb);
        if (mapperThumb.getId() != null) {
            line = thumbMapper.deleteThumbByPostId(postVo.getId());
            if (line != 1)
                return 0;
        }

        //3. 校验收藏表,如果存在则删除
        Favour favour = new Favour();
        favour.setPostId(postVo.getId());
        Favour mapperFavour = favourMapper.getFavourByPostId(favour);
        if (mapperFavour.getId() != null) {
            line = favourMapper.deleteFavourByPostId(postVo.getId());
            if (line != 1)
                return 0;
        }

        //4. 删除帖子
        line = postMapper.deletePostById(postVo.getId());
        if (line != 1)
            return 0;

        //返回Successful
        return 1;
    }

    /**
     * 更新帖子
     *
     * @param postvo
     * @return
     */
    public String UpdatePost(PostVo postvo) {
        if (postvo == null)
            return "Error";
        //参数校验
        String title = postvo.getTitle();
        List<String> tags = postvo.getTags();
        String content = postvo.getContent();
        String introduce = postvo.getIntroduce();
        Integer favourNum = postvo.getFavourNum();
        Integer thumbNum = postvo.getThumbNum();
        String category = postvo.getCategory();
        if (StringUtil.isBlank(title) || StringUtil.isBlank(content) ||
                StringUtil.isBlank(introduce) || tags == null || tags.size() == 0
                || favourNum == null || thumbNum == null || category == null)
            return "ParamError";

        Post post = new Post();
        BeanUtils.copyProperties(postvo, post);
        //将List集合转化为String类型的List集合存储到数据库当中，骗过数据库存储List集合数据，曲线救国
        StringBuilder strTags = new StringBuilder("[");
        for (int i = 0; i < tags.size(); i++) {
            String tag = tags.get(i);
            strTags.append("\"");
            strTags.append(tag);
            strTags.append("\"");
            if (i != tags.size() - 1)
                strTags.append(",");
        }
        String tagList = strTags.append("]").toString();
        post.setTags(tagList);

        //更新Post帖子
        Integer res = postMapper.updatePostById(post);

        if (res != 1)
            return "Error";
        return "恭喜你,更新成功";
    }
}
