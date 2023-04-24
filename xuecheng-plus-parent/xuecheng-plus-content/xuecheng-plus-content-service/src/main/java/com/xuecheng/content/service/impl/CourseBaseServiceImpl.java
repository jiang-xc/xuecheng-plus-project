package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.*;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.*;
import com.xuecheng.content.service.CourseBaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    @Autowired
    private CourseMarketMapper courseMarketMapper;
    @Autowired
    private CourseCategoryMapper courseCategoryMapper;
    @Autowired
    private TeachplanMapper teachplanMapper;
    @Autowired
    private TeachplanMediaMapper teachplanMediaMapper;
    @Autowired
    private CourseTeacherMapper courseTeacherMapper;


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

    @Transactional
    @Override
    public CourseBaseInfoDto addCourseBaseInfoAndMarketInfo(Long companyId,AddCourseDto dto) {
        //合法性校验
        /*if (StringUtils.isBlank(dto.getName())) {
            throw new RuntimeException("课程名称为空");
        }

        if (StringUtils.isBlank(dto.getMt())) {
            throw new RuntimeException("课程分类为空");
        }

        if (StringUtils.isBlank(dto.getTags())) {
            throw new XueChengPlusException(CommonError.REQUEST_NULL.getErrMessage());
        }

        if (StringUtils.isBlank(dto.getSt())) {
            throw new RuntimeException("课程分类为空");
        }

        if (StringUtils.isBlank(dto.getGrade())) {
            throw new RuntimeException("课程等级为空");
        }

        if (StringUtils.isBlank(dto.getTeachmode())) {
            throw new RuntimeException("教育模式为空");
        }

        if (StringUtils.isBlank(dto.getUsers())) {
            throw new RuntimeException("适应人群为空");
        }

        if (StringUtils.isBlank(dto.getCharge())) {
            throw new RuntimeException("收费规则为空");
        }*/


        //保存数据到课程基本信息表
        //新增对象
        CourseBase courseBaseNew = new CourseBase();
        //将填写的课程信息赋值给新增对象
        BeanUtils.copyProperties(dto,courseBaseNew);
        //设置审核状态
        courseBaseNew.setAuditStatus("202002");
        //设置发布状态
        courseBaseNew.setStatus("203001");
        //机构id
        courseBaseNew.setCompanyId(companyId);
        //添加时间
        courseBaseNew.setCreateDate(LocalDateTime.now());
        //插入课程基本信息表
        int insert = courseBaseMapper.insert(courseBaseNew);
        if(insert<=0){
            throw new RuntimeException("新增课程基本信息失败");
        }

        //保存数据到课程营销信息表
        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(dto,courseMarket);
        //设置课程ID
        courseMarket.setId(courseBaseNew.getId());
        saveOrUpdateCourseMarket(courseMarket);

        //查询课程营销信息和课程基本信息
        CourseBaseInfoDto courseBaseInfoReult = getCourseBaseInfo(courseBaseNew.getCompanyId());

        return courseBaseInfoReult;
    }


    private int saveOrUpdateCourseMarket(CourseMarket courseMarketNew){
        //收费规则
        String charge = courseMarketNew.getCharge();
        if(StringUtils.isBlank(charge)){
            XueChengPlusException.cast("收费规则没有选择");
        }
        //收费规则为收费
        if(charge.equals("201001")){
            if(courseMarketNew.getPrice() == null || courseMarketNew.getPrice().floatValue()<=0){
                XueChengPlusException.cast("课程为收费价格不能为空且必须大于0");
            }
        }
        //根据id从课程营销表查询
        CourseMarket courseMarketObj = courseMarketMapper.selectById(courseMarketNew.getId());
        if(courseMarketObj == null){
            return courseMarketMapper.insert(courseMarketNew);
        }else{
            BeanUtils.copyProperties(courseMarketNew,courseMarketObj);
            courseMarketObj.setId(courseMarketNew.getId());
            return courseMarketMapper.updateById(courseMarketObj);
        }
    }
    //根据课程id查询课程基本信息，包括基本信息和营销信息
    public CourseBaseInfoDto getCourseBaseInfo(Long courseId){

        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if(courseBase == null){
            return null;
        }
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase,courseBaseInfoDto);
        if(courseMarket != null){
            BeanUtils.copyProperties(courseMarket,courseBaseInfoDto);
        }

        //查询分类名称
        CourseCategory courseCategoryBySt = courseCategoryMapper.selectById(courseBase.getSt());
        courseBaseInfoDto.setStName(courseCategoryBySt.getName());
        CourseCategory courseCategoryByMt = courseCategoryMapper.selectById(courseBase.getMt());
        courseBaseInfoDto.setMtName(courseCategoryByMt.getName());

        return courseBaseInfoDto;

    }

    @Transactional
    @Override
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto) {
        //课程id
        Long courseId = dto.getId();
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if(courseBase==null){
            XueChengPlusException.cast("课程不存在");
        }

        //校验本机构只能修改本机构的课程
        if(!courseBase.getCompanyId().equals(companyId)){
            XueChengPlusException.cast("本机构只能修改本机构的课程");
        }

        //EditCourseDto courseDto = new EditCourseDto();
        //课程基本信息修改
        CourseBase cBase = new CourseBase();
        BeanUtils.copyProperties(dto,cBase);
        int i = courseBaseMapper.updateById(cBase);
        if(i<=0){
            XueChengPlusException.cast("修改课程基本信息出现异常。。。");
        }
        //课程营销信息修改
        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(dto,courseMarket);
        saveOrUpdateCourseMarket(courseMarket);

        return getCourseBaseInfo(courseId);
    }

    @Transactional
    @Override
    public void delCourse(Long courseId) {
        //查询当前课程是否提交
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        //审核状态：202002：未提交
        if("202002".equals(courseBase.getAuditStatus())){

            try {
                //删除课程基本信息
                courseBaseMapper.deleteById(courseId);

                //1.删除营销信息
                courseMarketMapper.deleteById(courseId);
                //2.删除课程计划
                LambdaQueryWrapper<Teachplan> qwTeachplan = new LambdaQueryWrapper<>();
                qwTeachplan.eq(Teachplan::getCourseId,courseId);
                teachplanMapper.delete(qwTeachplan);
                //2.1删除课程计划媒资信息
                LambdaQueryWrapper<TeachplanMedia> qwTeachplanMedia = new LambdaQueryWrapper<>();
                qwTeachplanMedia.eq(TeachplanMedia::getCourseId,courseId);
                teachplanMediaMapper.delete(qwTeachplanMedia);
                //3.删除课程教师信息
                LambdaQueryWrapper<CourseTeacher> qwCourseTeacher = new LambdaQueryWrapper<>();
                qwCourseTeacher.eq(CourseTeacher::getCourseId,courseId);
                courseTeacherMapper.delete(qwCourseTeacher);
            }catch (Exception e){
                log.error("删除课程出现异常。。{}",e.getMessage());
                XueChengPlusException.cast("删除课程出现异常。。。。");
            }
        }
    }
}
