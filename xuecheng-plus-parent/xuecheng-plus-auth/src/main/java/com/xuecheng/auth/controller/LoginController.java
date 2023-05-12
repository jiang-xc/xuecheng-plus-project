package com.xuecheng.auth.controller;

import com.xuecheng.base.model.RestResponse;
import com.xuecheng.ucenter.mapper.XcUserMapper;
import com.xuecheng.ucenter.model.dto.SmsCodeParamsDto;
import com.xuecheng.ucenter.model.po.XcUser;
import com.xuecheng.ucenter.service.UserInfoSerive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mr.M
 * @version 1.0
 * @description 测试controller
 * @date 2022/9/27 17:25
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    XcUserMapper userMapper;
    @Autowired
    private UserInfoSerive userInfoSerive;


    @RequestMapping("/login-success")
    public String loginSuccess() {

        return "登录成功";
    }


    @RequestMapping("/user/{id}")
    //@PreAuthorize("hasAuthority('p1')")
    public XcUser getuser(@PathVariable("id") String id) {
        XcUser xcUser = userMapper.selectById(id);
        return xcUser;
    }

    @RequestMapping("/r/r1")
    @PreAuthorize("hasAuthority('p1')")
    public String r1() {
        return "访问r1资源";
    }


    @RequestMapping("/r/r2")
    @PreAuthorize("hasAuthority('p2')")
    public String r2() {
        return "访问r2资源";
    }


    @PostMapping("/register")
    public RestResponse<Boolean> register(@RequestBody SmsCodeParamsDto smsCodeParamsDto){
        userInfoSerive.register(smsCodeParamsDto);
        return new RestResponse<>();
    }

    /**
     *
     * @return
     */
    @PostMapping("/findpassword")
    public RestResponse<Boolean> findPasswdByPhone(@RequestBody SmsCodeParamsDto smsCodeParamsDto){
        userInfoSerive.findPassword(smsCodeParamsDto);
        return new RestResponse<>();
    }




}
