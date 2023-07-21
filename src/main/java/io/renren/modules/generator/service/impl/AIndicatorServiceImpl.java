package io.renren.modules.generator.service.impl;

import io.renren.modules.generator.dao.Goods2Dao;
import io.renren.modules.generator.entity.ACompanyEntity;
import io.renren.modules.generator.entity.Goods2Entity;
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

import io.renren.modules.generator.dao.AIndicatorDao;
import io.renren.modules.generator.entity.AIndicatorEntity;
import io.renren.modules.generator.service.AIndicatorService;


@Service("aIndicatorService")
public class AIndicatorServiceImpl extends ServiceImpl<AIndicatorDao, AIndicatorEntity> implements AIndicatorService {
    @Autowired
    private AIndicatorDao aIndicatorDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        System.out.println("params="+params);
        String indicatorName = (String) params.get("indicatorName");
        String indicatorLevel = (String) params.get("indicatorLevel");
        String star = (String) params.get("star");

        IPage<AIndicatorEntity> page = this.page(
                new Query<AIndicatorEntity>().getPage(params),
                new QueryWrapper<AIndicatorEntity>()
                        .eq(!Objects.equals(indicatorName, ""),"Indicator_name", indicatorName)
                        .eq(!Objects.equals(indicatorLevel, ""),"Indicator_level", indicatorLevel)
                        .eq(!Objects.equals(star, ""),"star", star)

        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageIndicatorLevel4(Map<String, Object> params) {
        IPage<AIndicatorEntity> page = this.page(
                new Query<AIndicatorEntity>().getPage(params),
                new QueryWrapper<AIndicatorEntity>()
                        .eq("Indicator_level", 4)
        );

        return new PageUtils(page);
    }

    @Override
    public List<AIndicatorEntity> getAllIndicators() {
        return aIndicatorDao.getAllIndicators();
    }

    @Override
    public List<AIndicatorEntity> getIndicatorsByParentId(Integer parentId) {
        return aIndicatorDao.getIndicatorsByParentId(parentId);
    }

    @Override
    public AIndicatorEntity getIndicatorsById(Integer id) {
        return aIndicatorDao.getIndicatorsById(id);
    }

    @Override
    public List<AIndicatorEntity> getIndicatorsByIndicatorLevel(Integer indicatorLevel) {
        return aIndicatorDao.getIndicatorsByIndicatorLevel(indicatorLevel);
    }

    @Override
    public List<AIndicatorEntity> getBrothersIndicatorsById(Integer id) {
        return aIndicatorDao.getBrothersIndicatorsById(id);
    }


    @Override
    public Integer getSumIndicatorWeightByParentId(Integer parentId,Integer companyId,Integer year) {
        return aIndicatorDao.getSumIndicatorWeightByParentId(parentId,companyId,year);
    }
    @Override
    public Integer getSumIndicatorWeightByParentIdNotNull(Integer parentId,Integer companyId,Integer year) {
        return aIndicatorDao.getSumIndicatorWeightByParentIdNotNull(parentId,companyId, year);
    }

}
