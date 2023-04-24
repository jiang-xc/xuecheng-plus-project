package com.xuecheng.content.api;

import com.xuecheng.base.exception.ValidationGroups;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseService;
import com.xuecheng.content.service.impl.CourseBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程基本信息 前端控制器
 * </p>
 *
 * @author jiangxc
 */
@Slf4j
@RestController
//@RequestMapping("courseBase")
public class CourseBaseInfoController {

    @Autowired
    private CourseBaseService courseBaseService;


    @PostMapping("/course/list")
    public PageResult<CourseBase> queryCourseInfoByPage(PageParams pageParams, @RequestBody QueryCourseParamsDto queryCourseParamsDto){

        if(pageParams.getPageNo()==null||pageParams.getPageNo()==0L){
            pageParams.setPageNo(1L);
        }

        if(pageParams.getPageSize()==null||pageParams.getPageSize()==0L){
            pageParams.setPageSize(5L);
        }

        return courseBaseService.queryPageByCondi(pageParams, queryCourseParamsDto);
    }


    @PostMapping("/course")
    public CourseBaseInfoDto addCourseBaseInfoAndMarketInfo(@RequestBody @Validated({ValidationGroups.Inster.class}) AddCourseDto addCourseDto){
        Long companyId = 1232141425L;
        CourseBaseInfoDto courseBaseInfoDto = courseBaseService.addCourseBaseInfoAndMarketInfo(companyId, addCourseDto);

        return courseBaseInfoDto;
    }

    @GetMapping("/course/{courseId}")
    public CourseBaseInfoDto getCourseBaseById(@PathVariable Long courseId){

        return courseBaseService.getCourseBaseInfo(courseId);
    }


    @PutMapping("/course")
    public CourseBaseInfoDto updateCourseBase(@RequestBody @Validated({ValidationGroups.Update.class}) EditCourseDto editCourseDto){
        Long companyId = 1232141425L;
        CourseBaseInfoDto courseBaseInfoDto = courseBaseService.updateCourseBase(companyId, editCourseDto);
        return courseBaseInfoDto;
    }

    /**
     * 删除课程及其相关项
     * @param courseId
     */
    @DeleteMapping("/course/{courseId}")
    public void delCourse(@PathVariable Long courseId){
        courseBaseService.delCourse(courseId);
    }

}
