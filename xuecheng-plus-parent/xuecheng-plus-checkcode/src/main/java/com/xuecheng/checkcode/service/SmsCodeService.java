package com.xuecheng.checkcode.service;

public interface SmsCodeService {

    /**
     * 发送短信验证码
     * @param phone 手机号
     */
    public void sendSmsCode(String phone);

    /**
     * 校验短信验证码
     * @param key 用户手机号
     * @param value
     * @return
     */
    public Boolean toVerifySmsCode(String key,String value);

}
