package io.renren.modules.generator.controller;

import com.alibaba.fastjson.JSON;
import io.renren.common.utils.ShiroUtils;
import io.renren.modules.generator.entity.Goods2Entity;
import io.renren.modules.generator.entity.Goods8Entity;
import io.renren.modules.generator.service.Goods2Service;
import io.renren.modules.generator.service.Goods8Service;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

/**
 * @version 1.0
 * @auther guanhongli
 * @date 2023/1/15 4:35 PM
 */
@RestController
@RequestMapping("echart")
public class Echart8Controller {
    @Autowired
    private Goods8Service goods8Service;



    class Result {
        public List<String> xProductName;
        public List<BigDecimal> yTotalAmount;

        /**
         * xProductName，yTotalAmount
         * @param xProductName
         * @param yTotalAmount
         */
        public Result(List<String> xProductName, List<BigDecimal> yTotalAmount) {
            this.xProductName = xProductName;
            this.yTotalAmount = yTotalAmount;
        }
    }
    /**
     * 按贸易金额，从大到小，统计产品贸易量排序。
     * @param params
     * @return
     */
    @RequestMapping("/productTradeVolume")
    public Map<String,Object> productTradeVolume(@RequestParam Map<String, Object> params){
        System.out.println("params"+params);
        String fieldName = (String) params.get("fieldName");
        String userName= ShiroUtils.getUserEntity().getUsername();

        String pickDateStart="";
        String pickDateEnd="";
        if (params.get("pickDateStart")!=null){
            pickDateStart = StringUtils.substring((String) params.get("pickDateStart"),0,7).replace("-","");
            pickDateEnd = StringUtils.substring((String) params.get("pickDateEnd"),0,7).replace("-","");
        }
        System.out.println("pickDateStart"+pickDateStart);
        System.out.println("pickDateEnd"+pickDateEnd);
        List<Goods8Entity> goods8Entities = goods8Service.findTotalAmountByProductName(fieldName,pickDateStart,pickDateEnd,userName);
        System.out.println(goods8Entities);
        Map<String,Object> map = new HashMap<>();
        //x查询贸易总金额
        List<BigDecimal>  y_TotalAmount =new ArrayList<>();
        //x查询商品名称
        List<String> x_ProductName =new ArrayList<>();
        //组装x轴名称
        List<String> x_name =new ArrayList<>();
        for (Goods8Entity goods8Entity : goods8Entities){
            x_ProductName.add(goods8Entity.getProductName());
            y_TotalAmount.add(goods8Entity.getRmbAmount());
            x_name.add((goods8Entity.getProductNum()+"｜"+goods8Entity.getProductName()));

        }
        map.put("y_TotalAmount",y_TotalAmount);
        map.put("x_ProductName",x_ProductName);
        map.put("x_name",x_name);

        map.put("code",0);
        System.out.println(y_TotalAmount.size());
        System.out.println(x_ProductName.size());
        return map;
    }

