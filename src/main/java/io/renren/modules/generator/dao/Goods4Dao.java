package io.renren.modules.generator.dao;

import io.renren.modules.generator.entity.Goods4Entity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品编码（4位）
 * 
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-01-14 22:16:28
 */
@Mapper
public interface Goods4Dao extends BaseMapper<Goods4Entity> {
    Integer deleteAll(String userName);

}
