package com.xuecheng.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.ucenter.feignclient.CheckCodeClient;
import com.xuecheng.ucenter.mapper.XcUserMapper;
import com.xuecheng.ucenter.model.dto.AuthParamsDto;
import com.xuecheng.ucenter.model.dto.XcUserExt;
import com.xuecheng.ucenter.model.po.XcUser;
import com.xuecheng.ucenter.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * 账号密码验证
 */
@Slf4j
@Service("password_authservice")
public class PasswordAuthServiceImpl implements AuthService {

    @Autowired
    private XcUserMapper xcUserMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    CheckCodeClient checkCodeClient;


    @Override
    public XcUserExt execute(AuthParamsDto authParamsDto) {

        String key = authParamsDto.getCheckcodekey();
        String code = authParamsDto.getCheckcode();


        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(code)) {
            XueChengPlusException.cast("请输入验证码。。。");
        }

        //远程调用验证码服务来校验验证码
        Boolean result = checkCodeClient.verify(key, code);
        if (result == null || !result) {
            XueChengPlusException.cast("验证码有误，请重新输入！");
        }

        String username = authParamsDto.getUsername();
        XcUser user = xcUserMapper.selectOne(new LambdaQueryWrapper<XcUser>().eq(XcUser::getUsername, username));
        if (user == null) {
            XueChengPlusException.cast("用户名有误。。。");
        }
        XcUserExt userExt = new XcUserExt();
        BeanUtils.copyProperties(user, userExt);

        String passwd = user.getPassword();
        String rawpasswd = authParamsDto.getPassword();
        if (!passwordEncoder.matches(rawpasswd, passwd)) {
            XueChengPlusException.cast("密码错误。。。");
        }
        return userExt;
    }
}
