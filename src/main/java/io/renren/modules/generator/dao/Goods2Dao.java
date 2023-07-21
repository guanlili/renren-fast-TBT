package io.renren.modules.generator.dao;

import io.renren.modules.generator.entity.Goods2Entity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.generator.entity.GoodsEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品编码（2位）
 * 
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-01-14 22:16:28
 */
@Mapper
public interface Goods2Dao extends BaseMapper<Goods2Entity> {
    List<Goods2Entity> getAllGoods();
    List<Goods2Entity> findTotalAmountByProductName(String fieldName,String pickDateStart,String pickDateEnd,String userName);
    List<Goods2Entity> findTotalAmountByNationalName(String fieldName,String pickDateStart,String pickDateEnd,String userName);
    List<Goods2Entity> findTotalAmountByAreaName(String fieldName,String pickDateStart,String pickDateEnd,String userName);
    List<Goods2Entity> findTotalAmountByProductNameOfNation(String name,String fieldName,String pickDateStart,String pickDateEnd,String userName);
    List<Goods2Entity> findTotalAmountByProductNameOfArea(String name,String fieldName,String pickDateStart,String pickDateEnd,String userName);

    Integer deleteAll(String userName);

}
