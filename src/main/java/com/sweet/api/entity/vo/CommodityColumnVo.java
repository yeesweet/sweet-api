package com.sweet.api.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhang.hp
 * @date 2018/10/22.
 */
@Data
public class CommodityColumnVo implements Serializable{

    /**
     * 购物车商品唯一标识
     */
    private String id;
    /**
     * 加入购物车日期
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date addDate;
    /**
     * 加入购物车价格
     */
    private double addCartPrice;
    /**
     * 是否选中
     */
    private Integer buyStatus;
    /**
     * 商品数量
     */
    private int num;
    /**
     * 商品编号
     */
    private String commodityNo;
    /**
     * 商品名称
     */
    private String commodityName;
    /**
     * 商品卖点
     */
    private String sellPoint;
    /**
     * 商品默认展示图
     */
    private String defaultPic;
    /**
     * 商品描述
     */
    private String commodityDesc;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 货号
     */
    private String itemNo;
    /**
     * 销售价
     */
    private Double salePrice;
    /**
     * 市场价
     */
    private Double marketPrice;
    /**
     * 成本价
     */
    private Double costPrice;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 属性编码
     */
    private String propNo;
    /**
     * 属性名称
     */
    private String propName;
    /**
     * 1下架(停售),2上架(在售)，3停用,(启用重新变成1),4预售,5待进货,6待售 7 售罄
     */
    private Integer status;
    /**
     * 上架时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date showDate;
    /**
     * 下架时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date downDate;
    /**
     * 销量
     */
    private Integer salesQuantity;
}
