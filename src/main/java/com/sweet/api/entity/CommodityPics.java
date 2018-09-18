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
 * 商品图片表
 * </p>
 *
 * @author zhang.hp
 * @since 2018-08-12
 */
@Data
@ToString
@TableName("tbl_cms_commodity_pics")
public class CommodityPics extends Model<CommodityPics> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 商品编号
     */
	@TableField("commodity_no")
	private String commodityNo;
    /**
     * 图片类型  1.大图  2.详情图
     */
	private Integer type;
    /**
     * 图片
     */
	private String image;
    /**
     * 序号
     */
	@TableField("sort_no")
	private Integer sortNo;
    /**
     * 是否有效 1 有效 0 无效
     */
	private Integer status;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 更新时间
     */
	@TableField("update_time")
	private Date updateTime;
    /**
     * 操作人
     */
	private String operator;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
