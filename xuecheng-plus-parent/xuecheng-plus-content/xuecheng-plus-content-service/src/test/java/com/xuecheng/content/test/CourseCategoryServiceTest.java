package com.xuecheng.content.test;


import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CourseCategoryServiceTest {


    @Autowired
    private CourseCategoryMapper courseCategoryMapper;

    @Test
    public void test(){

        List<CourseCategoryDto> categoryDtos = courseCategoryMapper.queryCategoryChildNodes("1");
        for (CourseCategoryDto categoryDto : categoryDtos) {
            System.out.println(categoryDto);
        }

    }
}
