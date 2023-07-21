package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.AExpertScoreEntity;
import io.renren.modules.generator.entity.AIndicatorEntity;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

/**
 * 指标管理表
 *
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-05-04 22:19:18
 */
public interface AIndicatorService extends IService<AIndicatorEntity> {

    PageUtils queryPage(Map<String, Object> params);
    PageUtils queryPageIndicatorLevel4(Map<String, Object> params);

    List<AIndicatorEntity> getAllIndicators();
    List<AIndicatorEntity> getIndicatorsByParentId(Integer parentId);
    AIndicatorEntity getIndicatorsById(Integer id);
    List<AIndicatorEntity> getIndicatorsByIndicatorLevel(Integer indicatorLevel);

    List<AIndicatorEntity> getBrothersIndicatorsById(Integer id);
    Integer getSumIndicatorWeightByParentId(Integer parentId,Integer companyId,Integer year);
    Integer getSumIndicatorWeightByParentIdNotNull(Integer parentId,Integer companyId,Integer year);


}

