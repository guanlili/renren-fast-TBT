package io.renren.modules.generator.dao;

import io.renren.modules.generator.entity.AExpertScoreEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 专家打分表
 * 
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-05-04 22:19:18
 */
@Mapper
public interface AExpertScoreDao extends BaseMapper<AExpertScoreEntity> {
    AExpertScoreEntity getCompanyScoreByCompanyNameAndIndicator(String companyName,String indicatorId,String year);
    AExpertScoreEntity getCompanyScoreByCompanyNameAndIndicator1(Integer companyId,Integer indicatorId);

    List<AExpertScoreEntity> getCompanyScoreListByCompanyId(Integer companyId);
    Boolean updateExpertScoreById (AExpertScoreEntity aExpertScore);
    String getSumCompanyScoreListByCompanyNameAndIndicator(String companyName,String indicatorId,String year);
    Boolean updateCompanyScoreById (AExpertScoreEntity aExpertScore);
    List<AExpertScoreEntity> getCompanyScoreListByYear(Integer year);
    List<AExpertScoreEntity> getCompanyScoreListByYearAndIndicatorId(Integer indicatorId,Integer year);

}
