package com.xuecheng.checkcode.controller;


import com.xuecheng.checkcode.model.SmsCodeParamsDto;
import com.xuecheng.checkcode.service.SmsCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SmsCodeController {

    @Autowired
    private SmsCodeService smsCodeService;

    @PostMapping("/phone")
    public void sendSmsCode(@RequestParam("param1") String phone) {
        smsCodeService.sendSmsCode(phone);
    }

    /**
     * 校验短信验证码
     *
     * @param key  用户手机号
     * @param code redis value
     * @return
     */
    @PostMapping("/sms/verify")
    public Boolean toverifySmsCode(@RequestParam("key") String key, @RequestParam("code") String code) {
        return smsCodeService.toVerifySmsCode(key, code);
    }

}
