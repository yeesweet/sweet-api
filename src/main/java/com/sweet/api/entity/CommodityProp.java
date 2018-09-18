package com.sweet.api.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商品属性表
 * </p>
 *
 * @author zhang.hp
 * @since 2018-08-06
 */
@Data
@ToString
@TableName("tbl_cms_commodity_prop")
public class CommodityProp extends Model<CommodityProp> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 属性编码
     */
	@TableField("prop_no")
	private String propNo;
    /**
     * 属性名称
     */
	@TableField("prop_name")
	private String propName;
    /**
     * 类型  1：规格
     */
	private Integer type;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 操作人
     */
	private String operator;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
