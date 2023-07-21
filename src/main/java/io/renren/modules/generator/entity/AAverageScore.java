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
@TableName("A_average_score")
public class AAverageScore {
    /**
     * id
     */
    private Integer id;

    /**
     * 指标id
     */
    private Integer indicatorId;
    /**
     * 指标等级
     */
    private Integer indicatorLevel;
    /**
     * 平均分数
     */
    private double averageScore;
    /**
     * 展示分数
     */
    private double showScore;
    /**
     * 年份
     */
    private Integer year;

    /**
     * 公司类型
     */
    private String companyType;

}
