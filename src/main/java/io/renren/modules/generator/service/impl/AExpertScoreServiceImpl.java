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

import io.renren.modules.generator.dao.AExpertScoreDao;
import io.renren.modules.generator.entity.AExpertScoreEntity;
import io.renren.modules.generator.service.AExpertScoreService;


@Service("aExpertScoreService")
public class AExpertScoreServiceImpl extends ServiceImpl<AExpertScoreDao, AExpertScoreEntity> implements AExpertScoreService {
    @Autowired
    private AExpertScoreDao aExpertScoreDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        System.out.println("params:"+params);
        String companyName = (String) params.get("companyName");
        String indicatorName = (String) params.get("indicatorName");
        String scoreSystem = (String) params.get("scoreSystem");
        String year = (String) params.get("year");

        IPage<AExpertScoreEntity> page = this.page(
                new Query<AExpertScoreEntity>().getPage(params),
                new QueryWrapper<AExpertScoreEntity>()
                        .eq(!Objects.equals(companyName, ""),"company_name", companyName)
                        .eq(!Objects.equals(indicatorName, ""),"indicator_name", indicatorName)
                        .eq(!Objects.equals(year, ""),"year", year)
                        .eq(!Objects.equals(scoreSystem, ""),"score_system", scoreSystem)
        );
        return new PageUtils(page);
    }



    @Override
    public AExpertScoreEntity getCompanyScoreByCompanyNameAndIndicator(String companyName, String indicatorId,String year) {
        return aExpertScoreDao.getCompanyScoreByCompanyNameAndIndicator(companyName,indicatorId,year);
    }

    @Override
    public AExpertScoreEntity getCompanyScoreByCompanyNameAndIndicator1(Integer companyId,Integer indicatorId) {
        return aExpertScoreDao.getCompanyScoreByCompanyNameAndIndicator1(companyId,indicatorId);
    }

    @Override
    public List<AExpertScoreEntity> getCompanyScoreListByCompanyId(Integer companyId) {
        return aExpertScoreDao.getCompanyScoreListByCompanyId(companyId);
    }


    @Override
    public Boolean updateExpertScoreById(AExpertScoreEntity aExpertScore) {
        return aExpertScoreDao.updateExpertScoreById(aExpertScore);
    }

    @Override
    public String getSumCompanyScoreListByCompanyNameAndIndicator(String companyName,String indicatorId,String year) {
        return aExpertScoreDao.getSumCompanyScoreListByCompanyNameAndIndicator(companyName,indicatorId,year);
    }
    @Override
    public Boolean updateCompanyScoreById(AExpertScoreEntity aExpertScore) {
        return aExpertScoreDao.updateCompanyScoreById(aExpertScore);
    }


    @Override
    public List<AExpertScoreEntity> getCompanyScoreListByYear(Integer year) {
        return aExpertScoreDao.getCompanyScoreListByYear(year);
    }

    @Override
    public List<AExpertScoreEntity> getCompanyScoreListByYearAndIndicatorId(Integer indicatorId,Integer year) {
        return aExpertScoreDao.getCompanyScoreListByYearAndIndicatorId(indicatorId,year);
    }

}