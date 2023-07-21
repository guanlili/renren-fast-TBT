package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.AIndicatorEntity;
import io.renren.modules.generator.entity.AScoreSystemEntity;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

/**
 * 分制管理表
 *
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-05-04 22:19:18
 */
public interface AScoreSystemService extends IService<AScoreSystemEntity> {

    PageUtils queryPage(Map<String, Object> params);
    List<AScoreSystemEntity> getAllIndicators();
    AScoreSystemEntity getScoreByExpertScoreAndScoreSystem(String scoreSingle,String scoreSystem);



}

