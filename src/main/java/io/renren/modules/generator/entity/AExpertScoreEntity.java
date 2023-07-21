package io.renren.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 专家打分表
 * 
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-05-04 22:19:18
 */
@Data
@TableName("A_expert_score")
public class AExpertScoreEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * 公司id
	 */
	private Integer companyId;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 指标id
	 */
	private Integer indicatorId;
	/**
	 * 指标名称
	 */
	private String indicatorName;
	/**
	 * 自评打分
	 */
	private Integer selfScore;
	/**
	 * 专家打分
	 */
	private Integer expertScore;
	/**
	 * 得分换算
	 */
	private String scoreConversion;
	/**
	 * 分制
	 */
	private Integer scoreSystem;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 公司得分
	 */
	private Double companyScore;

	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 年份
	 */
	private Integer year;


}
