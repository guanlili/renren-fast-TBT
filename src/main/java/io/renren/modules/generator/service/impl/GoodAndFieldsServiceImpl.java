package io.renren.modules.generator.service.impl;

import io.renren.common.utils.ShiroUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.GoodAndFieldsDao;
import io.renren.modules.generator.entity.GoodAndFieldsEntity;
import io.renren.modules.generator.service.GoodAndFieldsService;


@Service("goodAndFieldsService")
public class GoodAndFieldsServiceImpl extends ServiceImpl<GoodAndFieldsDao, GoodAndFieldsEntity> implements GoodAndFieldsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String username= ShiroUtils.getUserEntity().getUsername();

        System.out.println("params:"+params);
        String fieldName = (String) params.get("fieldName");
        IPage<GoodAndFieldsEntity> page = this.page(
                new Query<GoodAndFieldsEntity>().getPage(params),
                new QueryWrapper<GoodAndFieldsEntity>()
                        .eq(!Objects.equals(fieldName, ""),"field_name", fieldName)
                        .eq(!Objects.equals(username, ""),"user_name", username)


        );

        return new PageUtils(page);
    }

}