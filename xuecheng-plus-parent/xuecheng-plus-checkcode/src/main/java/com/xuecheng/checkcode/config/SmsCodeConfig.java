package com.xuecheng.checkcode.config;


import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(SmsCodeProperties.class)
@Configuration
public class SmsCodeConfig {




    @Bean
    public Client createClient(SmsCodeProperties smsCodeProperties) throws Exception {

        Config config = new Config()
                //AccessKey ID
                .setAccessKeyId(smsCodeProperties.getAccessKeyId())
                //AccessKey Secret
                .setAccessKeySecret(smsCodeProperties.getSecret());
        // 访问的域名
        config.endpoint = smsCodeProperties.getEndpoint();
        return new Client(config);
    }

}