    /**
     * 按贸易金额，从大到小，统计国家贸易量排序
     * @param params
     * @return
     */
    @RequestMapping("/nationalTradeVolume")
    public Map<String,Object> nationalTradeVolume(@RequestParam Map<String, Object> params){
        System.out.println("params"+params);
        String userName= ShiroUtils.getUserEntity().getUsername();

        String fieldName = (String) params.get("fieldName");
        System.out.println("fieldName"+fieldName);
        String pickDateStart="";
        String pickDateEnd="";
        if (params.get("pickDateStart")!=null){
            pickDateStart = StringUtils.substring((String) params.get("pickDateStart"),0,7).replace("-","");
            pickDateEnd = StringUtils.substring((String) params.get("pickDateEnd"),0,7).replace("-","");
        }

        List<Goods8Entity> goods8Entities = goods8Service.findTotalAmountByNationalName(fieldName,pickDateStart,pickDateEnd,userName);
        System.out.println(goods8Entities);
        Map<String,Object> map = new HashMap<>();
        //y查询贸易总金额
        List<String> x_TradePartnerName =new ArrayList<>();
        //x查询国家名称
        List<BigDecimal> y_TotalAmount =new ArrayList<>();
        //组装x轴名称
        List<String> x_name =new ArrayList<>();
        for (Goods8Entity goods8Entity : goods8Entities){
            x_TradePartnerName.add(goods8Entity.getTradePartnerName());
            y_TotalAmount.add(goods8Entity.getRmbAmount());
            x_name.add((goods8Entity.getProductNum()+"｜"+goods8Entity.getProductName()));

        }
        map.put("x_TradePartnerName",x_TradePartnerName);
        map.put("y_TotalAmount",y_TotalAmount);
        map.put("x_name",x_name);

        map.put("code",0);
        System.out.println(x_TradePartnerName);
        System.out.println(y_TotalAmount);
        System.out.println("map："+map);
        return map;
    }
    /**
     * 按贸易金额，从大到小，统计地区贸易量排序
     * @param params
     * @return
     */
    @RequestMapping("/areaTradeVolume")
    public Map<String,Object> areaTradeVolume(@RequestParam Map<String, Object> params){
        System.out.println("params"+params);
        String userName= ShiroUtils.getUserEntity().getUsername();

        String fieldName = (String) params.get("fieldName");
        System.out.println("fieldName"+fieldName);
        String pickDateStart="";
        String pickDateEnd="";
        if (params.get("pickDateStart")!=null){
            pickDateStart = StringUtils.substring((String) params.get("pickDateStart"),0,7).replace("-","");
            pickDateEnd = StringUtils.substring((String) params.get("pickDateEnd"),0,7).replace("-","");
        }

        List<Goods8Entity> goods8Entities = goods8Service.findTotalAmountByAreaName(fieldName,pickDateStart,pickDateEnd,userName);
        System.out.println(goods8Entities);
        Map<String,Object> map = new HashMap<>();
        //y查询贸易总金额
        List<String> x_RegistrationPlaceName =new ArrayList<>();
        //x查询地区名称
        List<BigDecimal> y_TotalAmount =new ArrayList<>();
        for (Goods8Entity goods8Entity : goods8Entities){
            x_RegistrationPlaceName.add(goods8Entity.getRegistrationPlaceName());
            y_TotalAmount.add(goods8Entity.getRmbAmount());
        }
        map.put("x_TradePartnerName",x_RegistrationPlaceName);
        map.put("y_TotalAmount",y_TotalAmount);
        map.put("code",0);
        System.out.println(x_RegistrationPlaceName);
        System.out.println(y_TotalAmount);
        System.out.println("map："+map);
        return map;
    }
    /**
     * 统计14个国家，按产品金额从大到小，统计每个国家的产品贸易情况
     * @param params
     * @return
     */
    @RequestMapping("/productTradeByNational")
    public Map<String,Object> productTradeByNational(@RequestParam Map<String, Object> params){
        System.out.println("params"+params);
        String userName= ShiroUtils.getUserEntity().getUsername();

        String name = (String) params.get("national");
//        String name2 = (String) params.get("national2");
        System.out.println(name);
//        System.out.println(name2);

        String fieldName = (String) params.get("fieldName");
        String pickDateStart="";
        String pickDateEnd="";
        if (params.get("pickDateStart")!=null){
            pickDateStart = StringUtils.substring((String) params.get("pickDateStart"),0,7).replace("-","");
            pickDateEnd = StringUtils.substring((String) params.get("pickDateEnd"),0,7).replace("-","");
        }

        List<Goods8Entity> goods8Entities = goods8Service.findTotalAmountByProductNameOfNation(name,fieldName,pickDateStart,pickDateEnd,userName);
        System.out.println(goods8Entities);
        Map<String,Object> map = new HashMap<>();
        //y查询贸易总金额
        List<String> x_ProductName =new ArrayList<>();
        //x查询国家名称
        List<BigDecimal> y_TotalAmount =new ArrayList<>();
        //组装x轴名称
        List<String> x_name =new ArrayList<>();
        for (Goods8Entity goods8Entity : goods8Entities){
            x_ProductName.add(goods8Entity.getProductName());
            y_TotalAmount.add(goods8Entity.getRmbAmount());
            x_name.add((goods8Entity.getProductNum()+"｜"+goods8Entity.getProductName()));

        }
        map.put("x_ProductName",x_ProductName);
        map.put("y_TotalAmount",y_TotalAmount);
        map.put("x_name",x_name);

        map.put("code",0);
        System.out.println(x_ProductName);
        System.out.println(y_TotalAmount);
        return map;
    }
    /**
     * 统计31个省市，按产品金额从大到小，**统计每个省市的产品贸易情况**。
     * @param params
     * @return
     */
    @RequestMapping("/productTradeByArea")
    public Map<String,Object> productTradeByArea(@RequestParam Map<String, Object> params){
        System.out.println("params"+params);
        String userName= ShiroUtils.getUserEntity().getUsername();

        String area = (String) params.get("area");
        String fieldName = (String) params.get("fieldName");
        String pickDateStart="";
        String pickDateEnd="";
        if (params.get("pickDateStart")!=null){
            pickDateStart = StringUtils.substring((String) params.get("pickDateStart"),0,7).replace("-","");
            pickDateEnd = StringUtils.substring((String) params.get("pickDateEnd"),0,7).replace("-","");
        }

        System.out.println(area);
        List<Goods8Entity> goods8Entities = goods8Service.findTotalAmountByProductNameOfArea(area,fieldName,pickDateStart,pickDateEnd,userName);
        System.out.println(goods8Entities);
        Map<String,Object> map = new HashMap<>();
        //y查询贸易总金额
        List<String> x_ProductName =new ArrayList<>();
        //x查询国家名称
        List<BigDecimal> y_TotalAmount =new ArrayList<>();
        //组装x轴名称
        List<String> x_name =new ArrayList<>();
        for (Goods8Entity goods8Entity : goods8Entities){
            x_ProductName.add(goods8Entity.getProductName());
            y_TotalAmount.add(goods8Entity.getRmbAmount());
            x_name.add((goods8Entity.getProductNum()+"｜"+goods8Entity.getProductName()));

        }
        map.put("x_ProductName",x_ProductName);
        map.put("y_TotalAmount",y_TotalAmount);
        map.put("x_name",x_name);

        map.put("code",0);
        System.out.println(x_ProductName);
        System.out.println(y_TotalAmount);
        return map;
    }




