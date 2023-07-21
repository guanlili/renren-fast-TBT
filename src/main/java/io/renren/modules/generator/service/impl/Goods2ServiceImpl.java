package io.renren.modules.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.renren.common.utils.ShiroUtils;
import io.renren.modules.generator.dao.GoodsDao;
import io.renren.modules.generator.entity.GoodsEntity;
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

import io.renren.modules.generator.dao.Goods2Dao;
import io.renren.modules.generator.entity.Goods2Entity;
import io.renren.modules.generator.service.Goods2Service;


@Service("goods2Service")
public class Goods2ServiceImpl extends ServiceImpl<Goods2Dao, Goods2Entity> implements Goods2Service {
    @Autowired
    private Goods2Dao goods2Dao;
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

        IPage<Goods2Entity> page = this.page(
                new Query<Goods2Entity>().getPage(params),

        new QueryWrapper<Goods2Entity>()
                        .select(Goods2Entity.class,item -> !item .getColumn().equals("field_name"))
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
    public List<Goods2Entity> getAllGoods() {
        return goods2Dao.getAllGoods();
    }

    @Override
    public List<Goods2Entity> findTotalAmountByProductName(String fieldName,String pickDateStart,String pickDateEnd,String userName) {
        return goods2Dao.findTotalAmountByProductName(fieldName,pickDateStart,pickDateEnd,userName);
    }
    @Override
    public List<Goods2Entity> findTotalAmountByNationalName(String fieldName,String pickDateStart,String pickDateEnd,String userName) {
        return goods2Dao.findTotalAmountByNationalName(fieldName,pickDateStart,pickDateEnd,userName);
    }
    @Override
    public List<Goods2Entity> findTotalAmountByAreaName(String fieldName,String pickDateStart,String pickDateEnd,String userName) {
        return goods2Dao.findTotalAmountByAreaName(fieldName,pickDateStart,pickDateEnd,userName);
    }
    @Override
    public List<Goods2Entity> findTotalAmountByProductNameOfNation(String name,String fieldName,String pickDateStart,String pickDateEnd,String userName) {
        return goods2Dao.findTotalAmountByProductNameOfNation(name,fieldName,pickDateStart,pickDateEnd,userName);
    }


    @Override
    public List<Goods2Entity> findTotalAmountByProductNameOfArea(String name,String fieldName,String pickDateStart,String pickDateEnd,String userName) {
        return goods2Dao.findTotalAmountByProductNameOfArea(name,fieldName,pickDateStart,pickDateEnd,userName);
    }
    @Override
    public Integer deleteAll(String userName) {
        return goods2Dao.deleteAll(userName);
    }

}