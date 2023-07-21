package io.renren.modules.generator.service.impl;

import io.renren.common.utils.ShiroUtils;
import io.renren.modules.generator.dao.Goods6Dao;
import io.renren.modules.generator.entity.Goods8Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.Goods4Dao;
import io.renren.modules.generator.entity.Goods4Entity;
import io.renren.modules.generator.service.Goods4Service;


@Service("goods4Service")
public class Goods4ServiceImpl extends ServiceImpl<Goods4Dao, Goods4Entity> implements Goods4Service {
    @Autowired
    private Goods4Dao goods4Dao;
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

        IPage<Goods4Entity> page = this.page(
                new Query<Goods4Entity>().getPage(params),
                new QueryWrapper<Goods4Entity>()
                        .select(Goods4Entity.class, item -> !item .getColumn().equals("field_name"))
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
        return goods4Dao.deleteAll(userName);
    }
}