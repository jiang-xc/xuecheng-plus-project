package com.xuecheng.content.api;


import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import com.xuecheng.content.service.TeachplanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * 师资信息
 */
@RestController
public class TeacherInfoController {


    @Autowired
    private CourseTeacherService courseTeacherService;

    /**
     * 查询该课程师资列表
     * @param courseId 课程id
     * @return
     */
    @GetMapping("/courseTeacher/list/{courseId}")
    public List<CourseTeacher> queryCourseTeacherInfo(@PathVariable Long courseId){
       return courseTeacherService.queryCourseTeacherInfo(courseId);
    }

    @PostMapping("/courseTeacher")
    public CourseTeacher saveTeacherInfo(@RequestBody CourseTeacher courseTeacher){

        return courseTeacherService.saveOrUpdateTeacherInfo(courseTeacher);
    }

    @PutMapping("/courseTeacher")
    public CourseTeacher updateTeacherInfo(@RequestBody CourseTeacher courseTeacher){

        return courseTeacherService.saveOrUpdateTeacherInfo(courseTeacher);
    }

    @DeleteMapping("/courseTeacher/course/{courseId11}/{teacherId22}")
    public void delTeacherInfo(@PathVariable("courseId11") Long courseId,@PathVariable("teacherId22") Long teacherId){
        System.out.println("courseId="+courseId);
        System.out.println("teacherId="+teacherId);
        courseTeacherService.delTeacherInfo(courseId,teacherId);
    }


}
