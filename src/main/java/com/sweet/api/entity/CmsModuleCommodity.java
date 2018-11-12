package com.sweet.api.entity;

import lombok.Data;

import java.util.Date;

/**
 * 页面模块商品表
 * @author wang.s2
 *
 */
@Data
public class CmsModuleCommodity{

	//主键id
	private Long commodityId;
	//商品编号
	private String commodityCode;
	//排序编号
	private Integer sortNo;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	//模块id
	private Long moduleId;
	//操作人
	private String operator;
	//商品图片
	private String defaultPic;
	//库存数量
	private Integer inventoryNumber = 0;
	/**
	 * 市场价
	 */
	private Double marketPrice;
	/**
	 * 销售价
	 */
	private Double salePrice;
}
