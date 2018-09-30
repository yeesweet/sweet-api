package com.sweet.api.entity.bean;

import lombok.Data;

import java.util.Date;

/**
 * 页面模块子表
 * @author wang.s2
 *
 */
@Data
public class CmsModuleDetails{

	//主键id
	private Long detailId;
	//标题名称
	private String title;
	//子标题名称
	private String subTitle;
	//图片地址
	private String imgUrl;
	//排序编号
	private Integer sortNo;
	//链接类型
	private Integer subType;
	//基础数据id
	private String commonId;
	//子模块开始时间（为轮播图设计）
	private Date subStartTime;
	//子模块结束时间（为轮播图设计）
	private Date subEndTime;
	//子模块开始时间（为轮播图设计页面使用）
	private String subStarttime;
	//子模块结束时间（为轮播图设计页面使用）
	private String subEndtime;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	//模块id
	private Long moduleId;
	//操作人
	private String operator;
	//上线状态：1.已上线(活动中) 2.待上线 (未开始) 3.未上线(未启用)
	private Integer onlineState;
	//商品名称
	private String commodityName;
	/**
	 * 市场价
	 */
	private Double marketPrice;
	/**
	 * 销售价
	 */
	private Double salePrice;
}
