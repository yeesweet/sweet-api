package com.sweet.api.entity.res;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 商品分类表
 * </p>
 *
 * @author wang.s
 * @since 2018-08-06
 */
@Data
public class CategoryResp implements Serializable{

    private static final long serialVersionUID = 1L;

	private Long id;
    /**
     * 分类名称
     */
	private String name;
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

    private List<CategoryResp> childCategory;
}
