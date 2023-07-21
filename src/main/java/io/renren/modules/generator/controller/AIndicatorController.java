package io.renren.modules.generator.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.apache.http.HttpStatus;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.generator.entity.AIndicatorEntity;
import io.renren.modules.generator.service.AIndicatorService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 指标管理表
 *
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-05-04 22:19:18
 */
@RestController
@RequestMapping("generator/aindicator")
public class AIndicatorController {
    @Autowired
    private AIndicatorService aIndicatorService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:aindicator:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = aIndicatorService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:aindicator:info")
    public R info(@PathVariable("id") Integer id){
		AIndicatorEntity aIndicator = aIndicatorService.getById(id);

        return R.ok().put("aIndicator", aIndicator);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:aindicator:save")
    public R save(@RequestBody AIndicatorEntity aIndicator){
        System.out.println("aIndicator"+aIndicator);
        if (aIndicator.getParentId()!=0){
            AIndicatorEntity aIndicatorEntity= aIndicatorService.getIndicatorsById(aIndicator.getParentId());
            aIndicator.setParentName(aIndicatorEntity.getIndicatorName());
            System.out.println("aIndicatorEntity"+aIndicatorEntity);
        }else {
            aIndicator.setParentName(null);
        }

        // 如果校验IndicatorSum不通过
        R r = assertIndicatorSum(aIndicator);
        if (!Objects.equals(r, R.ok())){
            return r;
        }
        aIndicatorService.save(aIndicator);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:aindicator:update")
    public R update(@RequestBody AIndicatorEntity aIndicator){
        // 如果校验IndicatorSum不通过
        R r = assertIndicatorSum(aIndicator);
        if (!Objects.equals(r, R.ok())){
            return r;
        }
		aIndicatorService.updateById(aIndicator);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:aindicator:delete")
    public R delete(@RequestBody Integer[] ids){
		aIndicatorService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 父节点列表
     */
    @RequestMapping("/parentList")
//    @RequiresPermissions("generator:aindicator:list")
    public R parentList(@RequestParam Map<String, Object> params){
//        PageUtils page = aIndicatorService.queryPageParent(params);
        JSONObject jsonObject =new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String indicatorLevel = (String) params.get("indicatorLevel");
        System.out.println("indicatorLevel"+indicatorLevel);
        if (Objects.equals(indicatorLevel, "4")){
            //先查询一级指标
            List<AIndicatorEntity> aIndicatorEntities=  aIndicatorService.getIndicatorsByIndicatorLevel(1);
            for (AIndicatorEntity aIndicatorEntity : aIndicatorEntities){
                JSONObject jsonObject1 =new JSONObject();
                JSONArray jsonArray1 = new JSONArray();
                //根据一级指标查询二级指标
                List<AIndicatorEntity> aIndicatorEntities2 = aIndicatorService.getIndicatorsByParentId(aIndicatorEntity.getId());
                for (AIndicatorEntity aIndicatorEntity2 : aIndicatorEntities2){
                    JSONArray jsonArray2 = new JSONArray();
                    JSONObject jsonObject2 =new JSONObject();

                    //根据二级指标查询三级指标
                    List<AIndicatorEntity> aIndicatorEntities3 = aIndicatorService.getIndicatorsByParentId(aIndicatorEntity2.getId());
                    //遍历三级指标
                    for (AIndicatorEntity aIndicatorEntity3 : aIndicatorEntities3) {
                        JSONObject jsonObject3 =new JSONObject();
                        jsonObject3.put("value",aIndicatorEntity3.getId());
                        jsonObject3.put("label",aIndicatorEntity3.getIndicatorName());
                        jsonObject3.put("indicatorLevel",aIndicatorEntity3.getIndicatorLevel());
                        jsonArray2.add(jsonObject3);
                    }
                    jsonObject2.put("value",aIndicatorEntity2.getId());
                    jsonObject2.put("label",aIndicatorEntity2.getIndicatorName());
                    jsonObject2.put("indicatorLevel",aIndicatorEntity2.getIndicatorLevel());
                    jsonObject2.put("children",jsonArray2);
                    jsonArray1.add(jsonObject2);
                }
                jsonObject1.put("value",aIndicatorEntity.getId());
                jsonObject1.put("label",aIndicatorEntity.getIndicatorName());
                jsonObject1.put("indicatorLevel",aIndicatorEntity.getIndicatorLevel());
                jsonObject1.put("children",jsonArray1);
                jsonArray.add(jsonObject1);
            }
            jsonObject.put("children",jsonArray);
            jsonObject.put("value",0);
            jsonObject.put("label","根节点");
            System.out.println(jsonObject);
        }else if (Objects.equals(indicatorLevel, "3")){
            //先查询一级指标
            List<AIndicatorEntity> aIndicatorEntities=  aIndicatorService.getIndicatorsByIndicatorLevel(1);
            for (AIndicatorEntity aIndicatorEntity : aIndicatorEntities){
                JSONObject jsonObject1 =new JSONObject();
                JSONArray jsonArray1 = new JSONArray();
                //根据一级指标查询二级指标
                List<AIndicatorEntity> aIndicatorEntities2 = aIndicatorService.getIndicatorsByParentId(aIndicatorEntity.getId());
                for (AIndicatorEntity aIndicatorEntity2 : aIndicatorEntities2){
                    JSONArray jsonArray2 = new JSONArray();
                    JSONObject jsonObject2 =new JSONObject();
                    jsonObject2.put("value",aIndicatorEntity2.getId());
                    jsonObject2.put("label",aIndicatorEntity2.getIndicatorName());
                    jsonObject2.put("indicatorLevel",aIndicatorEntity2.getIndicatorLevel());
                    jsonObject2.put("children",jsonArray2);
                    jsonArray1.add(jsonObject2);
                }
                jsonObject1.put("value",aIndicatorEntity.getId());
                jsonObject1.put("label",aIndicatorEntity.getIndicatorName());
                jsonObject1.put("indicatorLevel",aIndicatorEntity.getIndicatorLevel());
                jsonObject1.put("children",jsonArray1);
                jsonArray.add(jsonObject1);
            }
            jsonObject.put("children",jsonArray);
            jsonObject.put("value",0);
            jsonObject.put("label","根节点");
            System.out.println(jsonObject);
        }else if (Objects.equals(indicatorLevel, "2")){
            //先查询一级指标
            List<AIndicatorEntity> aIndicatorEntities=  aIndicatorService.getIndicatorsByIndicatorLevel(1);
            for (AIndicatorEntity aIndicatorEntity : aIndicatorEntities){
                JSONObject jsonObject1 =new JSONObject();
                JSONArray jsonArray1 = new JSONArray();
                //根据一级指标查询二级指标
                jsonObject1.put("value",aIndicatorEntity.getId());
                jsonObject1.put("label",aIndicatorEntity.getIndicatorName());
                jsonObject1.put("indicatorLevel",aIndicatorEntity.getIndicatorLevel());
                jsonObject1.put("children",jsonArray1);
                jsonArray.add(jsonObject1);
            }
            jsonObject.put("children",jsonArray);
            jsonObject.put("value",0);
            jsonObject.put("label","根节点");
            System.out.println(jsonObject);
        }else {
            //先查询一级指标
            jsonObject.put("children",jsonArray);
            jsonObject.put("value",0);
            jsonObject.put("label","根节点");
            System.out.println(jsonObject);

        }
        System.out.println("jsonObject="+jsonObject);
        return R.ok().put("page", jsonObject);
    }


    /**
     * 4级节点
     */
    @RequestMapping("/queryIndicatorLevel4")
//    @RequiresPermissions("generator:aindicator:list")
    public R queryIndicatorLevel4(@RequestParam Map<String, Object> params){
        PageUtils page = aIndicatorService.queryPageIndicatorLevel4(params);
//        System.out.println(page);
        return R.ok().put("page", page);
    }


    /**
     * 校验权重之和
     */
    public R assertIndicatorSum(AIndicatorEntity aIndicator){
        //如果当前节点权重之和大于100
        List<AIndicatorEntity> aIndicatorEntities = aIndicatorService.getIndicatorsByParentId(aIndicator.getParentId());
        System.out.println("aIndicatorEntities"+aIndicatorEntities);

        Integer sum = 0;
        for (AIndicatorEntity aIndicatorEntity1 : aIndicatorEntities){
            sum += aIndicatorEntity1.getIndicatorWeight();
        }
        System.out.println("sum="+sum);
        if (sum>=100){
            return R.error(HttpStatus.SC_INTERNAL_SERVER_ERROR,"当前权重相加之和大于100，请修改其他指标！");
        }
        if (sum+aIndicator.getIndicatorWeight()>100){
            return R.error(HttpStatus.SC_INTERNAL_SERVER_ERROR,"新增后权重之和大于100，请修改权重值！");
        }
        return  R.ok();
    }



}
