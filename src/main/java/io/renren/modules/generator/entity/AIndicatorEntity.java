package io.renren.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 指标管理表
 * 
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-05-04 22:19:18
 */
@Data
@TableName("A_Indicator")
public class AIndicatorEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * 指标名称
	 */
	private String indicatorName;
	/**
	 * 指标等级
	 */
	private Integer indicatorLevel;
	/**
	 * 指标权重
	 */
	private Integer indicatorWeight;
	/**
	 * 父亲id
	 */
	private Integer parentId;

	/**
	 * 父亲名字
	 */
	private String parentName;

	/**
	 * 星标
	 */
	private Integer star;
	/**
	 * 创建时间
	 */
	private Date createTime;


}
