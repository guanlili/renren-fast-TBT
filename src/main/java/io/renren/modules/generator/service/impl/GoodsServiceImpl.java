package io.renren.modules.generator.service.impl;

import io.renren.modules.sys.dao.SysUserDao;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
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

import io.renren.modules.generator.dao.GoodsDao;
import io.renren.modules.generator.entity.GoodsEntity;
import io.renren.modules.generator.service.GoodsService;


@Service("goodsService")
public class GoodsServiceImpl extends ServiceImpl<GoodsDao, GoodsEntity> implements GoodsService {
    @Autowired
    private GoodsDao goodsDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        System.out.println("params:"+params);
        String key = (String) params.get("key");
        String name = (String)params.get("name");
        System.out.println("--"+name+"---");
        IPage<GoodsEntity> page = this.page(
                new Query<GoodsEntity>().getPage(params),
                new QueryWrapper<GoodsEntity>()
                        .eq(!Objects.equals(key, ""),"goods_id", key)
                        .eq(!Objects.equals(name, ""),"name", name)

        );

        return new PageUtils(page);
    }

    @Override
    public List<GoodsEntity> getAllGoods() {
        return goodsDao.getAllGoods();
    }


}