package com.xuecheng.content.test;

import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.model.dto.TeachPlanDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TeachPlanTest {


    @Autowired
    private TeachplanMapper teachplanMapper;

    @Test
    public void test(){

        List<TeachPlanDto> teachplanTree = teachplanMapper.findTeachplanTree(117L);
        teachplanTree.forEach(System.out::println);

    }
}
