package io.renren.modules.generator.dao;

import io.renren.modules.generator.entity.ACompanyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 公司管理表
 * 
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-05-04 22:19:18
 */
@Mapper
public interface ACompanyDao extends BaseMapper<ACompanyEntity> {
	
}
