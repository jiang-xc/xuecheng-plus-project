package com.xuecheng.media;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FilterInputStream;

@Slf4j
@SpringBootTest
public class BigFileUploadTest {

    @Autowired
    private MinioClient minioClient;

    @Test
    public void test() {
        //61f227da62a46afe3cc134da10a3872e
        //0
        //video
        //getChunkFileFolderPath()
        //6/1/61f227da62a46afe3cc134da10a3872e/chunk/0
        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket("video").object("test1.jpg").build();

        try {
            FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
            System.out.println(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("===============,{}",e.getMessage());
        }
        System.out.println("11111");

    }

    private String getChunkFileFolderPath(String fileMd5) {
        return fileMd5.substring(0, 1) + "/" + fileMd5.substring(1, 2) + "/" + fileMd5 + "/" + "chunk" + "/";
    }

}
