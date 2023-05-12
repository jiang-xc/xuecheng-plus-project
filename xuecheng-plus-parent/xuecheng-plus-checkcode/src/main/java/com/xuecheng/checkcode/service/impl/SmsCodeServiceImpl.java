package com.xuecheng.checkcode.service.impl;

import com.xuecheng.checkcode.service.SmsCodeService;
import com.xuecheng.checkcode.util.SendSmsUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SmsCodeServiceImpl implements SmsCodeService {

    @Autowired
    private SendSmsUtil sendSmsUtil;
    @Autowired
    private RedisCheckCodeStore redisCheckCodeStore;


    @Override
    public void sendSmsCode(String phone) {

        sendSmsUtil.sendSms(phone);

    }

    @Override
    public Boolean toVerifySmsCode(String key, String value) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        String s = redisCheckCodeStore.get(key);
        if (StringUtils.isEmpty(s)) {
            return false;
        } else {
            if (s.equals(value)) {
                redisCheckCodeStore.remove(s);
                return true;
            }else {
                return false;
            }
        }

    }


}
