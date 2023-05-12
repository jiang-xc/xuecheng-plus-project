package com.xuecheng.ucenter.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.ucenter.feignclient.CheckCodeClient;
import com.xuecheng.ucenter.mapper.XcUserMapper;
import com.xuecheng.ucenter.mapper.XcUserRoleMapper;
import com.xuecheng.ucenter.model.dto.SmsCodeParamsDto;
import com.xuecheng.ucenter.model.po.XcUser;
import com.xuecheng.ucenter.model.po.XcUserRole;
import com.xuecheng.ucenter.service.UserInfoSerive;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoSerive {

    @Autowired
    CheckCodeClient checkCodeClient;
    @Autowired
    private XcUserMapper xcUserMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private XcUserRoleMapper xcUserRoleMapper;


    @Transactional
    @Override
    public void register(SmsCodeParamsDto smsCodeParamsDto) {
        //1、校验验证码，如果不一致则抛出异常
        Boolean result = checkCodeClient.toverifySmsCode(smsCodeParamsDto.getCellphone(), smsCodeParamsDto.getCheckcode());
        if (result == null || result == false) {
            XueChengPlusException.cast("验证码不正确，请重新输入。。。");
        }

        //2、校验两次密码是否一致，如果不一致则抛出异常
        String passwd = smsCodeParamsDto.getPassword();
        String confirmpwd = smsCodeParamsDto.getConfirmpwd();
        if (StringUtils.isEmpty(passwd) || StringUtils.isEmpty(confirmpwd)) {
            XueChengPlusException.cast("两次密码不一致");
        }

        if (!passwd.equals(confirmpwd)) {
            XueChengPlusException.cast("两次密码不一致");
        }

        //3、校验用户是否存在，如果存在则抛出异常
        LambdaQueryWrapper<XcUser> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isEmpty(smsCodeParamsDto.getUsername())) {
            XueChengPlusException.cast("请输入用户名！");
        } else {
            queryWrapper.eq(XcUser::getUsername, smsCodeParamsDto.getUsername());
            XcUser xcUser = xcUserMapper.selectOne(queryWrapper);
            if (xcUser != null) {
                XueChengPlusException.cast("该用户已存在！");
            }
        }
        //4、向用户表、用户角色关系表添加数据。角色为学生角色。
        XcUser xcUser = new XcUser();
        //对密码加密
        String encodePasswd = passwordEncoder.encode(passwd);

        //BeanUtils.copyProperties(smsCodeParamsDto,xcUser);
        xcUser.setCellphone(smsCodeParamsDto.getCellphone());
        xcUser.setUsername(smsCodeParamsDto.getUsername());
        xcUser.setEmail(smsCodeParamsDto.getEmail());
        xcUser.setNickname(smsCodeParamsDto.getNickname());
        xcUser.setPassword(encodePasswd);
        xcUser.setCreateTime(LocalDateTime.now());
        xcUser.setName("学生");
        xcUser.setStatus("1");
        xcUser.setUtype("101001");

        int i1 = xcUserMapper.insert(xcUser);
        if (i1 < 1) {
            XueChengPlusException.cast("注册失败，请稍后再试。。。");
        }
        XcUserRole xcUserRole = new XcUserRole();
        xcUserRole.setCreateTime(LocalDateTime.now());
        xcUserRole.setUserId(xcUser.getId());
        xcUserRole.setRoleId("17");
        int i2 = xcUserRoleMapper.insert(xcUserRole);
        if (i2 < 1) {
            XueChengPlusException.cast("注册失败，请稍后再试。。。");
        }

    }

    @Override
    @Transactional
    public void findPassword(SmsCodeParamsDto smsCodeParamsDto) {

        //获取手机号
        String phone = smsCodeParamsDto.getCellphone();
        if (StringUtils.isEmpty(phone)) {
            XueChengPlusException.cast("请输入手机号。。。");
        }

        //判断用户是否存在
        LambdaQueryWrapper<XcUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(XcUser::getCellphone, phone);
        List<XcUser> xcUsers = xcUserMapper.selectList(queryWrapper);
        if (xcUsers == null || xcUsers.size() == 0) {
            XueChengPlusException.cast("该用户不存在！");
        }

        //获取验证码
        String checkCode = smsCodeParamsDto.getCheckcode();
        //判断验证码是否正确
        if (StringUtils.isEmpty(checkCode)) {
            XueChengPlusException.cast("请输入验证码。。。");
        }

        Boolean result = checkCodeClient.toverifySmsCode(phone, checkCode);
        if (!result) {
            XueChengPlusException.cast("验证码不正确!");
        }
        //判断两次密码是否一致
        String password = smsCodeParamsDto.getPassword();
        String confirmPwd = smsCodeParamsDto.getConfirmpwd();
        if (password == null || password == "") {
            XueChengPlusException.cast("请输入密码！");
        }

        if (StringUtils.isEmpty(confirmPwd)) {
            XueChengPlusException.cast("两次密码不一致！");
        }

        if (!password.equals(confirmPwd)) {
            XueChengPlusException.cast("两次密码不一致！");
        }

        //将用户密码更新
        String encodePwd = passwordEncoder.encode(password);
        XcUser xcUser = new XcUser();
        BeanUtils.copyProperties(smsCodeParamsDto, xcUser);
        xcUser.setPassword(encodePwd);
        xcUser.setUpdateTime(LocalDateTime.now());

        int updateCount = xcUserMapper.update(xcUser, queryWrapper);
        if (updateCount < 1) {
            XueChengPlusException.cast("密码更新失败，请稍后再试！");
        }

    }
}
