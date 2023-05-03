package com.xuecheng.content.api;


import com.xuecheng.content.service.CoursePublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 课程发布Controller
 */
@RestController
public class CoursePublishController {

    @Autowired
    private CoursePublishService coursePublishService;



    @ResponseBody
    @PostMapping("/courseaudit/commit/{courseId}")
    public void commitAudit(@PathVariable("courseId") Long courseId){
        Long companyId = 1232141425L;
        coursePublishService.commitAudit(companyId,courseId);
    }
}
