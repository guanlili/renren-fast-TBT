package io.renren.modules.generator.service.impl;

import io.renren.common.utils.ShiroUtils;
import io.renren.modules.generator.dao.Goods8Dao;
import io.renren.modules.generator.dao.GoodsDao;
import io.renren.modules.generator.entity.Goods4Entity;
import io.renren.modules.generator.entity.Goods8Entity;
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

import io.renren.modules.generator.dao.Goods6Dao;
import io.renren.modules.generator.entity.Goods6Entity;
import io.renren.modules.generator.service.Goods6Service;


@Service("goods6Service")
public class Goods6ServiceImpl extends ServiceImpl<Goods6Dao, Goods6Entity> implements Goods6Service {
    @Autowired
    private Goods6Dao goods6Dao;
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


        IPage<Goods6Entity> page = this.page(
                new Query<Goods6Entity>().getPage(params),
                new QueryWrapper<Goods6Entity>()
                        .select(Goods6Entity.class, item -> !item .getColumn().equals("field_name"))
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
    public Integer deleteAll(String userName) {
        return goods6Dao.deleteAll(userName);
    }

}