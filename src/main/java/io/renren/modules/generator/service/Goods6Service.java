package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.Goods6Entity;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

/**
 * 商品编码（6位）
 *
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-01-14 22:16:28
 */
public interface Goods6Service extends IService<Goods6Entity> {

    PageUtils queryPage(Map<String, Object> params);
    Integer deleteAll(String userName);

}

