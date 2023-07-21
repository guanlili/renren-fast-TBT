package io.renren.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 公司管理表
 * 
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-05-04 22:19:18
 */
@Data
@TableName("A_company")
public class ACompanyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 公司类型
	 */
	private String companyType;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 年份
	 */
	private Integer year;

}
