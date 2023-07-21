package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.AExpertScoreEntity;
import io.renren.modules.generator.entity.AScoreSystemEntity;
import io.renren.modules.generator.entity.Goods2Entity;

import java.util.List;
import java.util.Map;

/**
 * 专家打分表
 *
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-05-04 22:19:18
 */
public interface AExpertScoreService extends IService<AExpertScoreEntity> {

    PageUtils queryPage(Map<String, Object> params);
    AExpertScoreEntity getCompanyScoreByCompanyNameAndIndicator(String companyName,String indicatorId,String year);
    AExpertScoreEntity getCompanyScoreByCompanyNameAndIndicator1(Integer companyId,Integer indicatorId);

    List<AExpertScoreEntity> getCompanyScoreListByCompanyId(Integer companyId);
    Boolean updateExpertScoreById (AExpertScoreEntity aExpertScore);
    String getSumCompanyScoreListByCompanyNameAndIndicator(String companyName,String indicatorId,String year);
    Boolean updateCompanyScoreById (AExpertScoreEntity aExpertScore);
    List<AExpertScoreEntity> getCompanyScoreListByYear(Integer year);
    List<AExpertScoreEntity> getCompanyScoreListByYearAndIndicatorId(Integer indicatorId,Integer year);


}

