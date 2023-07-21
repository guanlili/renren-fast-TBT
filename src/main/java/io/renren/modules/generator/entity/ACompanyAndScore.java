package io.renren.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @version 1.0
 * @auther guanhongli
 * @date 2023/5/23 3:20 PM
 */
@Data
@TableName("A_company_and_score")
public class ACompanyAndScore {
    /**
     * id
     */
    private Integer id;
    /**
     * 公司id
     */
    private Integer companyId;
    /**
     * 指标id
     */
    private Integer indicatorId;
    /**
     * 公司得分
     */
    private Double companyScore;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 年份
     */
    private Integer year;
}
