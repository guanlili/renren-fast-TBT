package io.renren.modules.generator.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.generator.entity.AReduceScoreIndicatorEntity;
import io.renren.modules.generator.service.AReduceScoreIndicatorService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 减分项管理表
 *
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-05-04 23:11:17
 */
@RestController
@RequestMapping("generator/areducescoreindicator")
public class AReduceScoreIndicatorController {
    @Autowired
    private AReduceScoreIndicatorService aReduceScoreIndicatorService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:areducescoreindicator:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = aReduceScoreIndicatorService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:areducescoreindicator:info")
    public R info(@PathVariable("id") Integer id){
		AReduceScoreIndicatorEntity aReduceScoreIndicator = aReduceScoreIndicatorService.getById(id);

        return R.ok().put("aReduceScoreIndicator", aReduceScoreIndicator);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:areducescoreindicator:save")
    public R save(@RequestBody AReduceScoreIndicatorEntity aReduceScoreIndicator){
		aReduceScoreIndicatorService.save(aReduceScoreIndicator);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:areducescoreindicator:update")
    public R update(@RequestBody AReduceScoreIndicatorEntity aReduceScoreIndicator){
		aReduceScoreIndicatorService.updateById(aReduceScoreIndicator);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:areducescoreindicator:delete")
    public R delete(@RequestBody Integer[] ids){
		aReduceScoreIndicatorService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
