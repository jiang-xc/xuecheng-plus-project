package com.xuecheng.checkcode.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "xczx.sms")
public class SmsCodeProperties {

    private String accessKeyId;
    //accessKeyId secret
    private String secret;
    //访问的域名
    private String endpoint;
    //短信签名名称
    private String signName;
    //模板名称
    private String templateCode;

}
