package io.renren.modules.generator.dao;

import io.renren.modules.generator.entity.GoodAndFieldsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 领域和商品名称字典表
 * 
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-01-18 08:48:02
 */
@Mapper
public interface GoodAndFieldsDao extends BaseMapper<GoodAndFieldsEntity> {
	
}
