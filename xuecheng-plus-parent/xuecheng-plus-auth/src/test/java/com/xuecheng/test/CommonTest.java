package com.xuecheng.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.ArrayList;
import java.util.List;

//@SpringBootTest
public class CommonTest {

    @Test
    public void test01(){

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            String encode = passwordEncoder.encode("111111");
            System.out.println(encode);
            list.add(encode);
        }

        for (String s : list) {
            boolean matches = passwordEncoder.matches("111111", s);
            System.out.println(matches);
        }


    }
}
