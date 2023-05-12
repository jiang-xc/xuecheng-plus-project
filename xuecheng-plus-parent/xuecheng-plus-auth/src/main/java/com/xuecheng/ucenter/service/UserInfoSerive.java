package com.xuecheng.ucenter.service;

import com.xuecheng.ucenter.model.dto.SmsCodeParamsDto;

public interface UserInfoSerive {

    public void register(SmsCodeParamsDto smsCodeParamsDto);


    public void findPassword(SmsCodeParamsDto smsCodeParamsDto);
}
