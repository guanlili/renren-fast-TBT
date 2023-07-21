package io.renren.modules.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.generator.dao.ACompanyAndScoreDao;
import io.renren.modules.generator.dao.AExpertScoreDao;
import io.renren.modules.generator.entity.ACompanyAndScore;
import io.renren.modules.generator.entity.AExpertScoreEntity;
import io.renren.modules.generator.service.ACompanyAndScoreService;
import io.renren.modules.generator.service.AExpertScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @auther guanhongli
 * @date 2023/5/23 3:29 PM
 */
@Service("ACompanyAndScoreService")
public class ACompanyAndScoreServiceImpl extends ServiceImpl<ACompanyAndScoreDao, ACompanyAndScore> implements ACompanyAndScoreService {
    @Autowired
    private ACompanyAndScoreDao aCompanyAndScoreDao;

    @Override
    public String getSumCompanyScoreByCompanyAndIndicator(Integer companyId,Integer indicatorId) {
        return aCompanyAndScoreDao.getSumCompanyScoreByCompanyAndIndicator(companyId,indicatorId);
    }

    @Override
    public String getSumCompanyScoreByCompany(Integer companyId,Integer year) {
        return aCompanyAndScoreDao.getSumCompanyScoreByCompany(companyId,year);
    }

    @Override
    public ACompanyAndScore getByCompanyAndIndicator(Integer companyId,Integer indicatorId,Integer year) {
        return aCompanyAndScoreDao.getByCompanyAndIndicator(companyId, indicatorId, year);
    }



}
