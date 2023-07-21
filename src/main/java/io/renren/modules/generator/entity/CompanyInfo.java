package io.renren.modules.generator.entity;

import lombok.Data;

/**
 * @author guanhongli
 * @version 1.0
 * @auther guanhongli
 * @date 2023/5/10 11:39 PM
 */
@Data
public class CompanyInfo {
    /**
     * 公司id
     */
    private Integer id;
    /**
     * 公司名
     */
    private String companyName;
    /**
     * 公司得分
     */
    private Double companyScore;

    /**
     * 平均得分
     */
    private Double averageScore;
}
