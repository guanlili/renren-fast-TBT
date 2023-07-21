package io.renren.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品编码（4位）
 * 
 * @author guanhongli
 * @email 785481747@qq.com
 * @date 2023-01-14 22:16:28
 */
@Data
@TableName("tb_goods4")
public class Goods4Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一标识
	 */
	@TableId
	private Long id;
	/**
	 * 商品编码（4位）
	 */
	private Integer productNum;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 贸易伙伴编码
	 */
	private Integer tradePartnerNum;
	/**
	 * 贸易伙伴名称
	 */
	private String tradePartnerName;
	/**
	 * 贸易方式编码
	 */
	private Integer tradeMethodNum;
	/**
	 * 贸易方式名称
	 */
	private String tradeMethodName;
	/**
	 * 注册地编码
	 */
	private Integer registrationPlaceNum;
	/**
	 * 注册地名称
	 */
	private String registrationPlaceName;
//	/**
//	 * 第一数量
//	 */
//	private Integer firstNum;
//	/**
//	 * 第一计量单位
//	 */
//	private String firstNumUnit;
//	/**
//	 * 第二数量
//	 */
//	private Integer secondNum;
//	/**
//	 * 第二计量单位
//	 */
//	private String secondNumUnit;
	/**
	 * 人民币金额
	 */
	private BigDecimal rmbAmount;
	/**
	 * 数据年月
	 */
	private Integer dataDate;
	/**
	 * 进出口类型
	 */
	private String tradeType;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 用户名
	 */
	private String userName;

}
