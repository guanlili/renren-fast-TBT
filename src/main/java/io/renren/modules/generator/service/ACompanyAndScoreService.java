package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.ACompanyAndScore;
import io.renren.modules.generator.entity.AExpertScoreEntity;

import java.util.Map;

/**
 * @version 1.0
 * @auther guanhongli
 * @date 2023/5/23 3:27 PM
 */
public interface ACompanyAndScoreService extends IService<ACompanyAndScore> {
    String getSumCompanyScoreByCompanyAndIndicator(Integer companyId,Integer indicatorId);
    String getSumCompanyScoreByCompany(Integer companyId,Integer year);
    ACompanyAndScore getByCompanyAndIndicator(Integer companyId,Integer indicatorId,Integer year);


}
