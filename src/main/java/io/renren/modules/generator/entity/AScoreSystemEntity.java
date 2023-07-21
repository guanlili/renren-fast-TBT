package io.renren.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 分制管理表
 * 
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-05-04 22:19:18
 */
@Data
@TableName("A_score_system")
public class AScoreSystemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * 分制
	 */
	private Integer scoreSystem;
	/**
	 * 分值（个位）
	 */
	private Integer scoreSingle;
	/**
	 * 分数（百分）
	 */
	private Integer scorePercent;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
