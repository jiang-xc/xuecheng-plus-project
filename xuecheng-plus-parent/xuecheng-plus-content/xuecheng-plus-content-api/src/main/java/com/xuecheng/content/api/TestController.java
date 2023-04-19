package com.xuecheng.content.api;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {



    @PostMapping("/course/test")
    public String testSwag(String arg1){

        return "success";
    }
}
