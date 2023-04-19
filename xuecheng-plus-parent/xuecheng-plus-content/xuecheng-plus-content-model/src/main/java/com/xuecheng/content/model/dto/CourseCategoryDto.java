package com.xuecheng.content.model.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xuecheng.content.model.po.CourseCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 课程分类
 * </p>
 *
 * @author itcast
 */
@Data
public class CourseCategoryDto extends CourseCategory implements Serializable {

    private List<CourseCategoryDto> courseCategoryDtos;

}
