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

import io.renren.modules.generator.entity.ACompanyEntity;
import io.renren.modules.generator.service.ACompanyService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 公司管理表
 *
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-05-04 22:19:18
 */
@RestController
@RequestMapping("generator/acompany")
public class ACompanyController {
    @Autowired
    private ACompanyService aCompanyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:acompany:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = aCompanyService.queryPage(params);
        System.out.println(page.getList());

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:acompany:info")
    public R info(@PathVariable("id") Integer id){
		ACompanyEntity aCompany = aCompanyService.getById(id);

        return R.ok().put("aCompany", aCompany);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:acompany:save")
    public R save(@RequestBody ACompanyEntity aCompany){
		aCompanyService.save(aCompany);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:acompany:update")
    public R update(@RequestBody ACompanyEntity aCompany){
		aCompanyService.updateById(aCompany);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:acompany:delete")
    public R delete(@RequestBody Integer[] ids){
		aCompanyService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 查询公司类型列表
     */
    @RequestMapping("/companyTypelist")
//    @RequiresPermissions("generator:acompany:list")
    public R companyTypelist(@RequestParam Map<String, Object> params){
        PageUtils page = aCompanyService.querycompanyTypePage(params);
        System.out.println("companyTypelist="+page.getList());
        return R.ok().put("page", page);
    }

}
