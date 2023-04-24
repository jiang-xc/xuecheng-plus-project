package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.model.dto.SaveOrUpdateTeachPlanDto;
import com.xuecheng.content.model.dto.TeachPlanDto;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.service.TeachplanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Function;

/**
 * <p>
 * 课程计划 服务实现类
 * </p>
 *
 * @author jiangxc
 */
@Slf4j
@Service
public class TeachplanServiceImpl extends ServiceImpl<TeachplanMapper, Teachplan> implements TeachplanService {

    @Autowired
    private TeachplanMapper teachplanMapper;

    @Override
    public List<TeachPlanDto> getTreeNodes(Long courseId) {


        return teachplanMapper.findTeachplanTree(courseId);
    }

    @Override
    public void saveOrUpdateTeachPlan(SaveOrUpdateTeachPlanDto saveOrUpdateTeachPlanDto) {
        Long id = saveOrUpdateTeachPlanDto.getId();

        if (id == null) {
            //新增
            Integer groupBy = getCount(saveOrUpdateTeachPlanDto.getParentid(), saveOrUpdateTeachPlanDto.getCourseId());
            Teachplan teachplan = new Teachplan();
            BeanUtils.copyProperties(saveOrUpdateTeachPlanDto, teachplan);
            teachplan.setOrderby(groupBy);
            int result = teachplanMapper.insert(teachplan);
            if (result <= 0) {
                XueChengPlusException.cast("数据插入数据库异常");
            }
        } else {
            //修改
            Teachplan teachplanNew = teachplanMapper.selectById(id);
            BeanUtils.copyProperties(saveOrUpdateTeachPlanDto, teachplanNew);
            int i = teachplanMapper.updateById(teachplanNew);
            if (i <= 0) {
                XueChengPlusException.cast("数据插入数据库异常");
            }
        }
    }

    @Transactional
    @Override
    public void delTeachPlan(Long id) {
        Integer childNodes = teachplanMapper.countChildNodes(id);
        //通过有无子节点来判断是否为章节
        if (childNodes != null && childNodes > 0) {
            XueChengPlusException.cast("该章节下还有子节点，暂不能删除！");
        }

        //System.out.println("==走着了吗==");

        //删除之前更新该小节下的所有排序字段比他大的课程计划的排序字段
        updateOrderBy(id);
        int i = teachplanMapper.deleteById(id);
        if(i<=0){
            XueChengPlusException.cast("删除节点出现异常。。。");
        }
    }

    @Override
    public void moveTeachPlan(String moveMode, Long id) {
        Teachplan teachplan = teachplanMapper.selectById(id);
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        //如果该节点的orderby
        if("movedown".equals(moveMode)){
            //找到该节点的下个节点courseid+parentid+(orderby+1)
            //teachplan.setOrderby(teachplan.getOrderby()+1);
            queryWrapper.eq(!StringUtils.isEmpty(teachplan.getCourseId()),Teachplan::getCourseId,teachplan.getCourseId());
            queryWrapper.eq(!StringUtils.isEmpty(teachplan.getParentid()),Teachplan::getParentid,teachplan.getParentid());
            queryWrapper.eq(!StringUtils.isEmpty(teachplan.getOrderby()),Teachplan::getOrderby,teachplan.getOrderby()+1);
            Teachplan nextTeachplan = teachplanMapper.selectOne(queryWrapper);
            if(nextTeachplan!=null){
                nextTeachplan.setOrderby(nextTeachplan.getOrderby()-1);
                int i1 = teachplanMapper.updateById(nextTeachplan);
                if (i1<=0){
                    XueChengPlusException.cast("更新课程计划排序字段异常。。。");
                }
                teachplan.setOrderby(teachplan.getOrderby()+1);
                teachplanMapper.updateById(teachplan);
            }
        }else if("moveup".equals(moveMode)){
            //找到该节点的上个节点courseid+parentid+(orderby-1)

            queryWrapper.eq(!StringUtils.isEmpty(teachplan.getCourseId()),Teachplan::getCourseId,teachplan.getCourseId());
            queryWrapper.eq(!StringUtils.isEmpty(teachplan.getParentid()),Teachplan::getParentid,teachplan.getParentid());
            queryWrapper.eq(!StringUtils.isEmpty(teachplan.getOrderby()),Teachplan::getOrderby,teachplan.getOrderby()-1);
            Teachplan preTeachplan = teachplanMapper.selectOne(queryWrapper);
            if(preTeachplan==null){
                System.out.println("=========走了吗===========");
                //teachplan.setOrderby(1);
            }else {
                preTeachplan.setOrderby(preTeachplan.getOrderby()+1);
                int i2 = teachplanMapper.updateById(preTeachplan);
                if (i2<=0){
                    XueChengPlusException.cast("更新课程计划排序字段异常。。。");
                }
                //不等于0才更新数据库
                teachplan.setOrderby(teachplan.getOrderby()-1);
                teachplanMapper.updateById(teachplan);
            }
        }
    }



    /**
     * 更新该小节下的所有排序字段比他大的课程计划的排序字段
     * @param id
     */
    public void updateOrderBy(Long id){
        Teachplan teachplan = teachplanMapper.selectById(id);
        Integer orderby = teachplan.getOrderby();
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getParentid,teachplan.getParentid());
        queryWrapper.eq(Teachplan::getCourseId,teachplan.getCourseId());
        queryWrapper.gt(Teachplan::getOrderby,orderby);
        List<Teachplan> teachplanList = teachplanMapper.selectList(queryWrapper);
        if(!CollectionUtils.isEmpty(teachplanList)){

            teachplanList.stream().forEach(item->{
                item.setOrderby(item.getOrderby()-1);;
                teachplanMapper.updateById(item);
            });
        }
    }


    /**
     * 获取最新的排序字段orderBy
     *
     * @param parentId 上级id
     * @param courseId 课程标识
     * @return 计算好的最新排序count
     */
    private Integer getCount(Long parentId, Long courseId) {
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getParentid, parentId);
        queryWrapper.eq(Teachplan::getCourseId, courseId);
        Integer count = teachplanMapper.selectCount(queryWrapper);
        return (count + 1);
    }


}
