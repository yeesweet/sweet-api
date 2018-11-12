package com.sweet.api.entity;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 分类商品关系表
 * </p>
 *
 * @author wangsai
 * @since 2018-10-22
 */
@Data
public class CategoryCommodity{

    private static final long serialVersionUID = 1L;

	private Long id;
    /**
     * 分类id
     */
	private Long categoryId;
    /**
     * 商品编码
     */
	private String commodityNo;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 操作人
     */
	private String operator;
}
