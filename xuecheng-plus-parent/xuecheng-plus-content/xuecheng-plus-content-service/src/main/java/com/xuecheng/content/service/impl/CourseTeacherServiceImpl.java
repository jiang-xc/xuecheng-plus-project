package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.mapper.CourseTeacherMapper;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 课程-教师关系表 服务实现类
 * </p>
 *
 * @author jiangxc
 */
@Slf4j
@Service
public class CourseTeacherServiceImpl extends ServiceImpl<CourseTeacherMapper, CourseTeacher> implements CourseTeacherService {

    @Autowired
    private CourseTeacherMapper courseTeacherMapper;

    @Override
    public List<CourseTeacher> queryCourseTeacherInfo(Long courseId) {
        LambdaQueryWrapper<CourseTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(courseId!=null,CourseTeacher::getCourseId,courseId);
        return courseTeacherMapper.selectList(queryWrapper);
    }

    @Transactional
    @Override
    public CourseTeacher saveOrUpdateTeacherInfo(CourseTeacher courseTeacher) {
        //通过课程老师的id判断新增还是修改
        if (courseTeacher.getId()==null){
            System.out.println("================="+LocalDateTime.now());
            courseTeacher.setCreateDate(LocalDateTime.now());
            int i1 = courseTeacherMapper.insert(courseTeacher);
            if(i1<=0){
                XueChengPlusException.cast("新增师资信息异常。。。");
            }
        }else {
            int i2 = courseTeacherMapper.updateById(courseTeacher);
            if(i2<=0){
                XueChengPlusException.cast("修改师资信息异常。。。");
            }
        }
        CourseTeacher resTeacher = queryTeacherInfoById(courseTeacher.getCourseId());
        return resTeacher;
    }


    public CourseTeacher queryTeacherInfoById(Long teacherId){
       return courseTeacherMapper.selectById(teacherId);
    }


    @Override
    public void delTeacherInfo(Long courseId, Long teacherId) {
        LambdaQueryWrapper<CourseTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(courseId!=null,CourseTeacher::getCourseId,courseId);
        queryWrapper.eq(teacherId!=null,CourseTeacher::getId,teacherId);
        courseTeacherMapper.delete(queryWrapper);
    }

}
