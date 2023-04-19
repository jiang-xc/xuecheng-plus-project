package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程基本信息 服务实现类
 * </p>
 *
 * @author jiangxc
 */
@Slf4j
@Service
public class CourseBaseServiceImpl extends ServiceImpl<CourseBaseMapper, CourseBase> implements CourseBaseService {

    @Autowired
    private CourseBaseMapper courseBaseMapper;

    public PageResult<CourseBase> queryPageByCondi(PageParams pageParams, QueryCourseParamsDto CourseParamsDto){

        //分页条件数据
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(),pageParams.getPageSize());
        //封装条件查询数据
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();

        //课程名称，采用模糊搜索
        queryWrapper.like(StringUtils.isNotEmpty(CourseParamsDto.getCourseName()),CourseBase::getName,CourseParamsDto.getCourseName());
        //审核状态
        queryWrapper.eq(StringUtils.isNotEmpty(CourseParamsDto.getAuditStatus()),CourseBase::getAuditStatus,CourseParamsDto.getAuditStatus());
        //发布状态
        queryWrapper.eq(StringUtils.isNotEmpty(CourseParamsDto.getPublishStatus()),CourseBase::getStatus,CourseParamsDto.getPublishStatus());

        Page<CourseBase> courseBasePage = courseBaseMapper.selectPage(page, queryWrapper);

        PageResult<CourseBase> pageResult = new PageResult<>(courseBasePage.getRecords(),courseBasePage.getTotal(),courseBasePage.getPages(),courseBasePage.getSize());

        return pageResult;
    }
}
