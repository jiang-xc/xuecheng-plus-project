package com.xuecheng.content;

import com.xuecheng.content.config.MultipartSupportConfig;
import com.xuecheng.content.feignclient.MediaServiceClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@SpringBootTest
public class FeignClientTest {

    @Autowired
    private MediaServiceClient mediaServiceClient;


    //远程调用，上传文件
    @Test
    public void test() throws IOException {

        MultipartFile multipartFile = MultipartSupportConfig.getMultipartFile(new File("D:\\develop\\upload\\117.html"));
        mediaServiceClient.upload(multipartFile,"course/117.html");
    }
}
