package com.xuecheng.content.feignclient;

import com.xuecheng.media.model.dto.UploadFileResultDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class MediaServiceClientFallback  implements MediaServiceClient{
    @Override
    public UploadFileResultDto upload(MultipartFile filedata, String objectName) throws IOException {

        System.out.println("走降级方法了吗。。。");
        return null;
    }
}
