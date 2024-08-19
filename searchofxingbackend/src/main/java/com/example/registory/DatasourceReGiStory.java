package com.example.registory;

import com.example.datasource.DataSource;
import com.example.datasource.ImageDataSource;
import com.example.datasource.PostDataSource;
import com.example.datasource.UserDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源注册Bean
 * 此时使用了注册器模式
 */
@Component
public class DatasourceReGiStory {


    @Autowired
    private PostDataSource postDataSource;

    @Autowired
    private ImageDataSource imageDataSource;

    @Autowired
    private UserDataSource userDataSource;

    Map<String, DataSource> typeDataSourceMap ;

    @PostConstruct
    public void initDataSource()
    {
        typeDataSourceMap = new HashMap(){{
            put("user",userDataSource);
            put("post",postDataSource);
            put("picture",imageDataSource);
            put("front",postDataSource);
            put("backend",postDataSource);
        }};
    }

    /**
     * 根据传入的type来返回对应的数据源
     * @param type
     * @return
     */
    public DataSource getDataSourceByType(String type)
    {
        return typeDataSourceMap.get(type);
    }

}
