package com.xuecheng.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuecheng.content.model.dto.TeachPlanDto;
import com.xuecheng.content.model.po.Teachplan;

import java.util.List;

/**
 * <p>
 * 课程计划 Mapper 接口
 * </p>
 *
 * @author jiangxc
 */
public interface TeachplanMapper extends BaseMapper<Teachplan> {


    public List<TeachPlanDto> findTeachplanTree(Long courseId);

    public Integer countChildNodes(Long id);

}
