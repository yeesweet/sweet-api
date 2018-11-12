package com.sweet.api.entity;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 专题商品表
 * </p>
 *
 * @author wang.s2
 * @since 2018-09-07
 */
@Data
public class TopicCommodity{

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
