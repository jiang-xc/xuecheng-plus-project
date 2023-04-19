package com.xuecheng.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;

/**
 * <p>
 * 课程基本信息 服务类
 * </p>
 *
 * @author jiangxc
 * @since 2023-04-18
 */
public interface CourseBaseService extends IService<CourseBase> {


    public PageResult<CourseBase> queryPageByCondi(PageParams pageParams, QueryCourseParamsDto CourseParamsDto);

}