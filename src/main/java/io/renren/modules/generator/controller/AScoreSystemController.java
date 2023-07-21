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

import io.renren.modules.generator.entity.AScoreSystemEntity;
import io.renren.modules.generator.service.AScoreSystemService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 分制管理表
 *
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-05-04 22:19:18
 */
@RestController
@RequestMapping("generator/ascoresystem")
public class AScoreSystemController {
    @Autowired
    private AScoreSystemService aScoreSystemService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:ascoresystem:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = aScoreSystemService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:ascoresystem:info")
    public R info(@PathVariable("id") Integer id){
		AScoreSystemEntity aScoreSystem = aScoreSystemService.getById(id);
        return R.ok().put("aScoreSystem", aScoreSystem);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:ascoresystem:save")
    public R save(@RequestBody AScoreSystemEntity aScoreSystem){
		aScoreSystemService.save(aScoreSystem);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:ascoresystem:update")
    public R update(@RequestBody AScoreSystemEntity aScoreSystem){
		aScoreSystemService.updateById(aScoreSystem);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:ascoresystem:delete")
    public R delete(@RequestBody Integer[] ids){
		aScoreSystemService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
