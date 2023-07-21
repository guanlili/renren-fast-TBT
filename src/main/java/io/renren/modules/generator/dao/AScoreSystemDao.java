package io.renren.modules.generator.dao;

import io.renren.modules.generator.entity.AIndicatorEntity;
import io.renren.modules.generator.entity.AScoreSystemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 分制管理表
 * 
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-05-04 22:19:18
 */
@Mapper
public interface AScoreSystemDao extends BaseMapper<AScoreSystemEntity> {
    List<AScoreSystemEntity> getScore();
    AScoreSystemEntity getScoreByExpertScoreAndScoreSystem(String scoreSingle,String scoreSystem);

}
