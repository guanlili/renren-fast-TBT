package io.renren.modules.generator.dao;

import io.renren.modules.generator.entity.Goods6Entity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.generator.entity.Goods8Entity;
import io.renren.modules.generator.entity.GoodsEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品编码（6位）
 * 
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-01-14 22:16:28
 */
@Mapper
public interface Goods6Dao extends BaseMapper<Goods6Entity> {
    Integer deleteAll(String userName);

}
