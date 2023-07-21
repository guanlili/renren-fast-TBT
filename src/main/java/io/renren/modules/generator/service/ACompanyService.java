package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.ACompanyEntity;

import java.util.List;
import java.util.Map;

/**
 * 公司管理表
 *
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-05-04 22:19:18
 */
public interface ACompanyService extends IService<ACompanyEntity> {

    PageUtils queryPage(Map<String, Object> params);
    PageUtils querycompanyTypePage(Map<String, Object> params);
    List<ACompanyEntity> queryPageByCompanyType(Map<String, Object> params);
    List<ACompanyEntity> findAllCompany(Integer year);
    ACompanyEntity findCompanyByCompnayName(String companyName,String year);
    List<ACompanyEntity> queryPageByCompanyType1(String companyType,String year);




}

