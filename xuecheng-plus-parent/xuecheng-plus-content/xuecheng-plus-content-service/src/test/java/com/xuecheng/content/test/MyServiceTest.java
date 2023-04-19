package com.xuecheng.content.test;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.model.po.CourseBase;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MyServiceTest {


    @Autowired
    private CourseBaseMapper courseBaseMapper;

    @Test
    public void test01(){

        //CourseBase courseBase = courseBaseMapper.selectById(241L);
        //Assertions.assertNotNull(courseBase);
        //courseBaseMapper.selectList()
        //QueryWrapper<CourseBase> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<CourseBase> wrapper = new LambdaQueryWrapper<>();
        //wrapper.like(true,CourseBase::getName,"spring");
        wrapper.like(CourseBase::getName, "spring");
        PageParams pageParams = new PageParams();
        pageParams.setPageNo(2L);
        pageParams.setPageSize(5L);

        Page<CourseBase> page = new Page<>(pageParams.getPageNo(),pageParams.getPageSize());

        Page<CourseBase> page22 = courseBaseMapper.selectPage(page, wrapper);
        List<CourseBase> records = page22.getRecords();

        for (CourseBase record : records) {
            System.out.println(record);
        }


    }
}
