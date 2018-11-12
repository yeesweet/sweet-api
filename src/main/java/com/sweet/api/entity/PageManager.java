package com.sweet.api.entity;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 页面表
 * </p>
 *
 * @author wang.s
 * @since 2018-08-21
 */
@Data
public class PageManager{

    /**
     * 页面id
     */
	private Long id;
    /**
     * 页面名称
     */
	private String name;
    /**
     * 顶部标题
     */
	private String topName;
    /**
     * 落地页类型(1 首页 1026 二级页)
     */
	private Integer type;
    /**
     * 背景类型(1:背景图片;2:背景颜色)
     */
	private Integer typeBg;
    /**
     * 背景图片链接或者颜色值
     */
	private String bg;
    /**
     * 是否启用(0：停用  1：启用)
     */
	private Integer isDisplay;
    /**
     * 是否删除(0：未删除  1：删除)
     */
	private Integer isDelete;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 修改时间
     */
	private Date updateTime;
    /**
     * 操作人
     */
	private String operator;

}