    /**
     * 不同国家的贸易总量对比。
     * @param params
     * @return
     */
    @RequestMapping("/compareProductTradeByNational")
    public Map<String,Object> compareProductTradeByNational(@RequestParam Map<String, Object> params){
        //deal recive params
        System.out.println("params"+params);
        String userName= ShiroUtils.getUserEntity().getUsername();

        String national1 = (String) params.get("nationName1");
        String national2 = (String) params.get("nationName2");
        String fieldName = (String) params.get("fieldName");

        System.out.println("national1"+national1);
        System.out.println("national2"+national2);

        String pickDateStart="";
        String pickDateEnd="";
        if (params.get("pickDateStart")!=null){
            pickDateStart = StringUtils.substring((String) params.get("pickDateStart"),0,7).replace("-","");
            pickDateEnd = StringUtils.substring((String) params.get("pickDateEnd"),0,7).replace("-","");
        }

            Map<String,Object> returnMap = new HashMap<>();
        //db
        List<Goods8Entity> goods8Entities1 = goods8Service.findTotalAmountByProductNameOfNation(national1,fieldName,pickDateStart,pickDateEnd,userName);
//        System.out.println(goods8Entities1);
        List<Goods8Entity> goods8Entities2 = goods8Service.findTotalAmountByProductNameOfNation(national2,fieldName,pickDateStart,pickDateEnd,userName);
//        System.out.println(goods8Entities2);
        //查询商品名称和贸易总额
        Result result1 = findProductNameAndTotalAmount(goods8Entities1);
        Result result2 =findProductNameAndTotalAmount(goods8Entities2);
        //合并xProductNameList
        List<String> x_name = new ArrayList<>(result1.xProductName);
        x_name.removeAll(result2.xProductName);
        x_name.addAll(result2.xProductName);
        System.out.println(x_name);
        //获取合并后的yTotalAmountList
        List<BigDecimal> yTotalAmountList1=findyTotalAmount(x_name,result1.xProductName,result1.yTotalAmount);
        List<BigDecimal> yTotalAmountList2=findyTotalAmount(x_name,result2.xProductName,result2.yTotalAmount);
        returnMap.put("code",0);
        returnMap.put("xProductNameList",x_name);
        returnMap.put("y_TotalAmount1",yTotalAmountList1);
        returnMap.put("y_TotalAmount2",yTotalAmountList2);
        System.out.println(returnMap);

        return returnMap;
    }


