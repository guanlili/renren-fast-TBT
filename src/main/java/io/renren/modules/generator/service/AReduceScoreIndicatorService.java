package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.AReduceScoreIndicatorEntity;

import java.util.Map;

/**
 * 减分项管理表
 *
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-05-04 23:11:17
 */
public interface AReduceScoreIndicatorService extends IService<AReduceScoreIndicatorEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

