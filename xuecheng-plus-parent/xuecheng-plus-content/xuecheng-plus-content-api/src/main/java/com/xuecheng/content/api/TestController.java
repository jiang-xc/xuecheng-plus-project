package com.xuecheng.content.api;


import com.xuecheng.base.exception.CommonError;
import com.xuecheng.base.exception.XueChengPlusException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {



    @PostMapping("/course/test")
    public String testSwag(String arg1){

        if(arg1==null){
            log.info("异常信息为：{}",CommonError.OBJECT_NULL.getErrMessage());
            System.out.println(CommonError.OBJECT_NULL.getErrMessage());
            XueChengPlusException.cast(CommonError.OBJECT_NULL.getErrMessage());
        }

        int i = 1/0;

        return "success";
    }
}
