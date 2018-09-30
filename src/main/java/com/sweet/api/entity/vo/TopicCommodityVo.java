package com.sweet.api.entity.vo;

import lombok.Data;

/**
 * <p>
 * 专题商品表
 * </p>
 *
 * @author wang.s2
 * @since 2018-09-07
 */
@Data
public class TopicCommodityVo {

	private Long id;
    /**
     * 专题id
     */
	private Long topicId;
    /**
     * 商品编码
     */
	private String commodityNo;
    /**
     * 状态  1 上架  0 下架
     */
	private Integer status;
    /**
     * 编号
     */
	private Integer sortNo;

	/**
	 * 商品默认展示图片
	 */
	private String defaultPic;
	/**
	 * 商品名称
	 */
	private String commodityName;

	/**
	 * 销售价
	 */
	private Double salePrice;
	/**
	 * 市场价
	 */
	private Double marketPrice;
	/**
	 * 库存
	 */
	private Integer stock;

	/**
	 * 属性编码
	 */
	private String propNo;

}
