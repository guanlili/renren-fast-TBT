package io.renren.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 领域和商品名称字典表
 * 
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-01-18 08:48:02
 */
@Data
@TableName("tb_good_and_fields")
public class GoodAndFieldsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * 商品编码
	 */
	private String productNum;
	/**
	 * 领域名称
	 */
	private String fieldName;

	/**
	 * 用户名
	 */
	private String userName;

}
