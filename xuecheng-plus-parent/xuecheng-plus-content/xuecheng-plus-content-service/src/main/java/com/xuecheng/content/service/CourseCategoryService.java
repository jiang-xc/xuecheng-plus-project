package com.xuecheng.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuecheng.content.model.dto.CourseCategoryDto;
import com.xuecheng.content.model.po.CourseCategory;

import java.util.List;

/**
 * <p>
 * 课程分类 服务类
 * </p>
 *
 * @author jiangxc
 * @since 2023-04-18
 */
public interface CourseCategoryService extends IService<CourseCategory> {

    List<CourseCategoryDto> queryCategoryChildNodes(String id);
}
