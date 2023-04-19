package com.xuecheng.content.api;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseService;
import com.xuecheng.content.service.impl.CourseBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
