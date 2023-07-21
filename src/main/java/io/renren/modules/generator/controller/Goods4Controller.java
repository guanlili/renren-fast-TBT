package io.renren.modules.generator.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.ShiroUtils;
import io.renren.common.utils.goodsUntils;
import io.renren.modules.generator.entity.Goods2Entity;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.generator.entity.Goods4Entity;
import io.renren.modules.generator.service.Goods4Service;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 商品编码（4位）
 *
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-01-14 22:16:28
 */
@RestController
@RequestMapping("generator/goods4")
public class Goods4Controller {
    @Autowired
    private Goods4Service goods4Service;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:goods4:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = goods4Service.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:goods4:info")
    public R info(@PathVariable("id") Long id){
		Goods4Entity goods4 = goods4Service.getById(id);

        return R.ok().put("goods4", goods4);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:goods4:save")
    public R save(@RequestBody Goods4Entity goods4){
        goods4.setUserName(ShiroUtils.getUserEntity().getUsername());
        goods4Service.save(goods4);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:goods4:update")
    public R update(@RequestBody Goods4Entity goods4){
		goods4Service.updateById(goods4);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:goods4:delete")
    public R delete(@RequestBody Long[] ids){
		goods4Service.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
    /**
     * 删除全部
     */
    @RequestMapping("/deleteAll")
    @RequiresPermissions("generator:goods4:deleteAll")
    public R deleteAll(){
        System.out.println("删除全部");
        goods4Service.deleteAll(ShiroUtils.getUserEntity().getUsername());
        return R.ok();
    }
    /**
     * import excel
     */
    @RequestMapping("/importExcel")
    @RequiresPermissions("generator:good2s:import")
    public R importExcel(@RequestBody JSONObject data){
        JSONArray dataJsonArray =data.getJSONArray("data");
        String fileName= (String) data.get("fileName");
        String defaultYear= StringUtils.substringBetween(fileName, "2", "年");
        System.out.println("defaultYear="+defaultYear);
//        System.out.println("111111dataJSONArray="+dataJsonArray);
        List<Goods4Entity> goodsEntitys =new ArrayList<>();

        for (Object object:dataJsonArray){
//            System.out.println("object"+object);
            Map entity = (Map)object;
            Goods4Entity goodsEntity=new Goods4Entity();
            if (entity.get("商品编码")!=null && entity.get("商品名称")!=null) {
                goodsEntity.setProductNum(Integer.valueOf(entity.get("商品编码").toString()));
                goodsEntity.setProductName(entity.get("商品名称").toString());
            }
            if (entity.get("贸易伙伴编码")!=null && entity.get("贸易伙伴名称")!=null) {
                goodsEntity.setTradePartnerNum(Integer.valueOf(entity.get("贸易伙伴编码").toString()));
                goodsEntity.setTradePartnerName(entity.get("贸易伙伴名称").toString());
            }
            if (entity.get("贸易方式编码")!=null && entity.get("贸易方式名称")!=null){
                goodsEntity.setTradeMethodNum(Integer.valueOf(entity.get("贸易方式编码").toString()));
                goodsEntity.setTradeMethodName(entity.get("贸易方式名称").toString());
            }
            if (entity.get("注册地编码")!=null && entity.get("注册地名称")!=null) {
                goodsEntity.setRegistrationPlaceNum(Integer.valueOf(entity.get("注册地编码").toString()));
                goodsEntity.setRegistrationPlaceName(entity.get("注册地名称").toString());
            }
//            goods2Entity.setFirstNum(Integer.valueOf(entity.get("第一数量").toString()));
//            goods2Entity.setFirstNumUnit(entity.get("第一计量单位").toString());
//            goods2Entity.setSecondNum(Integer.valueOf(entity.get("第二数量").toString()));
//            goods2Entity.setSecondNumUnit(entity.get("第二计量单位").toString());
            goodsEntity.setRmbAmount(new BigDecimal(entity.get("人民币").toString()));
            if(entity.get("数据年月")!=null){
                goodsEntity.setDataDate(Integer.valueOf(entity.get("数据年月").toString()));
            }else{
                goodsEntity.setDataDate(Integer.valueOf(("2"+defaultYear+"00")));
            }
            //通过文件名字获取进出口类型
            goodsEntity.setTradeType(goodsUntils.getTradeType(fileName));
            goodsEntity.setUserName(ShiroUtils.getUserEntity().getUsername());

            System.out.println("goodsEntity"+goodsEntity);
            goodsEntitys.add(goodsEntity);

//            goods4Service.save(goodsEntity);
        }
        goods4Service.saveBatch(goodsEntitys);

        return R.ok();
    }

}
