package com.xuecheng.ucenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.ucenter.mapper.XcMenuMapper;
import com.xuecheng.ucenter.mapper.XcUserMapper;
import com.xuecheng.ucenter.model.dto.AuthParamsDto;
import com.xuecheng.ucenter.model.dto.XcUserExt;
import com.xuecheng.ucenter.model.po.XcMenu;
import com.xuecheng.ucenter.model.po.XcUser;
import com.xuecheng.ucenter.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Component
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private XcUserMapper xcUserMapper;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private XcMenuMapper xcMenuMapper;


    @Override
    public UserDetails loadUserByUsername(String str) throws UsernameNotFoundException {
        AuthParamsDto authParamsDto = null;

        try {
            //将认证参数转为AuthParamsDto类型
            authParamsDto = JSON.parseObject(str, AuthParamsDto.class);
        } catch (Exception e) {
            log.info("认证请求不符合项目要求:{}", str);
            throw new RuntimeException("认证请求数据格式不对");
        }
        String authType = authParamsDto.getAuthType();
        AuthService authService = applicationContext.getBean(authType + "_authservice", AuthService.class);

        //执行校验用户身份
        XcUserExt userExt = authService.execute(authParamsDto);

        return getUserInfo(userExt);
    }


    /**
     * 查询用户信息
     *
     * @param xcUser
     * @return
     */
    public UserDetails getUserInfo(XcUserExt xcUser) {
        //用户权限,如果不加报Cannot pass a null GrantedAuthority collection
        //String[] authorities = {"test"};
        List<String> xcMenus = xcMenuMapper.selectPermissionByUserId(xcUser.getId());
        ArrayList<String> permissions = new ArrayList<>();

        //如果查出来的权限为空，添加一个临时权限以免报错
        if (xcMenus.size() == 0 || xcMenus == null) {
            permissions.add("tempPer");
        } else {
            xcMenus.forEach(item -> permissions.add(item));
        }
        String[] permissionsArray = permissions.toArray(new String[0]);

        //创建UserDetails对象,权限信息待实现授权功能时再向UserDetail中加入
        String password = xcUser.getPassword();
        //设置密码为空，防止信息泄露
        xcUser.setPassword(null);
        String userInfo = JSONObject.toJSONString(xcUser);
        UserDetails userDetails = User.withUsername(userInfo).password(password).authorities(permissionsArray).build();
        return userDetails;
    }
}