    /**
     * 不同地区之间的贸易总量对比。
     * @param params
     * @return
     */
    @RequestMapping("/compareProductTradeByArea")
    public Map<String,Object> compareProductTradeByArea(@RequestParam Map<String, Object> params){
        //deal recive params
        System.out.println("params"+params);
        String userName= ShiroUtils.getUserEntity().getUsername();

        String areaName1 = (String) params.get("areaName1");
        String areaName2 = (String) params.get("areaName2");
        String fieldName = (String) params.get("fieldName");
        String pickDateStart="";
        String pickDateEnd="";
        if (params.get("pickDateStart")!=null){
            pickDateStart = StringUtils.substring((String) params.get("pickDateStart"),0,7).replace("-","");
            pickDateEnd = StringUtils.substring((String) params.get("pickDateEnd"),0,7).replace("-","");
        }

        Map<String,Object> returnMap = new HashMap<>();
        //db
        List<Goods8Entity> goods8Entities1 = goods8Service.findTotalAmountByProductNameOfArea(areaName1,fieldName,pickDateStart,pickDateEnd,userName);
//        System.out.println(goods8Entities1);
        List<Goods8Entity> goods8Entities2 = goods8Service.findTotalAmountByProductNameOfArea(areaName2,fieldName,pickDateStart,pickDateEnd,userName);
//        System.out.println(goods8Entities2);
        //查询商品名称和贸易总额
        Result result1 = findProductNameAndTotalAmount(goods8Entities1);
        Result result2 =findProductNameAndTotalAmount(goods8Entities2);
        System.out.println("result2"+result2);
        String json= JSON.toJSONString(result2);
        System.out.println("json="+json);
        //合并xProductNameList
        List<String> x_name = new ArrayList<>(result1.xProductName);
        x_name.removeAll(result2.xProductName);
        x_name.addAll(result2.xProductName);
        System.out.println(x_name);
        //获取合并后的yTotalAmountList
        List<BigDecimal> yTotalAmountList1=findyTotalAmount(x_name,result1.xProductName,result1.yTotalAmount);
        List<BigDecimal> yTotalAmountList2=findyTotalAmount(x_name,result2.xProductName,result2.yTotalAmount);
        returnMap.put("code",0);
        returnMap.put("xProductNameList",x_name);
        returnMap.put("y_TotalAmount1",yTotalAmountList1);
        returnMap.put("y_TotalAmount2",yTotalAmountList2);
        System.out.println(returnMap);

        return returnMap;
    }
    /**
     * 根据xProductNameList，xProductName，yTotalAmount获取yTotalAmountList
     * @param xProductNameList
     * @param xProductName
     * @param yTotalAmount
     * @return
     */
    public List<BigDecimal> findyTotalAmount(List<String> xProductNameList,List<String> xProductName,List<BigDecimal> yTotalAmount){

        List<BigDecimal> yTotalAmountList =new ArrayList<>();
        int j= 0;
        //判断大列表是在小列表中
        for (String name:xProductNameList){
            //如果存在元素赋值
            if (xProductName.contains(name)){
                yTotalAmountList.add(yTotalAmount.get(j));
                j++;
            }else {
                yTotalAmountList.add(BigDecimal.valueOf(0));
            }
        }
        System.out.println("yTotalAmountList"+yTotalAmountList);
        return yTotalAmountList;
    }

    /**
     * 查询贸易总金额和产品名称
     * @param goods8Entities
     * @return
     */
    public Result findProductNameAndTotalAmount(List<Goods8Entity> goods8Entities){
        List<String> xProductName =new ArrayList<>();
        List<BigDecimal> yTotalAmount =new ArrayList<>();
        List<String> x_name =new ArrayList<>();

        for (Goods8Entity goods8Entity : goods8Entities){
            xProductName.add(goods8Entity.getProductName());
            yTotalAmount.add(goods8Entity.getRmbAmount());
            x_name.add((goods8Entity.getProductNum()+"｜"+goods8Entity.getProductName()));

        }
        return new Result(x_name, yTotalAmount);
    }


    }