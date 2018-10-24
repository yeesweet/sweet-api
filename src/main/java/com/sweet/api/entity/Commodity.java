package com.sweet.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author zhang.hp
 * @since 2018-09-17
 */
@Data
@TableName("tbl_cms_commodity")
public class Commodity extends Model<Commodity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 商品编号
     */
    @TableField("commodity_no")
    private String commodityNo;
    /**
     * 商品名称
     */
    @TableField("commodity_name")
    private String commodityName;
    /**
     * 商品卖点
     */
    @TableField("sell_point")
    private String sellPoint;
    /**
     * 商品默认展示图
     */
    @TableField("default_pic")
    private String defaultPic;
    /**
     * 商品描述
     */
    @TableField("commodity_desc")
    private String commodityDesc;
    /**
     * 品牌名称
     */
    @TableField("brand_name")
    private String brandName;
    /**
     * 货号
     */
    @TableField("item_no")
    private String itemNo;
    /**
     * 销售价
     */
    @TableField("sale_price")
    private Double salePrice;
    /**
     * 市场价
     */
    @TableField("market_price")
    private Double marketPrice;
    /**
     * 成本价
     */
    @TableField("cost_price")
    private Double costPrice;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 属性编码
     */
    @TableField("prop_no")
    private String propNo;
    /**
     * 1下架(停售),2上架(在售)，3停用,(启用重新变成1),4预售,5待进货,6待售
     */
    @TableField("commodity_status")
    private Integer commodityStatus;
    /**
     * 上架时间
     */
    @TableField("show_date")
    private Date showDate;
    /**
     * 下架时间
     */
    @TableField("down_date")
    private Date downDate;
    /**
     * 销量
     */
    @TableField("sales_quantity")
    private Integer salesQuantity;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;
    /**
     * 操作人
     */
    private String operator;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
