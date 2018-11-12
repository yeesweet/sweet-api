package com.sweet.api.entity;

import com.sweet.api.entity.vo.TopicCommodityVo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 专题表
 * </p>
 *
 * @author wang.s2
 * @since 2018-09-07
 */
@Data
public class Topic {

	private Long id;
    /**
     * 专题名称
     */
	private String name;
    /**
     * 标题
     */
	private String title;
    /**
     * 排序
     */
	private Integer sortNo;
    /**
     * 列表排序
     */
	private Integer listSort;
    /**
     * 专题开始时间
     */
	private Date startTime;
    /**
     * 专题结束时间
     */
	private Date endTime;
    /**
     * 是否启用  1 启用 0 停用
     */
	private Integer isDisplay;
    /**
     * 是否删除  1 删除  0 未删除
     */
	private Integer status;
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

	/**
	 * 有效期1，未开始 2,未过期3，已过期4，无有效期
	 *
	 * @return
	 */
	private Integer effectiveType;

	/**
	 * 商品数量
	 */
	private Integer commitityCount;

	//专题商品列表
	private List<TopicCommodityVo> commodityList;

}
