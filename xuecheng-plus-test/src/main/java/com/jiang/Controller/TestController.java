package com.jiang.Controller;


import com.jiang.po.CourseBase;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Api(value = "课程信息管理接口",tags = "课程信息管理接口")
@RestController
public class TestController {


    @RequestMapping("/test")
    public CourseBase test(String str){
        CourseBase courseBase = new CourseBase();
        courseBase.setCreateDate(LocalDateTime.now());
        return courseBase;

    }
}
