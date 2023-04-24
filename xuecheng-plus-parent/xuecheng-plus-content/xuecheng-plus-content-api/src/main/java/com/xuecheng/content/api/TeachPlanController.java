package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.SaveOrUpdateTeachPlanDto;
import com.xuecheng.content.model.dto.TeachPlanDto;
import com.xuecheng.content.service.TeachplanService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeachPlanController {

    @Autowired
    TeachplanService teachplanService;


    @ApiOperation("查询课程计划树形结构")
    @GetMapping("teachplan/{courseId}/tree-nodes")
    public List<TeachPlanDto> getTreeNodes(@PathVariable Long courseId) {

        return teachplanService.getTreeNodes(courseId);
    }


    /**
     * 新增或修改课程计划
     * @param saveOrUpdateTeachPlanDto
     */
    @PostMapping("/teachplan")
    public void saveOrUpdateTeachPlan(@RequestBody SaveOrUpdateTeachPlanDto saveOrUpdateTeachPlanDto){

        teachplanService.saveOrUpdateTeachPlan(saveOrUpdateTeachPlanDto);
    }

    /**
     * 删除课程计划
     * @param id 课程计划id
     */
    @DeleteMapping("/teachplan/{id}")
    public void delTeachPlan(@PathVariable Long id){

        teachplanService.delTeachPlan(id);
    }


    /**
     * 课程计划的上下移动
     * @param moveMode
     * @param id
     */
    @PostMapping("/teachplan/{moveMode}/{id}")
    public void moveTeachPlan(@PathVariable("moveMode") String moveMode, @PathVariable("id") Long id){

        teachplanService.moveTeachPlan(moveMode,id);
    }

}
