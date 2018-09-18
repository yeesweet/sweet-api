package com.sweet.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商品分类表
 * </p>
 *
 * @author wang.s
 * @since 2018-08-06
 */
@Data
public class Category implements Serializable{

    private static final long serialVersionUID = 1L;

	private Long id;
    /**
     * 分类名称
     */
	private String name;
    /**
     * 等级
     */
	private Integer level;
    /**
     * 分类图片
     */
	private String image;
    /**
     * 序号
     */
	private Integer sortNo;
    /**
     * 父节点
     */
	private Long pid;
    /**
     * 分类是否启用(1：启用；0：停用)
     */
	private Integer display;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 更新时间
     */
	private Date updateTime;
    /**
     * 操作人
     */
	private String operator;
}
