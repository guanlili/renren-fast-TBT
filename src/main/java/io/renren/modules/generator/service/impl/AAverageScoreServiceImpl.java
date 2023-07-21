package io.renren.modules.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.generator.dao.AAverageScoreDao;
import io.renren.modules.generator.dao.ACompanyAndScoreDao;
import io.renren.modules.generator.entity.AAverageScore;
import io.renren.modules.generator.entity.ACompanyAndScore;
import io.renren.modules.generator.entity.AIndicatorEntity;
import io.renren.modules.generator.service.AAverageScoreService;
import io.renren.modules.generator.service.ACompanyAndScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * @version 1.0
 * @auther guanhongli
 * @date 2023/6/21 12:15 PM
 */
@Service("AAverageScoreService")
public class AAverageScoreServiceImpl extends ServiceImpl<AAverageScoreDao, AAverageScore> implements AAverageScoreService {
    @Autowired
    private AAverageScoreDao aAverageScoreDao;

    @Override
    public String getSumScoreByIndicatorId(Integer indicatorId,Integer year) {
        return aAverageScoreDao.getSumScoreByIndicatorId(indicatorId,year);
    }

    @Override
    public AAverageScore getScoreByIndicatorId(Integer indicatorId,Integer year) {
        return aAverageScoreDao.getScoreByIndicatorId(indicatorId,year);
    }


}
