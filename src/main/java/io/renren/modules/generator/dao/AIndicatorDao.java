package io.renren.modules.generator.dao;

import io.renren.modules.generator.entity.AIndicatorEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.generator.entity.Goods2Entity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 指标管理表
 * 
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-05-04 22:19:18
 */
@Mapper
public interface AIndicatorDao extends BaseMapper<AIndicatorEntity> {
    List<AIndicatorEntity> getAllIndicators();
    List<AIndicatorEntity> getIndicatorsByParentId(Integer parentId);
    AIndicatorEntity getIndicatorsById(Integer id);
    List<AIndicatorEntity> getIndicatorsByIndicatorLevel(Integer indicatorLevel);
    List<AIndicatorEntity> getBrothersIndicatorsById(Integer id);
    Integer getSumIndicatorWeightByParentId(Integer parentId,Integer companyId,Integer year);
    Integer getSumIndicatorWeightByParentIdNotNull(Integer parentId,Integer companyId,Integer year);



}
