package com.jiang.Controller;


import com.jiang.po.CourseBase;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Api(value = "课程信息管理接口",tags = "课程信息管理接口")
@RestController
@Slf4j
public class TestController {


    @RequestMapping("/test")
    public CourseBase test(String str){
        CourseBase courseBase = new CourseBase();
        courseBase.setCreateDate(LocalDateTime.now());

        log.error("11111131313");
        log.info("info 级别日志");
        log.warn("warn 级别日志");
        log.debug("error 级别日志");

        int i = 1/0;

        return courseBase;

    }


    public static void main(String[] args) {
        log.error("11111131313");
        log.info("info 级别日志");
        log.warn("warn 级别日志");
        log.debug("error 级别日志");
    }
}
