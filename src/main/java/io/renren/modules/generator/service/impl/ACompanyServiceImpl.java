package io.renren.modules.generator.service.impl;

import io.renren.common.utils.ShiroUtils;
import io.renren.modules.generator.entity.GoodAndFieldsEntity;
import io.renren.modules.generator.entity.Goods2Entity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.ACompanyDao;
import io.renren.modules.generator.entity.ACompanyEntity;
import io.renren.modules.generator.service.ACompanyService;


@Service("aCompanyService")
public class ACompanyServiceImpl extends ServiceImpl<ACompanyDao, ACompanyEntity> implements ACompanyService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        System.out.println("params:"+params);
        String companyName = (String) params.get("companyName");
        String companyType = (String) params.get("companyType");

//        String key = (String) params.get("key");

        IPage<ACompanyEntity> page = this.page(
                new Query<ACompanyEntity>().getPage(params),
                new QueryWrapper<ACompanyEntity>()
                        .eq(!Objects.equals(companyName, ""),"company_name", companyName)
                        .eq(!Objects.equals(companyType, ""),"company_type", companyType)

        );
        return new PageUtils(page);
    }


    @Override
    public PageUtils querycompanyTypePage(Map<String, Object> params) {
        System.out.println("params:"+params);
        String key = (String) params.get("key");
        IPage<ACompanyEntity> page = this.page(
                new Query<ACompanyEntity>().getPage(params),
                new QueryWrapper<ACompanyEntity>()
                        .groupBy("company_type")
        );
        return new PageUtils(page);
    }


    @Override
    public List<ACompanyEntity> queryPageByCompanyType(Map<String, Object> params) {
        System.out.println("params:"+params);
        String companyType = (String) params.get("companyType");
        String year = (String) params.get("year");

        List<ACompanyEntity> pageList = this.list(
                new QueryWrapper<ACompanyEntity>()
                        .eq(!Objects.equals(companyType, ""),"company_type", companyType)
                        .eq(!Objects.equals(year, ""),"year", year)

        );
        return pageList;
    }


    @Override
    public List<ACompanyEntity> findAllCompany(Integer year) {

        List<ACompanyEntity> pageList = this.list(
                new QueryWrapper<ACompanyEntity>()
                        .eq("year", year)

        );

        return pageList;
    }


    @Override
    public ACompanyEntity findCompanyByCompnayName(String companyName,String year) {

        ACompanyEntity result = this.getOne(
                new QueryWrapper<ACompanyEntity>()
                        .eq("company_name", companyName)
                        .eq("year", year)
                        .last("LIMIT 1")

        );

        return result;
    }

    @Override
    public List<ACompanyEntity> queryPageByCompanyType1(String companyType,String year) {

        List<ACompanyEntity> pageList = this.list(
                new QueryWrapper<ACompanyEntity>()
                        .eq(!Objects.equals(companyType, ""),"company_type", companyType)
                        .eq(!Objects.equals(year, ""),"year", year)

        );
        return pageList;
    }




}