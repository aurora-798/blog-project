package com.example.controller;

import com.example.design.Facade;
import com.example.pojo.vo.CommonRequest;
import com.example.pojo.vo.CommonVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于接受前端发送的Tab栏切换的查询数据的请求的Controller
 */
@RestController
@Slf4j
@RequestMapping("/searchApi/common")
public class CommonController {

    //尽量不要在Controller中写太多的逻辑代码，要有一个把这些代码抽象成一个类的方法的这种思想。

    @Autowired
    private Facade facade;


    @PostMapping("/active/vo")
    public CommonVo getAllData(@RequestBody CommonRequest commonRequest) throws Exception {
        CommonVo commonVo = facade.getFacade(commonRequest);
        if(commonVo == null)
            log.error("Tab数据请求失败");
        return commonVo;
    }
}
