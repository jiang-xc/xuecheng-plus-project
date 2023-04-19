package com.jiang.Controller;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "课程信息管理接口",tags = "课程信息管理接口")
@RestController
public class TestController {


    @RequestMapping("/test")
    public String test(String str){
        return "success";

    }
}
