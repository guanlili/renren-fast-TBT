package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.Goods2Entity;
import io.renren.modules.generator.entity.GoodsEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品编码（2位）
 *
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-01-14 22:16:28
 */
public interface Goods2Service extends IService<Goods2Entity> {

    PageUtils queryPage(Map<String, Object> params);
    List<Goods2Entity> getAllGoods();
    List<Goods2Entity> findTotalAmountByProductName(String fieldName,String pickDateStart,String pickDateEnd,String userName);
    List<Goods2Entity> findTotalAmountByNationalName(String fieldName,String pickDateStart,String pickDateEnd,String userName);
    List<Goods2Entity> findTotalAmountByAreaName(String fieldName,String pickDateStart,String pickDateEnd,String userName);

    List<Goods2Entity> findTotalAmountByProductNameOfNation(String name,String fieldName,String pickDateStart,String pickDateEnd,String userName);
    List<Goods2Entity> findTotalAmountByProductNameOfArea(String name,String fieldName,String pickDateStart,String pickDateEnd,String userName);
    Integer deleteAll(String userName);


}

