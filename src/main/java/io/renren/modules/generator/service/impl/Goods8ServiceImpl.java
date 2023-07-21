package io.renren.modules.generator.service.impl;

import io.renren.common.utils.ShiroUtils;
import io.renren.modules.generator.dao.Goods2Dao;
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

import io.renren.modules.generator.dao.Goods8Dao;
import io.renren.modules.generator.entity.Goods8Entity;
import io.renren.modules.generator.service.Goods8Service;


@Service("goods8Service")
public class Goods8ServiceImpl extends ServiceImpl<Goods8Dao, Goods8Entity> implements Goods8Service {
    @Autowired
    private Goods8Dao goods8Dao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        System.out.println("params:"+params);
        String productNum = (String) params.get("productNum");
        String productName = (String) params.get("productName");
        String tradeMethodName = (String)params.get("tradeMethodName");
        String tradePartnerName = (String)params.get("tradePartnerName");
        String registrationPlaceName = (String)params.get("registrationPlaceName");
        String dataDate = (String) params.get("dataDate");
        String username= ShiroUtils.getUserEntity().getUsername();


        IPage<Goods8Entity> page = this.page(
                new Query<Goods8Entity>().getPage(params),
                new QueryWrapper<Goods8Entity>()
                        .select(Goods8Entity.class,item -> !item .getColumn().equals("field_name"))
                        .eq(!Objects.equals(productNum, ""),"product_num", productNum)
                        .eq(!Objects.equals(productName, ""),"product_name", productName)
                        .eq(!Objects.equals(tradeMethodName, ""),"trade_method_name", tradeMethodName)
                        .eq(!Objects.equals(tradePartnerName, ""),"trade_partner_name", tradePartnerName)
                        .eq(!Objects.equals(registrationPlaceName, ""),"registration_place_name", registrationPlaceName)
                        .eq(!Objects.equals(username, ""),"user_name", username)

                        .like(!Objects.equals(dataDate, ""),"data_date", dataDate)

        );

        return new PageUtils(page);
    }
    @Override
    public List<Goods8Entity> getAllGoods() {
        return goods8Dao.getAllGoods();
    }

    @Override
    public List<Goods8Entity> findTotalAmountByProductName(String fieldName,String pickDateStart,String pickDateEnd,String userName) {
        return goods8Dao.findTotalAmountByProductName(fieldName,pickDateStart,pickDateEnd,userName);
    }
    @Override
    public List<Goods8Entity> findTotalAmountByNationalName(String fieldName,String pickDateStart,String pickDateEnd,String userName) {
        return goods8Dao.findTotalAmountByNationalName(fieldName,pickDateStart,pickDateEnd,userName);
    }
    @Override
    public List<Goods8Entity> findTotalAmountByAreaName(String fieldName,String pickDateStart,String pickDateEnd,String userName) {
        return goods8Dao.findTotalAmountByAreaName(fieldName,pickDateStart,pickDateEnd,userName);
    }
    @Override
    public List<Goods8Entity> findTotalAmountByProductNameOfNation(String name,String fieldName,String pickDateStart,String pickDateEnd,String userName) {
        return goods8Dao.findTotalAmountByProductNameOfNation(name,fieldName,pickDateStart,pickDateEnd,userName);
    }

    @Override
    public List<Goods8Entity> findTotalAmountByProductNameOfArea(String name,String fieldName,String pickDateStart,String pickDateEnd,String userName) {
        return goods8Dao.findTotalAmountByProductNameOfArea(name,fieldName,pickDateStart,pickDateEnd,userName);
    }
    @Override
    public Integer deleteAll(String userName) {
        return goods8Dao.deleteAll(userName);
    }
}