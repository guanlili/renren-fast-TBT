package io.renren.modules.generator.dao;

import io.renren.modules.generator.entity.Goods2Entity;
import io.renren.modules.generator.entity.Goods8Entity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品编码（8位）
 * 
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-01-14 22:16:28
 */
@Mapper
public interface Goods8Dao extends BaseMapper<Goods8Entity> {
    List<Goods8Entity> getAllGoods();
    List<Goods8Entity> findTotalAmountByProductName(String fieldName,String pickDateStart,String pickDateEnd,String userName);
    List<Goods8Entity> findTotalAmountByNationalName(String fieldName,String pickDateStart,String pickDateEnd,String userName);
    List<Goods8Entity> findTotalAmountByAreaName(String fieldName,String pickDateStart,String pickDateEnd,String userName);

    List<Goods8Entity> findTotalAmountByProductNameOfNation(String name,String fieldName,String pickDateStart,String pickDateEnd,String userName);
    List<Goods8Entity> findTotalAmountByProductNameOfArea(String name,String fieldName,String pickDateStart,String pickDateEnd,String userName);
    Integer deleteAll(String userName);



}
