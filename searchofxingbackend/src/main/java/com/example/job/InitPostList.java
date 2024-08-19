package com.example.job;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.mapper.PostMapper;
import com.example.pojo.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 单次任务:爬虫初始化帖子列表
 */

// 开启了Component后，每次执行项目都会执行一次run
//@Component
@Slf4j
public class InitPostList implements CommandLineRunner {

    @Autowired
    private PostMapper postMapper;

    /**
     * 单次任务执行函数
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        String json = "{\"current\":1,\"pageSize\":8,\"sortField\":\"createTime\",\"sortOrder\":\"descend\",\"category\":\"文章\",\"reviewStatus\":1}";
        String result = HttpRequest.post("https://www.code-nav.cn/api/post/search/page/vo")
                .body(json)
                .execute().body();

        Map<String,Object> DataMap = JSONUtil.toBean(result, Map.class);

        List<Post> postList = new ArrayList<>();
        Integer code = (Integer) DataMap.get("code");
        //响应成功
        if(code == 0)
        {
            JSONObject data = (JSONObject) DataMap.get("data");
            JSONArray records = (JSONArray) data.get("records");

            System.out.println(records);

            for(int i=0;i< records.size();i++)
            {
                JSONObject res = (JSONObject) records.get(i);
                Post post = new Post();
                post.setContent(res.getStr("content"));
                post.setTitle(res.getStr("title"));

                JSONArray tags = (JSONArray) res.get("tags");
                String str = "";
                for(int j=0;j< tags.size();j++) str+= tags.get(j) +",";
//                post.setTags(str);

                post.setUserId(1L);

                post.setUpdateTime(LocalDateTime.now());
                post.setCreateTime(LocalDateTime.now());
                postList.add(post);
            }
        }
        System.out.println(postList);
        for(int i=0;i<postList.size();i++)
        {
            boolean res = postMapper.AddOnePost(postList.get(i));
            if(!res)
                System.out.println("插入失败");
            else
                System.out.println("插入成功");
        }

        if( postList!=null || postList.size()>0)
            log.info("帖子列表插入成功,条数:{}",postList.size());
        else
            log.error("帖子列表插入失败");
    }
}
