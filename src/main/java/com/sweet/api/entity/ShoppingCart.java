package com.sweet.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 购物车表
 * </p>
 *
 * @author zhang.hp
 * @since 2018-10-15
 */
@Data
@TableName("tbl_shoppingcart")
public class ShoppingCart extends Model<ShoppingCart> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("id")
    private String id;
    /**
     * User login id
     */
    @TableField("login_id")
    private String loginId;
    /**
     * 立即购买  1：正常加入购物车，0：立即购买
     */
    @TableField("link_buy")
    private Integer linkBuy;
    /**
     * spMd5=md5(loginId+"@"+linkBuy)
     */
    @TableField("sp_md5")
    private String spMd5;
    /**
     * itemMd5=md5(loginId+"@"+linkBuy+"@"+commodityNo)
     */
    @TableField("item_md5")
    private String itemMd5;
    /**
     * 商品编码
     */
    @TableField("commodity_no")
    private String commodityNo;
    /**
     * 商品数量
     */
    @TableField("num")
    private Integer num;
    /**
     * 是否勾选：1勾选，0未勾选
     */
    @TableField("is_checked")
    private Integer isChecked;
    /**
     * 加车时商品价格
     */
    @TableField("add_price")
    private Double addPrice;
    /**
     * 添加时间
     */
    @TableField("add_time")
    private Date addTime;

    /**
     * 删除标识：1正常，0删除
     */
    @TableField("del_flag")
    private Integer delFlag;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
