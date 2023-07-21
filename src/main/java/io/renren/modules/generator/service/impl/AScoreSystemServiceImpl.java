package io.renren.modules.generator.service.impl;

import io.renren.modules.generator.dao.AIndicatorDao;
import io.renren.modules.generator.entity.AIndicatorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.AScoreSystemDao;
import io.renren.modules.generator.entity.AScoreSystemEntity;
import io.renren.modules.generator.service.AScoreSystemService;


@Service("aScoreSystemService")
public class AScoreSystemServiceImpl extends ServiceImpl<AScoreSystemDao, AScoreSystemEntity> implements AScoreSystemService {
    @Autowired
    private AScoreSystemDao aScoreSystemDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        System.out.println("params="+params);
        String scoreSystem = (String) params.get("scoreSystem");

        IPage<AScoreSystemEntity> page = this.page(
                new Query<AScoreSystemEntity>().getPage(params),
                new QueryWrapper<AScoreSystemEntity>()
                        .eq(!Objects.equals(scoreSystem, ""),"score_system", scoreSystem)

        );

        return new PageUtils(page);
    }

    @Override
    public List<AScoreSystemEntity> getAllIndicators() {
        return aScoreSystemDao.getScore();
    }

    @Override
    public AScoreSystemEntity getScoreByExpertScoreAndScoreSystem(String scoreSingle,String scoreSystem) {
        return aScoreSystemDao.getScoreByExpertScoreAndScoreSystem(scoreSingle,scoreSystem);
    }




}