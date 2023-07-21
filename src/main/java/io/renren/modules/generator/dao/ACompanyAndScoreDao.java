package io.renren.modules.generator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.generator.entity.ACompanyAndScore;
import io.renren.modules.generator.entity.ACompanyEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version 1.0
 * @auther guanhongli
 * @date 2023/5/23 3:32 PM
 */
@Mapper
public interface ACompanyAndScoreDao extends BaseMapper<ACompanyAndScore> {
    String getSumCompanyScoreByCompanyAndIndicator(Integer companyId,Integer indicatorId);
    String getSumCompanyScoreByCompany(Integer companyId,Integer year);
    ACompanyAndScore getByCompanyAndIndicator(Integer companyId,Integer indicatorId,Integer year);

}