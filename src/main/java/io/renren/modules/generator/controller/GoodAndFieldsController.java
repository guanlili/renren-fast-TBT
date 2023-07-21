package io.renren.modules.generator.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.common.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.generator.entity.GoodAndFieldsEntity;
import io.renren.modules.generator.service.GoodAndFieldsService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 领域和商品名称字典表
 *
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-01-18 08:48:02
 */
@RestController
@RequestMapping("generator/goodandfields")
public class GoodAndFieldsController {
    @Autowired
    private GoodAndFieldsService goodAndFieldsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:goodandfields:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = goodAndFieldsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:goodandfields:info")
    public R info(@PathVariable("id") Integer id){
		GoodAndFieldsEntity goodAndFields = goodAndFieldsService.getById(id);

        return R.ok().put("goodAndFields", goodAndFields);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:goodandfields:save")
    public R save(@RequestBody GoodAndFieldsEntity goodAndFields){
        goodAndFields.setUserName(ShiroUtils.getUserEntity().getUsername());
        goodAndFieldsService.save(goodAndFields);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:goodandfields:update")
    public R update(@RequestBody GoodAndFieldsEntity goodAndFields){
        goodAndFields.setUserName(ShiroUtils.getUserEntity().getUsername());
		goodAndFieldsService.updateById(goodAndFields);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:goodandfields:delete")
    public R delete(@RequestBody Integer[] ids){
		goodAndFieldsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
