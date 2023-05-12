package com.xuecheng.checkcode.util;


import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teautil.models.RuntimeOptions;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.checkcode.config.SmsCodeProperties;
import com.xuecheng.checkcode.model.SmsCodeParamsDto;
import com.xuecheng.checkcode.service.impl.RedisCheckCodeStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
public class SendSmsUtil {

    @Autowired
    private Client client;
    @Autowired
    private SmsCodeProperties smsCodeProperties;

    @Autowired
    private RedisCheckCodeStore redisCheckCodeStore;


    /**
     * 发送短信
     */
    public void sendSms(String phone){

        String code = getRandomCode();
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(phone)
                .setSignName(smsCodeProperties.getSignName())
                .setTemplateCode(smsCodeProperties.getTemplateCode())
                .setTemplateParam("{\"code\":"+"\""+code+"\"}");

        // 复制代码运行请自行打印 API 的返回值
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = client.sendSmsWithOptions(sendSmsRequest, new RuntimeOptions());
            log.info("响应信息为:{}",sendSmsResponse.getBody().getMessage());
            String resultCode = sendSmsResponse.getBody().getCode();
            if("OK".equals(resultCode)){
                //将验证码存入redis,2分钟内有效//暂时写为24小时
                redisCheckCodeStore.set(phone,code,86400);
            }else {
                XueChengPlusException.cast("短信服务异常，请稍后再试。。。");
            }



        } catch (Exception e) {
            log.error("发送短信验证码异常。。。{}",e.getMessage());
            e.printStackTrace();
            XueChengPlusException.cast("短信服务异常，请稍后再试");
        }
    }


    /*public void VerifySmsCode(SmsCodeParamsDto paramsDto){
        //1、校验验证码，如果不一致则抛出异常
        String checkcode = paramsDto.getCheckcode();
        redisCheckCodeStore.
        //2、校验两次密码是否一致，如果不一致则抛出异常
        //3、校验用户是否存在，如果存在则抛出异常
        //4、向用户表、用户角色关系表添加数据。角色为学生角色。



    }*/


    /**
     * 生成一个4位随机数字
     * @return
     */
    private String getRandomCode(){
        Random random = new Random();
        //生成一个0到8999之间的随机整数，再加上1000，就可以得到一个6位随机数字
        int randomNumber = random.nextInt(900000) + 100000;
        String randomString = String.valueOf(randomNumber);
        return randomString;
    }

}
