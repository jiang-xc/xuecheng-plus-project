package com.xuecheng.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuecheng.content.model.dto.CoursePreviewDto;
import com.xuecheng.content.model.po.CoursePublish;

/**
 * <p>
 * 课程发布 服务类
 * </p>
 *
 * @author jiangxc
 * @since 2023-04-18
 */
public interface CoursePublishService extends IService<CoursePublish> {


    public CoursePreviewDto getCoursePreviewInfo(Long courseId);


    /**
     * @description 提交审核
     * @param courseId  课程id
     * @return void
     * @author Mr.M
     * @date 2022/9/18 10:31
     */
    public void commitAudit(Long companyId,Long courseId);
}
