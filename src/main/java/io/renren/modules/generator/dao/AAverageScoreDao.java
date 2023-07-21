package io.renren.modules.generator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.generator.entity.AAverageScore;
import io.renren.modules.generator.entity.ACompanyAndScore;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version 1.0
 * @auther guanhongli
 * @date 2023/6/21 12:17 PM
 */
@Mapper
public interface AAverageScoreDao extends BaseMapper<AAverageScore> {
    String getSumScoreByIndicatorId(Integer indicatorId,Integer year);
    AAverageScore getScoreByIndicatorId(Integer indicatorId,Integer year);

}
