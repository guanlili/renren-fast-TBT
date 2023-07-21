package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.Goods2Entity;
import io.renren.modules.generator.entity.Goods8Entity;
import io.renren.modules.generator.entity.GoodsEntity;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 商品编码（8位）
 *
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-01-14 22:16:28
 */
public interface Goods8Service extends IService<Goods8Entity> {

    PageUtils queryPage(Map<String, Object> params);
    List<Goods8Entity> getAllGoods();
    List<Goods8Entity> findTotalAmountByProductName(String fieldName,String pickDateStart,String pickDateEnd,String userName);
    List<Goods8Entity> findTotalAmountByNationalName(String fieldName,String pickDateStart,String pickDateEnd,String userName);
    List<Goods8Entity> findTotalAmountByAreaName(String fieldName,String pickDateStart,String pickDateEnd,String userName);

    List<Goods8Entity> findTotalAmountByProductNameOfNation(String name,String fieldName,String pickDateStart,String pickDateEnd,String userName);
    List<Goods8Entity> findTotalAmountByProductNameOfArea(String name,String fieldName,String pickDateStart,String pickDateEnd,String userName);
    Integer deleteAll(String userName);


}

