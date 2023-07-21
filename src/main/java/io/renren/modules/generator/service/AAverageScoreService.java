package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.generator.entity.AAverageScore;
import io.renren.modules.generator.entity.ACompanyAndScore;

/**
 * 查表时候做的平均值，提前算好直接拿过来用。后来改成实时计算，废弃了。
 * @version 1.0
 * @auther guanhongli
 * @date 2023/6/21 12:01 PM
 */
public interface AAverageScoreService extends IService<AAverageScore> {
    String getSumScoreByIndicatorId(Integer indicatorId,Integer year);
    AAverageScore getScoreByIndicatorId(Integer indicatorId,Integer year);

}
