package com.example.datasource;

import com.example.pojo.vo.CommonVo;

/**
 * 数据源接口，这里我们可以使用适配器模式
 */
public interface DataSource {

    /**
     * 制定数据源接口规范
     * @param text
     * @param pageNum
     * @param pageSize
     * @return
     */
     CommonVo GetSource(String text,int pageNum,int pageSize,String category) throws Exception;
}
