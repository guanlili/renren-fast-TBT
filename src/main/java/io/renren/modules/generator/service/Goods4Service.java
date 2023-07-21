package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.Goods4Entity;

import java.util.Map;

/**
 * 商品编码（4位）
 *
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-01-14 22:16:28
 */
public interface Goods4Service extends IService<Goods4Entity> {

    PageUtils queryPage(Map<String, Object> params);
    Integer deleteAll(String userName);

}

