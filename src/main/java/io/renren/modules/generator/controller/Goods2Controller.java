package io.renren.modules.generator.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import io.renren.common.utils.ShiroUtils;
import io.renren.common.utils.goodsUntils;
import io.renren.modules.generator.entity.GoodsEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionUsageException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.generator.entity.Goods2Entity;
import io.renren.modules.generator.service.Goods2Service;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 商品编码（2位）
 *
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-01-14 22:16:28
 */
@RestController
@RequestMapping("generator/goods2")
public class Goods2Controller {
    @Autowired
    private Goods2Service goods2Service;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:goods2:list")
    public R list(@RequestParam Map<String, Object> params){

        PageUtils page = goods2Service.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:goods2:info")
    public R info(@PathVariable("id") Long id){
		Goods2Entity goods2 = goods2Service.getById(id);

        return R.ok().put("goods2", goods2);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:goods2:save")
    public R save(@RequestBody Goods2Entity goods2){
        goods2.setUserName(ShiroUtils.getUserEntity().getUsername());
        goods2Service.save(goods2);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:goods2:update")
    public R update(@RequestBody Goods2Entity goods2){
		goods2Service.updateById(goods2);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:goods2:delete")
    public R delete(@RequestBody Long[] ids){
		goods2Service.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
    /**
     * 删除全部
     */
    @RequestMapping("/deleteAll")
    @RequiresPermissions("generator:goods2:deleteAll")
    public R deleteAll(){
        System.out.println("删除全部");
        goods2Service.deleteAll(ShiroUtils.getUserEntity().getUsername());
        return R.ok();
    }
    /**
     * import excel
     */
    @RequestMapping("/importExcel")
    @RequiresPermissions("generator:good2s:import")
    public R importExcel(@RequestBody JSONObject data){
        long startTime = System.currentTimeMillis();

//        System.out.println("data="+data);
        JSONArray dataJsonArray =data.getJSONArray("data");
        String fileName= (String) data.get("fileName");
        System.out.println("fileName="+fileName);
        String defaultYear=StringUtils.substringBetween(fileName, "2", "年");
        System.out.println("defaultYear="+defaultYear);
        System.out.println("dataJSONArray="+dataJsonArray);
        List<Goods2Entity> goodsEntitys =new ArrayList<>();
        for (Object object:dataJsonArray){
            System.out.println("object"+object);
            Map entity = (Map)object;
            Goods2Entity goods2Entity=new Goods2Entity();
            if (entity.get("商品编码")!=null && entity.get("商品名称")!=null) {
                goods2Entity.setProductNum(Integer.valueOf(entity.get("商品编码").toString()));
                goods2Entity.setProductName(entity.get("商品名称").toString());
            }
            if (entity.get("贸易伙伴编码")!=null && entity.get("贸易伙伴名称")!=null) {
                goods2Entity.setTradePartnerNum(Integer.valueOf(entity.get("贸易伙伴编码").toString()));
                goods2Entity.setTradePartnerName(entity.get("贸易伙伴名称").toString());
            }
            if (entity.get("贸易方式编码")!=null && entity.get("贸易方式名称")!=null){
                goods2Entity.setTradeMethodNum(Integer.valueOf(entity.get("贸易方式编码").toString()));
                goods2Entity.setTradeMethodName(entity.get("贸易方式名称").toString());
            }
            if (entity.get("注册地编码")!=null && entity.get("注册地名称")!=null) {
                goods2Entity.setRegistrationPlaceNum(Integer.valueOf(entity.get("注册地编码").toString()));
                goods2Entity.setRegistrationPlaceName(entity.get("注册地名称").toString());
            }
//            goods2Entity.setFirstNum(Integer.valueOf(entity.get("第一数量").toString()));
//            goods2Entity.setFirstNumUnit(entity.get("第一计量单位").toString());
//            goods2Entity.setSecondNum(Integer.valueOf(entity.get("第二数量").toString()));
//            goods2Entity.setSecondNumUnit(entity.get("第二计量单位").toString());
            goods2Entity.setRmbAmount(new BigDecimal(entity.get("人民币").toString()));
            if(entity.get("数据年月")!=null){
                goods2Entity.setDataDate(Integer.valueOf(entity.get("数据年月").toString()));
            }else{
                goods2Entity.setDataDate(Integer.valueOf(("2"+defaultYear+"00")));
            }
            //通过文件名字获取进出口类型
            goods2Entity.setTradeType(goodsUntils.getTradeType(fileName));
            goods2Entity.setUserName(ShiroUtils.getUserEntity().getUsername());

            System.out.println("goods2Entity"+goods2Entity);
            goodsEntitys.add(goods2Entity);
//            goods2Service.save(goods2Entity);
        }
        goods2Service.saveBatch(goodsEntitys);
        System.out.println("耗费了" + (System.currentTimeMillis() - startTime) + "ms");

        return R.ok();
    }

}
