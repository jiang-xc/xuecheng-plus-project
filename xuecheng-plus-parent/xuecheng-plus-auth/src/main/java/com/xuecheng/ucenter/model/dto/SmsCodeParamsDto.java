package com.xuecheng.ucenter.model.dto;


import lombok.Data;

@Data
public class SmsCodeParamsDto {


    private String cellphone;
    private String username;
    private String email;
    private String nickname;
    private String password;
    private String confirmpwd;
    private String checkcodekey;
    private String checkcode;


}
