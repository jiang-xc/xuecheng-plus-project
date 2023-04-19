package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryDto;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.service.CourseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程分类 服务实现类
 * </p>
 *
 * @author jiangxc
 */
@Slf4j
@Service
public class CourseCategoryServiceImpl extends ServiceImpl<CourseCategoryMapper, CourseCategory> implements CourseCategoryService {

    @Autowired
    private CourseCategoryMapper courseCategoryMapper;

    /**
     * 查询指定类别的所有子节点
     * @param id
     * @return
     */
    @Override
    public List<CourseCategoryDto> queryCategoryChildNodes(String id) {

        List<CourseCategoryDto> courseCategoryDtos = courseCategoryMapper.queryCategoryChildNodes(id);
        //将所有数据按照key为id，value为对象的形式放入map集合中
        Map<String, CourseCategoryDto> courseCategoryDtoMap = courseCategoryDtos.stream().filter(item -> !id.equals(item.getId())).collect(Collectors.toMap(key -> key.getId(), value -> value, (key1, key2) -> key2));
        ArrayList<CourseCategoryDto> arrayList = new ArrayList<>();
        //排除根节点
        List<CourseCategoryDto> courseCategoryRMRootNodeList = courseCategoryDtos.stream().filter(item -> !id.equals(item.getId())).collect(Collectors.toList());

        courseCategoryRMRootNodeList.forEach(item -> {
            if(item.getParentid().equals(id)){
                arrayList.add(item);
            }

            CourseCategoryDto temDto = courseCategoryDtoMap.get(item.getParentid());
            //找出父节点，并为所有父节点的children列表赋值
            if(temDto!=null){
                if(CollectionUtils.isEmpty(temDto.getCourseCategoryDtos())){
                   temDto.setCourseCategoryDtos(new ArrayList<>());
                }

                temDto.getCourseCategoryDtos().add(item);
            }
        });

        return arrayList;
    }
}
