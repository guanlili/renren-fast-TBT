package io.renren.modules.generator.dao;

import io.renren.modules.generator.entity.GoodsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2023-01-08 22:32:03
 */
@Mapper
public interface GoodsDao extends BaseMapper<GoodsEntity> {

    List<GoodsEntity> getAllGoods();


}
