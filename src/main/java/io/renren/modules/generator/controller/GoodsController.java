package io.renren.modules.generator.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.generator.entity.GoodsEntity;
import io.renren.modules.generator.service.GoodsService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 商品管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2023-01-08 22:32:03
 */
@RestController
@RequestMapping("generator/goods")
public class GoodsController {
    private final static Gson gson = new Gson();

    @Autowired
    private GoodsService goodsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("generator:goods:list")
    public R list(@RequestParam Map<String, Object> params){
//        System.out.println("params="+params);
        PageUtils page = goodsService.queryPage(params);
        List<GoodsEntity> goodsEntities = (List<GoodsEntity>) page.getList();
        System.out.println("goodsEntities="+goodsEntities);
        BigDecimal sum = BigDecimal.valueOf(0);
        for (GoodsEntity goodsEntity:goodsEntities){
            sum = sum.add(goodsEntity.getPrice()) ;
        }

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{goodsId}")
    @RequiresPermissions("generator:goods:info")
    public R info(@PathVariable("goodsId") Long goodsId){
		GoodsEntity goods = goodsService.getById(goodsId);

        return R.ok().put("goods", goods);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:goods:save")
    public R save(@RequestBody GoodsEntity goods){
		goodsService.save(goods);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:goods:update")
    public R update(@RequestBody GoodsEntity goods){
		goodsService.updateById(goods);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:goods:delete")
    public R delete(@RequestBody Long[] goodsIds){
		goodsService.removeByIds(Arrays.asList(goodsIds));

        return R.ok();
    }

    /**
     * import excel
     */
    @RequestMapping("/importExcel")
//    @RequiresPermissions("generator:goods:import")
    public R importExcel(@RequestBody JSONObject data){
        JSONArray dataJsonArray =data.getJSONArray("data");
        System.out.println("dataJSONArray="+dataJsonArray);
        for (Object object:dataJsonArray){
            Map entity = (Map)object;
            GoodsEntity goodsEntity=new GoodsEntity();
            goodsEntity.setName(entity.get("商品名").toString());
            goodsEntity.setIntro(entity.get("介绍").toString());
            goodsEntity.setPrice(new BigDecimal(entity.get("价格").toString()));
            goodsEntity.setNum(Integer.valueOf(entity.get("数量").toString()));
            System.out.println("goodsEntity"+goodsEntity);
            goodsService.save(goodsEntity);
        }
        return R.ok();
    }

    /**
     * echart
     */
    @RequestMapping("/echart")
    public R echart(){
        System.out.println("========");
        List<GoodsEntity> goodsEntities = goodsService.getAllGoods();
        System.out.println("goodsEntities="+goodsEntities);
        BigDecimal sum = BigDecimal.valueOf(0);
        for (GoodsEntity goodsEntity:goodsEntities){
            sum = sum.add(goodsEntity.getPrice()) ;
        }



        return R.ok();
    }


}
