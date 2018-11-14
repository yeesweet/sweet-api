package com.sweet.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 订单详情
 * </p>
 *
 * @author zhang.hp
 * @since 2018-11-12
 */
@Data
@TableName("tbl_order_detail")
public class OrderDetail extends Model<OrderDetail> {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;
    /**
     * 货品总数量
     */
    @TableField("product_total_num")
    private Integer productTotalNum;
    /**
     * 货品总金额
     */
    @TableField("product_total_amt")
    private Double productTotalAmt;
    /**
     * 优惠总金额
     */
    @TableField("product_promotion_total_amt")
    private Double productPromotionTotalAmt;
    /**
     * 商品编码
     */
    @TableField("commodity_no")
    private String commodityNo;
    /**
     * 商品价格
     */
    @TableField("sale_price")
    private Double salePrice;
    /**
     * 市场价
     */
    @TableField("mark_price")
    private Double markPrice;
    /**
     * 商品名称
     */
    @TableField("commodity_name")
    private String commodityName;
    /**
     * 商品图片
     */
    @TableField("commodity_pic")
    private String commodityPic;
    /**
     * 品牌名称
     */
    @TableField("brand_name")
    private String brandName;
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
     * 货号
     */
    @TableField("item_no")
    private String itemNo;
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
     * 是否有效  1 是 0 否
     */
    @TableField("delete_flag")
    private Integer deleteFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
