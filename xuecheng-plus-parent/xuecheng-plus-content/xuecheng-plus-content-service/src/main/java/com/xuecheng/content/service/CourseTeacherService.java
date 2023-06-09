package com.xuecheng.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuecheng.content.model.po.CourseTeacher;
import java.util.List;


/**
 * <p>
 * 课程-教师关系表 服务类
 * </p>
 *
 * @author jiangxc
 * @since 2023-04-18
 */
public interface CourseTeacherService extends IService<CourseTeacher> {

    List<CourseTeacher> queryCourseTeacherInfo(Long courseId);

    CourseTeacher saveOrUpdateTeacherInfo(CourseTeacher courseTeacher);

    public void delTeacherInfo(Long courseId,Long teacherId);
}
