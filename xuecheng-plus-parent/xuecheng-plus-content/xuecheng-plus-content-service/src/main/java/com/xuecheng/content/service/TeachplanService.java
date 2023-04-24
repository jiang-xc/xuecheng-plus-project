package com.xuecheng.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuecheng.content.model.dto.SaveOrUpdateTeachPlanDto;
import com.xuecheng.content.model.dto.TeachPlanDto;
import com.xuecheng.content.model.po.Teachplan;
import java.util.List;
/**
 * <p>
 * 课程计划 服务类
 * </p>
 *
 * @author jiangxc
 * @since 2023-04-18
 */
public interface TeachplanService extends IService<Teachplan> {


    public List<TeachPlanDto> getTreeNodes(Long courseId);


    public void saveOrUpdateTeachPlan(SaveOrUpdateTeachPlanDto saveOrUpdateTeachPlanDto);

    public void delTeachPlan(Long id);

    public void moveTeachPlan(String moveMode,Long id);


}
