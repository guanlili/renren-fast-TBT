package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.GoodAndFieldsEntity;

import java.util.Map;

/**
 * 领域和商品名称字典表
 *
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-01-18 08:48:02
 */
public interface GoodAndFieldsService extends IService<GoodAndFieldsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

