package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.mapper.TeachplanMediaMapper;
import com.xuecheng.content.model.dto.BindTeachplanMediaDto;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import com.xuecheng.content.service.TeachplanMediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jiangxc
 */
@Slf4j
@Service
public class TeachplanMediaServiceImpl extends ServiceImpl<TeachplanMediaMapper, TeachplanMedia> implements TeachplanMediaService {

    @Autowired
    private TeachplanMediaMapper teachplanMediaMapper;
    @Autowired
    private TeachplanMapper teachplanMapper;


    @Override
    public TeachplanMedia associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto) {

        //教学计划id
        Long teachplanId = bindTeachplanMediaDto.getTeachplanId();
        Teachplan teachplan = teachplanMapper.selectById(teachplanId);
        if(teachplan==null){
            XueChengPlusException.cast("教学计划不存在");
        }
        Integer grade = teachplan.getGrade();
        if(grade!=2){
            XueChengPlusException.cast("只允许第二级教学计划绑定媒资文件");
        }

        //先删除该教学计划下的媒资文件
        teachplanMediaMapper.delete(new LambdaQueryWrapper<TeachplanMedia>().eq(TeachplanMedia::getTeachplanId,bindTeachplanMediaDto.getTeachplanId()));
        TeachplanMedia teachplanMedia = new TeachplanMedia();
        teachplanMedia.setMediaId(bindTeachplanMediaDto.getMediaId());
        teachplanMedia.setTeachplanId(bindTeachplanMediaDto.getTeachplanId());
        teachplanMedia.setCourseId(teachplan.getCourseId());
        teachplanMedia.setMediaFilename(bindTeachplanMediaDto.getFileName());
        teachplanMedia.setCreateDate(LocalDateTime.now());
        int result = teachplanMediaMapper.insert(teachplanMedia);
        if(result<=0){
            XueChengPlusException.cast("保存媒资文件失败。。。");
        }

        return teachplanMedia;
    }

    @Override
    public void delMediaByTeachPlanIdAndMediaId(Long teachPlanId, String mediaId) {
        LambdaQueryWrapper<TeachplanMedia> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeachplanMedia::getTeachplanId,teachPlanId);
        queryWrapper.eq(TeachplanMedia::getMediaId,mediaId);

        int result = teachplanMediaMapper.delete(queryWrapper);
        if(result<=0){
            XueChengPlusException.cast("删除媒资文件失败。。。");
        }
    }
}
