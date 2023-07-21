package io.renren.modules.generator.controller;
import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.renren.modules.generator.entity.*;
import io.renren.modules.generator.service.*;
import org.apache.http.HttpStatus;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;

import static io.renren.modules.app.utils.commonUtils.*;


/**
 * 专家打分表
 *
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-05-04 22:19:18
 */
@RestController
@RequestMapping("generator/aexpertscore")
public class AExpertScoreController {
    @Autowired
    private ACompanyService aCompanyService;

    @Autowired
    private AExpertScoreService aExpertScoreService;

    @Autowired
    private AIndicatorService aIndicatorService;

    @Autowired
    private AScoreSystemService aScoreSystemService;

    @Autowired
    private ACompanyAndScoreService aCompanyAndScoreService;

    @Autowired
    private AAverageScoreService aAverageScoreService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:aexpertscore:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = aExpertScoreService.queryPage(params);
        Map<String, Object> map =new HashMap<>();
//        map.put("companyId",1);
//        map.put("year",2023);
//        getCompanyScore(map);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:aexpertscore:info")
    public R info(@PathVariable("id") Integer id){
		AExpertScoreEntity aExpertScore = aExpertScoreService.getById(id);
        return R.ok().put("aExpertScore", aExpertScore);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:aexpertscore:save")
    public R save(@RequestBody AExpertScoreEntity aExpertScore){
        //判断是否插入重复数据
        AExpertScoreEntity aExpertScoreEntity = aExpertScoreService.getCompanyScoreByCompanyNameAndIndicator(aExpertScore.getCompanyName(),String.valueOf(aExpertScore.getIndicatorId()),String.valueOf(aExpertScore.getYear()));
        if (aExpertScoreEntity!=null){
            return R.error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "插入失败，数据库中已存在记录");
        }
        //如果公司本项指标不涉及
        if (aExpertScore.getExpertScore() == null){
            aExpertScoreService.save(aExpertScore);
            //重新计算其他指标
            recalculateCompanyScore(aExpertScore);
        }else{
            //计算四级指标的实际分数
            Double companyScore = getCompanyScore4(aExpertScore);
            aExpertScore.setCompanyScore(companyScore);
            aExpertScoreService.save(aExpertScore);
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:aexpertscore:update")
    public R update(@RequestBody AExpertScoreEntity aExpertScore){
        System.out.println("req aExpertScore"+aExpertScore);
        Double companyScore = 0.0;
        //如果专家打分为空
        if (aExpertScore.getExpertScore() == null){
            aExpertScore.setCompanyScore(null);
            aExpertScoreService.updateExpertScoreById(aExpertScore);
            //重新计算其他指标
            recalculateCompanyScore(aExpertScore);
        }else {
            //专家打分不为空，计算公司实际分数
            companyScore = getCompanyScore4(aExpertScore);
            aExpertScore.setCompanyScore(companyScore);
            aExpertScore.setUpdateTime(new Date());
            aExpertScoreService.updateById(aExpertScore);
        }
        //重新计算当前得3级指标得分
        calculateCompany3LevelScore0624(aExpertScore.getCompanyId());
        //重新计算所有的2级指标得分
        calculateCompany2LevelScore0624(aExpertScore.getCompanyId(), aExpertScore.getYear());
        //重新计算所有的1级指标得分
        calculateCompany1LevelScore0624(aExpertScore.getCompanyId(),aExpertScore.getYear());
        //重新计算所有的0级指标得分
        calculateCompany0LevelScore0624(aExpertScore.getCompanyId(),aExpertScore.getYear());
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:aexpertscore:delete")
    public R delete(@RequestBody Integer[] ids){
		aExpertScoreService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 获取全部表格数据
     * @param params
     * @return
     */
    @RequestMapping("/getTable")
//    @RequiresPermissions("generator:aexpertscore:getTable")
    public R getTable(@RequestParam Map<String, Object> params){
        //先查出来全部指标
        JSONArray jsonArray1 =new JSONArray();
        //查询一级指标
        List<AIndicatorEntity> indicatorEntitys1 = aIndicatorService.getIndicatorsByIndicatorLevel(1);
        for (AIndicatorEntity aIndicatorEntity1 : indicatorEntitys1){
            JSONObject jsonObject1 =new JSONObject();
            JSONArray jsonArray2 =new JSONArray();
            //查找二级指标
            List<AIndicatorEntity> indicatorEntitys2 = aIndicatorService.getIndicatorsByParentId(aIndicatorEntity1.getId());
            for (AIndicatorEntity aIndicatorEntity2 : indicatorEntitys2){
                JSONObject jsonObject2 =new JSONObject();
                JSONArray jsonArray3 =new JSONArray();
                //查找三级指标
                List<AIndicatorEntity> indicatorEntitys3 = aIndicatorService.getIndicatorsByParentId(aIndicatorEntity2.getId());
                for (AIndicatorEntity aIndicatorEntity3 : indicatorEntitys3){
                    JSONObject jsonObject3 =new JSONObject();
                    JSONArray jsonArray4 =new JSONArray();
                    //查找四级指标
                    List<AIndicatorEntity> indicatorEntitys4 = aIndicatorService.getIndicatorsByParentId(aIndicatorEntity3.getId());
                    for (AIndicatorEntity aIndicatorEntity4 : indicatorEntitys4){
                        JSONObject jsonObject4 =new JSONObject();
                        jsonObject4.put("id",aIndicatorEntity4.getId());
                        jsonObject4.put("name",aIndicatorEntity4.getIndicatorName());
                        jsonObject4.put("weight",aIndicatorEntity4.getIndicatorWeight());
                        jsonObject4.put("star", aIndicatorEntity4.getStar());
                        jsonArray4.add(jsonObject4);
                    }
                    jsonObject3.put("id",aIndicatorEntity3.getId());
                    jsonObject3.put("name",aIndicatorEntity3.getIndicatorName());
                    jsonObject3.put("weight",aIndicatorEntity3.getIndicatorWeight());
                    jsonObject3.put("children",jsonArray4);
                    jsonArray3.add(jsonObject3);
                }
                jsonObject2.put("id",aIndicatorEntity2.getId());
                jsonObject2.put("name",aIndicatorEntity2.getIndicatorName());
                jsonObject2.put("weight",aIndicatorEntity2.getIndicatorWeight());
                jsonObject2.put("children",jsonArray3);
                jsonArray2.add(jsonObject2);
            }
            jsonObject1.put("id",aIndicatorEntity1.getId());
            jsonObject1.put("name",aIndicatorEntity1.getIndicatorName());
            jsonObject1.put("weight",aIndicatorEntity1.getIndicatorWeight());
            jsonObject1.put("children",jsonArray2);
            jsonArray1.add(jsonObject1);
            System.out.println("jsonArray1="+jsonArray1);
            }

        return R.ok().put("page", jsonArray1);
    }

    /**
     * 获取公司排序
     * @param params
     * @return
     */
    @RequestMapping("/getCompanySort")
//    @RequiresPermissions("generator:aexpertscore:getCompanySort")
    public Map<String,Object> getCompanySort(@RequestParam Map<String, Object> params){
        System.out.println("sort="+params);
        String year = (String) params.get("year");
        Map<String,Object> map = new HashMap<>();
        List<String> xCompany = new ArrayList<>();
        List<Double> yCompanyScore = new ArrayList<>();
        List<CompanyInfo> companyInfos = new ArrayList<>();
        String indicatorId = (String) params.get("indicatorId");
        double average = 0.0;
        //如果没有选择指标信息，就是查询全部的总分数据
        if (Objects.equals(indicatorId, "")){
            System.out.println("查询指标总和，一级指标相加");
            List<ACompanyEntity>  aCompanyEntities = aCompanyService.queryPageByCompanyType(params);
            for (ACompanyEntity aCompanyEntity :aCompanyEntities){
                ACompanyAndScore aCompanyAndScore = aCompanyAndScoreService.getByCompanyAndIndicator(aCompanyEntity.getId(),0, Integer.valueOf(year));
                CompanyInfo companyInfo = new CompanyInfo();
                companyInfo.setCompanyName(aCompanyEntity.getCompanyName());
                //计算总体平均分数
                companyInfo.setCompanyScore(roundToTwoDecimalPlaces(aCompanyAndScore.getCompanyScore()));
                System.out.println("companyInfo="+companyInfo);
                companyInfos.add(companyInfo);
            }
            // 使用 Stream 排序
            companyInfos = companyInfos.stream().sorted(Comparator.comparing(CompanyInfo::getCompanyScore).reversed())
                    .collect(Collectors.toList());
            // 打印 list 集合
            companyInfos.forEach(p -> {
                xCompany.add(p.getCompanyName());
                yCompanyScore.add(p.getCompanyScore());
            });
            average = calculateAverageByCompanyType(aCompanyEntities,null, Integer.valueOf(year));

        }else{
            //查询当前指标信息
            AIndicatorEntity aIndicatorEntity = aIndicatorService.getIndicatorsById(Integer.valueOf(indicatorId));
            //根据公司类型查询公司列表
            List<ACompanyEntity> companyList = aCompanyService.queryPageByCompanyType(params);
            for (ACompanyEntity aCompanyEntity :companyList){
                //根据指标等级查询当前公司的指标分数
                Double companyScore = getCompanyScore(aCompanyEntity,aIndicatorEntity, Integer.valueOf(year));
                Double companyScore1 = Double.parseDouble(String.format("%.2f", companyScore));
                CompanyInfo companyInfo = new CompanyInfo();
                companyInfo.setCompanyName(aCompanyEntity.getCompanyName());
                companyInfo.setCompanyScore(companyScore1);
                companyInfos.add(companyInfo);
            }
            // 使用 Stream 排序
            companyInfos = companyInfos.stream().sorted(Comparator.comparing(CompanyInfo::getCompanyScore).reversed())
                    .collect(Collectors.toList());
            // 打印 list 集合
            companyInfos.forEach(p -> {
                xCompany.add(p.getCompanyName());
                yCompanyScore.add(p.getCompanyScore());
            });
            //根据公司类型计算平均分new
            average = calculateAverageByCompanyType(companyList,aIndicatorEntity, Integer.valueOf(year));
        }
        map.put("xCompany",xCompany);
        map.put("yCompanyScore",yCompanyScore);
        map.put("tableData",companyInfos);
        map.put("average",roundToTwoDecimalPlaces(average));
        map.put("code",0);
        System.out.println("map="+map);
        return map;
    }

    /**
     * 根据专家打分计算公司四级指标的实际分数
     * @param aExpertScore
     * @return
     */
    public Double getCompanyScore4(AExpertScoreEntity aExpertScore){
        //根据专家打分和分制获取公司得分
        AScoreSystemEntity aScoreSystemEntity = aScoreSystemService.getScoreByExpertScoreAndScoreSystem(String.valueOf(aExpertScore.getExpertScore()),String.valueOf(aExpertScore.getScoreSystem()));
//        System.out.println("aScoreSystemEntity="+aScoreSystemEntity);
        //根据分制和打分换算成分数
        Float companyScore = Float.valueOf(aScoreSystemEntity.getScorePercent());
//        System.out.println("companyScore"+companyScore);
        //根据指标id查找权重
        AIndicatorEntity aIndicatorEntity = aIndicatorService.getIndicatorsById(aExpertScore.getIndicatorId());
//        System.out.println("aIndicatorEntity="+aIndicatorEntity);
        //通过父亲id，查找全部孩子
        List<AIndicatorEntity> aIndicators = aIndicatorService.getIndicatorsByParentId(aIndicatorEntity.getParentId());
//        System.out.println("aIndicators="+aIndicators);
        //计算当前层级指标的权重和
        Double sumIndicatorWeight = 0.0;
        for (AIndicatorEntity aIndicator : aIndicators ){
            sumIndicatorWeight += aIndicator.getIndicatorWeight();
        }
//        System.out.println("sumIndicatorWeight="+sumIndicatorWeight);
        //实际得分*权重比例
        Double finalScore = companyScore * (aIndicatorEntity.getIndicatorWeight() / sumIndicatorWeight);
        System.out.println("finalScore="+finalScore);
        return finalScore;
    }


    /**
     * 计算公司各级指标的实际分数
     * @param aCompanyEntity
     * @param aIndicatorEntity
     * @return
     */
    public Double getCompanyScore(ACompanyEntity aCompanyEntity,AIndicatorEntity aIndicatorEntity,Integer year){
        Integer sumIndicator = aIndicatorService.getSumIndicatorWeightByParentIdNotNull(aIndicatorEntity.getId(),aCompanyEntity.getId(),year);
        if (sumIndicator==null){
            sumIndicator=100;
        }
        //如果四级指标
        if (aIndicatorEntity.getIndicatorLevel() == 4){
            //根据指标id和公司类型查找分数
            AExpertScoreEntity aExpertScoreEntity = aExpertScoreService.getCompanyScoreByCompanyNameAndIndicator(aCompanyEntity.getCompanyName(),String.valueOf(aIndicatorEntity.getId()), String.valueOf(year));
            if (aExpertScoreEntity!=null && aExpertScoreEntity.getCompanyScore()!=null){
                sumIndicator = aIndicatorService.getSumIndicatorWeightByParentId(aIndicatorEntity.getParentId(),aCompanyEntity.getId(),year);
                if (sumIndicator==null){
                    sumIndicator=100;
                }
                return Math.floor(aExpertScoreEntity.getCompanyScore()/aIndicatorEntity.getIndicatorWeight()*sumIndicator);
            }else {
                return 0.0;
            }
        }else if (aIndicatorEntity.getIndicatorLevel() == 3){
            ACompanyAndScore aCompanyAndScore = aCompanyAndScoreService.getByCompanyAndIndicator(aCompanyEntity.getId(),aIndicatorEntity.getId(),year);

            System.out.println("sumIndicator="+sumIndicator);
            System.out.println("aIndicatorEntity="+aIndicatorEntity);
            System.out.println("aCompanyAndScore="+aCompanyAndScore);
            return aCompanyAndScore.getCompanyScore();
        }else if(aIndicatorEntity.getIndicatorLevel() == 2) {
            ACompanyAndScore aCompanyAndScore = aCompanyAndScoreService.getByCompanyAndIndicator(aCompanyEntity.getId(),aIndicatorEntity.getId(),year);
            System.out.println("sumIndicator="+sumIndicator);
            System.out.println("aIndicatorEntity="+aIndicatorEntity);
            System.out.println("aCompanyAndScore="+aCompanyAndScore);
            return aCompanyAndScore.getCompanyScore();
        }else if(aIndicatorEntity.getIndicatorLevel() == 1){
            ACompanyAndScore aCompanyAndScore = aCompanyAndScoreService.getByCompanyAndIndicator(aCompanyEntity.getId(),aIndicatorEntity.getId(),year);
            System.out.println("aCompanyAndScore="+aCompanyAndScore);
            return aCompanyAndScore.getCompanyScore();
        }else {
            return null;
        }
    }


    /**
     * 根据公司类型计算公司各级指标的平均分数，全部公司类型为空。
     * @param
     * @param aIndicatorEntity
     * @return
     */
    public Double calculateAverageByCompanyType(List<ACompanyEntity>  aCompanyEntities,AIndicatorEntity aIndicatorEntity,Integer year){
        List<Double> companyScoreList = new ArrayList<>();
        System.out.println("aCompanyEntities="+aCompanyEntities);
        System.out.println("aIndicatorEntity="+aIndicatorEntity);
        //不选指标，只选类型，算平均值。
        if (aIndicatorEntity==null) {
            //存储1级指标得分
            List<Double> companyScoreList1 = new ArrayList<>();
            List<AIndicatorEntity> aIndicatorEntities1 = aIndicatorService.getIndicatorsByParentId(0);
            System.out.println();

            for (AIndicatorEntity aIndicatorEntity1:aIndicatorEntities1){
                double averageScore1 =0.0;
                //找到所有2级指标
                List<AIndicatorEntity> aIndicatorEntities2 = aIndicatorService.getIndicatorsByParentId(aIndicatorEntity1.getId());
                List<Double> companyScoreList2= new ArrayList<>();

                for (AIndicatorEntity aIndicatorEntity2:aIndicatorEntities2){
                    //存储2级指标得分
                    double averageScore2 =0.0;
                    //找到所有3级指标
                    List<AIndicatorEntity> aIndicatorEntities3 = aIndicatorService.getIndicatorsByParentId(aIndicatorEntity2.getId());
                    for (AIndicatorEntity aIndicatorEntity3:aIndicatorEntities3){
                        //存储全部三级指标得分
                        List<Double> companyScoreList3 = new ArrayList<>();
                        double companyScore2 = 0.0;
                        for (ACompanyEntity aCompanyEntity : aCompanyEntities){
                            //找到所有4级指标
                            List<AIndicatorEntity> aIndicatorEntities4 = aIndicatorService.getIndicatorsByParentId(aIndicatorEntity3.getId());
                            double averageScore3 =0.0;
                            for (AIndicatorEntity aIndicatorEntity4:aIndicatorEntities4){
                                //查找4级指标得分
                                AExpertScoreEntity aExpertScoreEntity = aExpertScoreService.getCompanyScoreByCompanyNameAndIndicator(aCompanyEntity.getCompanyName(),String.valueOf(aIndicatorEntity4.getId()),String.valueOf(year));
                                if(aExpertScoreEntity!=null && aExpertScoreEntity.getCompanyScore()!=null){
                                    //4级得分之和
                                    averageScore3 += aExpertScoreEntity.getCompanyScore();
                                }
                            }
                            companyScoreList3.add(averageScore3 * aIndicatorEntity3.getIndicatorWeight()/100.0);
                        }

                        System.out.println("companyScoreList3="+companyScoreList3);
                        //计算3级指标平均值得分
                        double companyScore3= calculateAverage(companyScoreList3);
                        //计算2级指标得分之和
                        averageScore2+=companyScore3;
                    }
                    companyScoreList2.add(averageScore2 * aIndicatorEntity2.getIndicatorWeight()/100.0);
                    //计算2级指标平均值得分

                }
                double companyScore2= calculateAverage(companyScoreList2);
                System.out.println("companyScoreList2="+companyScoreList2);

                companyScoreList1.add(calculateSum(companyScoreList2)* aIndicatorEntity1.getIndicatorWeight()/100.0);

            }
            System.out.println("companyScoreList1="+companyScoreList1);
            return calculateSum(companyScoreList1);
            } else if (aIndicatorEntity.getIndicatorLevel() == 4){
            //4级指标平均值

            System.out.println("当前4级指标平均值");
                for (ACompanyEntity aCompanyEntity : aCompanyEntities){
                    Double showScore = getCompanyScore(aCompanyEntity, aIndicatorEntity, year);
                    companyScoreList.add(showScore);
                    System.out.println("companyScoreList4"+companyScoreList);
                }

            }else if (aIndicatorEntity.getIndicatorLevel() == 3){
                System.out.println("当前3级指标平均值");
                for (ACompanyEntity aCompanyEntity : aCompanyEntities){
                    //找到所有4级指标
                    List<AIndicatorEntity> aIndicatorEntities4 = aIndicatorService.getIndicatorsByParentId(aIndicatorEntity.getId());
                    System.out.println("aIndicatorEntities4="+aIndicatorEntities4);
                    double averageScore3 =0.0;
                    for (AIndicatorEntity aIndicatorEntity4:aIndicatorEntities4){
                        //查找4级指标得分
                        AExpertScoreEntity aExpertScoreEntity = aExpertScoreService.getCompanyScoreByCompanyNameAndIndicator(aCompanyEntity.getCompanyName(),String.valueOf(aIndicatorEntity4.getId()),String.valueOf(year));
//                        System.out.println("aExpertScoreEntity="+aExpertScoreEntity);
                        if (aExpertScoreEntity!=null && aExpertScoreEntity.getCompanyScore()!=null){
                            //4级得分之和
                            averageScore3 += aExpertScoreEntity.getCompanyScore();
                        }

                    }
                    System.out.println("averageScore3="+averageScore3);
                    companyScoreList.add(averageScore3);
                }
            }else if(aIndicatorEntity.getIndicatorLevel() == 2) {
                System.out.println("当前2级指标平均值");
                double averageScore2 =0.0;
                //找到所有3级指标
                List<AIndicatorEntity> aIndicatorEntities3 = aIndicatorService.getIndicatorsByParentId(aIndicatorEntity.getId());
                System.out.println("aIndicatorEntities3="+aIndicatorEntities3);

                for (AIndicatorEntity aIndicatorEntity3:aIndicatorEntities3){
                    List<Double> companyScoreList3 = new ArrayList<>();
                    double companyScore2 = 0.0;

                    for (ACompanyEntity aCompanyEntity : aCompanyEntities){
                        //找到所有4级指标
                        List<AIndicatorEntity> aIndicatorEntities4 = aIndicatorService.getIndicatorsByParentId(aIndicatorEntity3.getId());
                        System.out.println("aIndicatorEntities4="+aIndicatorEntities4);
                        double averageScore3 =0.0;
                        for (AIndicatorEntity aIndicatorEntity4:aIndicatorEntities4){
                            //查找4级指标得分
                            AExpertScoreEntity aExpertScoreEntity = aExpertScoreService.getCompanyScoreByCompanyNameAndIndicator(aCompanyEntity.getCompanyName(),String.valueOf(aIndicatorEntity4.getId()),String.valueOf(year));
                            if(aExpertScoreEntity!=null && aExpertScoreEntity.getCompanyScore()!=null){
                                //4级得分之和
                                averageScore3 += aExpertScoreEntity.getCompanyScore();
                            }
                        }
                        //3级得分
                        companyScoreList3.add(averageScore3*aIndicatorEntity3.getIndicatorWeight()/100.0);
                    }
                    double companyScore3= calculateAverage(companyScoreList3);
                    averageScore2+=companyScore3;
                    System.out.println("companyScoreList3="+companyScoreList3);
                }
                System.out.println("111111="+averageScore2);
                return averageScore2;
                //得到一个保存2级指标，三级指标之和的list
            }else if(aIndicatorEntity.getIndicatorLevel() == 1){
            //找到所有2级指标
            List<AIndicatorEntity> aIndicatorEntities2 = aIndicatorService.getIndicatorsByParentId(aIndicatorEntity.getId());
            List<Double> companyScoreList2= new ArrayList<>();
            for (AIndicatorEntity aIndicatorEntity2:aIndicatorEntities2){
                //存储2级指标得分
                double averageScore2 =0.0;
                //找到所有3级指标
                List<AIndicatorEntity> aIndicatorEntities3 = aIndicatorService.getIndicatorsByParentId(aIndicatorEntity2.getId());
                for (AIndicatorEntity aIndicatorEntity3:aIndicatorEntities3){
                    //存储全部三级指标得分
                    List<Double> companyScoreList3 = new ArrayList<>();
                    double companyScore2 = 0.0;
                    for (ACompanyEntity aCompanyEntity : aCompanyEntities){
                        //找到所有4级指标
                        List<AIndicatorEntity> aIndicatorEntities4 = aIndicatorService.getIndicatorsByParentId(aIndicatorEntity3.getId());
                        double averageScore3 =0.0;
                        for (AIndicatorEntity aIndicatorEntity4:aIndicatorEntities4){
                            //查找4级指标得分
                            AExpertScoreEntity aExpertScoreEntity = aExpertScoreService.getCompanyScoreByCompanyNameAndIndicator(aCompanyEntity.getCompanyName(),String.valueOf(aIndicatorEntity4.getId()),String.valueOf(year));
                            if(aExpertScoreEntity!=null && aExpertScoreEntity.getCompanyScore()!=null){
                                //4级得分之和
                                averageScore3 += aExpertScoreEntity.getCompanyScore();
                            }
                        }
                        companyScoreList3.add(averageScore3 * aIndicatorEntity3.getIndicatorWeight()/100.0);
                    }
                    System.out.println("companyScoreList3="+companyScoreList3);
                    //计算3级指标平均值得分
                    double companyScore3= calculateAverage(companyScoreList3);
                    //计算2级指标得分之和
                    averageScore2+=companyScore3;
                }
                companyScoreList2.add(averageScore2 * aIndicatorEntity2.getIndicatorWeight()/100.0);
                System.out.println("companyScoreList2="+companyScoreList2);
            }
            return calculateSum(companyScoreList2);
            }else {
                System.out.println("数据异常，没有当前指标等级");
                return null;
            }
        return calculateAverage(companyScoreList);
    }

    /**
     * 计算三级指标的实际分数
     * @param aCompanyEntity
     * @param aIndicatorEntity
     * @return
     */
    public Double getCompanyScore3(ACompanyEntity aCompanyEntity,AIndicatorEntity aIndicatorEntity){
        //计算当前三级指标的权重和
        Integer sumindicatorWeight3 = 100;
        //根据三级指标查询四级指标
        String sumCompanyScore4 = aExpertScoreService.getSumCompanyScoreListByCompanyNameAndIndicator(aCompanyEntity.getCompanyName(),String.valueOf(aIndicatorEntity.getId()),String.valueOf(aCompanyEntity.getYear()));
        if (sumCompanyScore4==null){
            return 0.0;
        }
        //实际得分*权重比例
        return Double.parseDouble(String.format("%.2f",Double.parseDouble(sumCompanyScore4) * (double)aIndicatorEntity.getIndicatorWeight()/(double)sumindicatorWeight3));
    }


    /**
     * 计算二级指标的实际分数
     * @param aCompanyEntity
     * @param aIndicatorEntity
     * @return
     */
    public Double getCompanyScore2(ACompanyEntity aCompanyEntity,AIndicatorEntity aIndicatorEntity){
        //计算当前二级指标的权重比例
//        Integer sumindicatorWeight2 = aIndicatorService.getSumIndicatorWeightByParentId(aIndicatorEntity.getParentId());
//        System.out.println("sumindicatorWeight2"+sumindicatorWeight2);
        Integer sumindicatorWeight2 = 100;
        //根据二级指标查询三级指标
        List<AIndicatorEntity> aIndicatorEntities = aIndicatorService.getIndicatorsByParentId(aIndicatorEntity.getId());
        Double sumCompanyScore3 = 0.0;
        for (AIndicatorEntity aIndicatorEntity1:aIndicatorEntities){
            //获取三级指标的专家打分
            Double companyScore3 = getCompanyScore3(aCompanyEntity,aIndicatorEntity1);
            sumCompanyScore3 += companyScore3;
        }
        //实际得分*权重比例
//        return sumCompanyScore3 * (double)aIndicatorEntity.getIndicatorWeight()/(double)sumindicatorWeight2;
        return Double.parseDouble(String.format("%.2f",sumCompanyScore3 * (double)aIndicatorEntity.getIndicatorWeight()/(double)sumindicatorWeight2));
    }
    public R recalculateCompanyScore(AExpertScoreEntity aExpertScore) {
        System.out.println("recalculateCompanyScore");
        //查询同级父节点下其他指标
        List<AIndicatorEntity> aIndicatorEntities = aIndicatorService.getBrothersIndicatorsById(aExpertScore.getIndicatorId());
        System.out.println("aIndicatorEntities="+aIndicatorEntities);
        //重新计算兄弟指标分数
        Double sumIndicatorWeight = 0.0;
        //计算权重和
        for (AIndicatorEntity aIndicatorEntity1 :aIndicatorEntities){
            //根据指标id和公司id查找兄弟打分。
            AExpertScoreEntity aExpertScoreEntity1 = aExpertScoreService.getCompanyScoreByCompanyNameAndIndicator(aExpertScore.getCompanyName(),String.valueOf(aIndicatorEntity1.getId()), String.valueOf(2023));
            if (!aIndicatorEntity1.getId().equals(aExpertScore.getIndicatorId()) && aExpertScoreEntity1.getExpertScore() != null){
                sumIndicatorWeight += aIndicatorEntity1.getIndicatorWeight();
            }
        }
//        System.out.println("sumIndicatorWeight="+sumIndicatorWeight);
        for (AIndicatorEntity aIndicatorEntity1 :aIndicatorEntities){
            //根据指标id和公司id查找兄弟打分。
            AExpertScoreEntity aExpertScoreEntity1 = aExpertScoreService.getCompanyScoreByCompanyNameAndIndicator(aExpertScore.getCompanyName(),String.valueOf(aIndicatorEntity1.getId()), String.valueOf(2023));
            System.out.println("aExpertScoreEntity1="+aExpertScoreEntity1);
            if (!aIndicatorEntity1.getId().equals(aExpertScore.getIndicatorId()) && aExpertScoreEntity1.getExpertScore() != null){
                //根据专家打分和分制获取公司得分
                AScoreSystemEntity aScoreSystemEntity = aScoreSystemService.getScoreByExpertScoreAndScoreSystem(String.valueOf(aExpertScoreEntity1.getExpertScore()),String.valueOf(aExpertScoreEntity1.getScoreSystem()));
                //根据分制和打分换算成分数
                Float companyScore = Float.valueOf(aScoreSystemEntity.getScorePercent());
                //实际得分*权重比例
                Double finalScore = companyScore * (aIndicatorEntity1.getIndicatorWeight() / sumIndicatorWeight);
                aExpertScoreEntity1.setCompanyScore(Double.parseDouble(String.format("%.2f",finalScore)));
                aExpertScoreEntity1.setUpdateTime(new Date());
//                System.out.println("aExpertScoreEntity1="+aExpertScoreEntity1);
                aExpertScoreService.updateById(aExpertScoreEntity1);
            }
        }
        return R.ok();
    }

    /**
     * 计算当前公司的3级指标
     * @return
     */
    public R calculateCompany3LevelScore0624(Integer companyId) {
        ACompanyEntity aCompanyEntity =aCompanyService.getById(companyId);
        System.out.println("aCompanyEntity"+aCompanyEntity);
        //查询所有的3级指标
        List<AIndicatorEntity> aIndicatorEntities = aIndicatorService.getIndicatorsByIndicatorLevel(3);
//        System.out.println("aIndicatorEntities"+aIndicatorEntities);

        for(AIndicatorEntity aIndicatorEntity : aIndicatorEntities){
            System.out.println("aIndicatorEntity="+aIndicatorEntity);
            //计算3级指标
            //根据三级指标查询四级指标
            String sumCompanyScore4 = aExpertScoreService.getSumCompanyScoreListByCompanyNameAndIndicator(aCompanyEntity.getCompanyName(),String.valueOf(aIndicatorEntity.getId()),String.valueOf(aCompanyEntity.getYear()));


            ACompanyAndScore aCompanyAndScore =  aCompanyAndScoreService.getByCompanyAndIndicator(companyId,aIndicatorEntity.getId(),aCompanyEntity.getYear());
            if (sumCompanyScore4!=null){
                System.out.println("三级指标得分是四级指标实际得分之和="+sumCompanyScore4);
                aCompanyAndScore.setCompanyScore(Double.valueOf(sumCompanyScore4));
            }else {
                aCompanyAndScore.setCompanyScore(null);
            }
            //更新指标
            aCompanyAndScore.setIndicatorId(aIndicatorEntity.getId());
            aCompanyAndScore.setUpdateTime(new Date());
            System.out.println("aCompanyAndScore"+aCompanyAndScore);
            aCompanyAndScoreService.updateById(aCompanyAndScore);
        }

        return R.ok();
    }



    /**
     * 计算当前公司的2级指标
     * @return
     */
    public R calculateCompany2LevelScore0624(Integer companyId,Integer year) {
        ACompanyEntity aCompanyEntity =aCompanyService.getById(companyId);
        System.out.println("aCompanyEntity"+aCompanyEntity);
        //查询所有的2级指标
        List<AIndicatorEntity> aIndicatorEntities2Level = aIndicatorService.getIndicatorsByIndicatorLevel(2);
        System.out.println("aIndicatorEntities2Level"+aIndicatorEntities2Level);
        double sumindicatorWeight2 = 100.0;
        for(AIndicatorEntity aIndicatorEntity2 : aIndicatorEntities2Level){
            //根据二级指标查询三级指标权重。
            double sumScore = 0.0;
            List<AIndicatorEntity> aIndicatorEntities3Level = aIndicatorService.getIndicatorsByParentId(aIndicatorEntity2.getId());
            for (AIndicatorEntity aIndicatorEntity3 : aIndicatorEntities3Level){
                //查询3级指标得分
                ACompanyAndScore aCompanyAndScore3 = aCompanyAndScoreService.getByCompanyAndIndicator(companyId,aIndicatorEntity3.getId(),year);
                //计算3级指标权重得
                double result =  aCompanyAndScore3.getCompanyScore()*(aIndicatorEntity3.getIndicatorWeight() / sumindicatorWeight2);
                sumScore += result;
            }
            //更新指标
            ACompanyAndScore aCompanyAndScore2 = aCompanyAndScoreService.getByCompanyAndIndicator(companyId,aIndicatorEntity2.getId(),year);
            aCompanyAndScore2.setCompanyScore(sumScore);
            aCompanyAndScore2.setUpdateTime(new Date());
            aCompanyAndScoreService.updateById(aCompanyAndScore2);
        }
        return R.ok();
    }

    /**
     * 计算当前公司的1级指标
     * @return
     */
    public R calculateCompany1LevelScore0624(Integer companyId,Integer year) {
        ACompanyEntity aCompanyEntity =aCompanyService.getById(companyId);
        System.out.println("aCompanyEntity"+aCompanyEntity);
        //查询所有的2级指标
        List<AIndicatorEntity> aIndicatorEntities1Level = aIndicatorService.getIndicatorsByIndicatorLevel(1);
        System.out.println("aIndicatorEntities1Level"+aIndicatorEntities1Level);
        double sumindicatorWeight1 = 100.0;
        for(AIndicatorEntity aIndicatorEntity1 : aIndicatorEntities1Level){
            //根据二级指标查询三级指标权重。
            double sumScore = 0.0;
            List<AIndicatorEntity> aIndicatorEntities2Level = aIndicatorService.getIndicatorsByParentId(aIndicatorEntity1.getId());
            for (AIndicatorEntity aIndicatorEntity2 : aIndicatorEntities2Level){
                //查询3级指标得分
                ACompanyAndScore aCompanyAndScore2 = aCompanyAndScoreService.getByCompanyAndIndicator(companyId,aIndicatorEntity2.getId(),year);
                //计算3级指标权重得
                double result =  aCompanyAndScore2.getCompanyScore()*(aIndicatorEntity2.getIndicatorWeight() / sumindicatorWeight1);
                sumScore += result;
            }
            //更新指标
            ACompanyAndScore aCompanyAndScore2 = aCompanyAndScoreService.getByCompanyAndIndicator(companyId,aIndicatorEntity1.getId(),year);
            aCompanyAndScore2.setCompanyScore(sumScore);
            aCompanyAndScore2.setUpdateTime(new Date());
            aCompanyAndScoreService.updateById(aCompanyAndScore2);
        }
        return R.ok();
    }


    /**
     * 计算当前公司的0级指标(总分)
     * @return
     */
    public R calculateCompany0LevelScore0624(Integer companyId,Integer year) {
        ACompanyEntity aCompanyEntity =aCompanyService.getById(companyId);
        System.out.println("aCompanyEntity"+aCompanyEntity);
        double sumindicatorWeight1 = 100.0;
            //根据二级指标查询三级指标权重。
        double sumScore = 0.0;
        List<AIndicatorEntity> aIndicatorEntities0Level = aIndicatorService.getIndicatorsByParentId(0);
        for (AIndicatorEntity aIndicatorEntity1 : aIndicatorEntities0Level){
            //查询3级指标得分
            ACompanyAndScore aCompanyAndScore1 = aCompanyAndScoreService.getByCompanyAndIndicator(companyId,aIndicatorEntity1.getId(),year);
            //计算3级指标权重得
            double result =  aCompanyAndScore1.getCompanyScore()*(aIndicatorEntity1.getIndicatorWeight() / sumindicatorWeight1);
            sumScore += result;
        }
        //更新指标
        ACompanyAndScore aCompanyAndScore0 = aCompanyAndScoreService.getByCompanyAndIndicator(companyId,0,year);
        if (aCompanyAndScore0!=null){
            aCompanyAndScore0.setCompanyScore(sumScore);
            aCompanyAndScore0.setUpdateTime(new Date());
            aCompanyAndScoreService.updateById(aCompanyAndScore0);
        }else {
            ACompanyAndScore aCompanyAndScore1= new ACompanyAndScore();
            aCompanyAndScore1.setCompanyScore(sumScore);
            aCompanyAndScore1.setCreateTime(new Date());
            aCompanyAndScore1.setCompanyId(companyId);
            aCompanyAndScore1.setIndicatorId(0);
            aCompanyAndScore1.setYear(year);
            aCompanyAndScoreService.save(aCompanyAndScore1);
        }
        return R.ok();
    }


    /**
     * 雷达图指标，当前一级指标和标准做对比。
     * @return
     */
    @RequestMapping("/radarChart")
    public R radarChart(@RequestParam Map<String, Object> params) {
        System.out.println("params"+params);
        Integer companyId = (Integer) params.get("companyId");
        Map<String,Object> map = new HashMap<>();
        List<Integer> indicatorWeight = new ArrayList<>();
        List<Double> companyScore = new ArrayList<>();
        //查询一级指标权重
        List<AIndicatorEntity> aIndicatorEntities = aIndicatorService.getIndicatorsByIndicatorLevel(1);
        //根据指标名称和公司名称去查询一级指标得分
        for (AIndicatorEntity aIndicatorEntity :aIndicatorEntities){
            ACompanyAndScore aCompanyAndScore = aCompanyAndScoreService.getByCompanyAndIndicator(companyId,aIndicatorEntity.getId(),2023);
            indicatorWeight.add(aIndicatorEntity.getIndicatorWeight());
            companyScore.add(aCompanyAndScore.getCompanyScore());
        }
        map.put("indicatorWeight",indicatorWeight);
        map.put("companyScore",companyScore);
        map.put("code",0);
        System.out.println("map="+map);


        return R.ok();
    }



    /**
     * 根据年份和公司名称，得到公司下的全部得分。
     * @return
     */
    @RequestMapping("/getCompanyAllScore")
    public R getCompanyAllScore(@RequestParam Map<String, Object> params) {
        System.out.println("params"+params);
        String companyName = (String) params.get("companyName");
        String year = (String)params.get("year");
        //计算当前公司所有4级指标
        ACompanyEntity aCompanyEntity = aCompanyService.findCompanyByCompnayName(companyName,year);
        System.out.println("aCompanyEntity"+aCompanyEntity);
        List<AIndicatorEntity> aIndicatorEntities1 =aIndicatorService.getIndicatorsByIndicatorLevel(1);
        System.out.println("aIndicatorEntities"+aIndicatorEntities1);
        double sumScore =0.0;
        JSONArray jsonArray1 = new JSONArray();
        //一级指标数据
        for (AIndicatorEntity aIndicatorEntity1:aIndicatorEntities1){
            JSONObject jsonObject1 =new JSONObject();
            Double companyScore1 = getCompanyScore(aCompanyEntity,aIndicatorEntity1, Integer.valueOf(year));
            jsonObject1.put("id",aIndicatorEntity1.getId());
            jsonObject1.put("indicatorName",aIndicatorEntity1.getIndicatorName());
            jsonObject1.put("indicatorLevel",aIndicatorEntity1.getIndicatorLevel());
            jsonObject1.put("companyScore",roundToTwoDecimalPlaces(companyScore1));
            //计算总分
            sumScore += companyScore1*aIndicatorEntity1.getIndicatorWeight()/100.0;
            jsonArray1.add(jsonObject1);
            //计算当前公司所有2级指标
            List<AIndicatorEntity> aIndicatorEntities2 =aIndicatorService.getIndicatorsByParentId(aIndicatorEntity1.getId());
            JSONArray jsonArray2 = new JSONArray();
            for (AIndicatorEntity aIndicatorEntity2:aIndicatorEntities2){
                JSONObject jsonObject2 =new JSONObject();
                Double companyScore2 = getCompanyScore(aCompanyEntity,aIndicatorEntity2, Integer.valueOf(year));
                jsonObject2.put("id",aIndicatorEntity2.getId());
                jsonObject2.put("indicatorName",aIndicatorEntity2.getIndicatorName());
                jsonObject2.put("indicatorLevel",aIndicatorEntity2.getIndicatorLevel());
                jsonObject2.put("companyScore",roundToTwoDecimalPlaces(companyScore2));
                jsonArray2.add(jsonObject2);
                //计算当前公司所有3级指标
                List<AIndicatorEntity> aIndicatorEntities3 =aIndicatorService.getIndicatorsByParentId(aIndicatorEntity2.getId());
                JSONArray jsonArray3 = new JSONArray();
                for (AIndicatorEntity aIndicatorEntity3:aIndicatorEntities3){
                    JSONObject jsonObject3 =new JSONObject();
                    Double companyScore3 = getCompanyScore(aCompanyEntity,aIndicatorEntity3, Integer.valueOf(year));
                    jsonObject3.put("id",aIndicatorEntity3.getId());
                    jsonObject3.put("indicatorName",aIndicatorEntity3.getIndicatorName());
                    jsonObject3.put("indicatorLevel",aIndicatorEntity3.getIndicatorLevel());
                    jsonObject3.put("companyScore",roundToTwoDecimalPlaces(companyScore3));
                    jsonArray3.add(jsonObject3);
                    //计算当前公司所有3级指标
                    List<AIndicatorEntity> aIndicatorEntities4 =aIndicatorService.getIndicatorsByParentId(aIndicatorEntity3.getId());
                    JSONArray jsonArray4 = new JSONArray();
                    for (AIndicatorEntity aIndicatorEntity4:aIndicatorEntities4){
                        JSONObject jsonObject4 =new JSONObject();
                        Double companyScore4 = getCompanyScore(aCompanyEntity,aIndicatorEntity4, Integer.valueOf(year));
                        jsonObject4.put("id",aIndicatorEntity4.getId());
                        jsonObject4.put("indicatorName",aIndicatorEntity4.getIndicatorName());
                        jsonObject4.put("indicatorLevel",aIndicatorEntity4.getIndicatorLevel());
                        jsonObject4.put("companyScore",roundToTwoDecimalPlaces(companyScore4));
                        jsonArray4.add(jsonObject4);
                    }
                    jsonObject3.put("children",jsonArray4);
                }
                jsonObject2.put("children",jsonArray3);
                }
            jsonObject1.put("children",jsonArray2);
            }
        System.out.println("jsonArray111="+jsonArray1);
        System.out.println("sumScore="+roundToTwoDecimalPlaces(sumScore));
        Map<String, Object> page =new HashMap<>();
        page.put("sumScore", roundToTwoDecimalPlaces(sumScore));
        page.put("result", jsonArray1);
        return R.ok().put("page", page);
    }


}
