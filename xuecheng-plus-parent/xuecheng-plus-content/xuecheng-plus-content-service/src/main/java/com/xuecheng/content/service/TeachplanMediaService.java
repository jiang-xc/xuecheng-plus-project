package com.xuecheng.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuecheng.content.model.dto.BindTeachplanMediaDto;
import com.xuecheng.content.model.po.TeachplanMedia;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jiangxc
 * @since 2023-04-18
 */
public interface TeachplanMediaService extends IService<TeachplanMedia> {

    public TeachplanMedia associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto);


    /**
     * 删除当前课程计划节点下的媒资文件
     * @param teachPlanId
     * @param mediaId
     */
    public void delMediaByTeachPlanIdAndMediaId(Long teachPlanId,String mediaId);

}
