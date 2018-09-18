package com.sweet.api.entity.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author zhang.hp
 * @date 2018/9/18.
 */
@Data
@ToString
public class CommodityRes {

    private Long id;
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
    @JsonIgnore
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
     * 状态  1下架(停售),2上架(在售)
     * 3 停用,(启用重新变成1),
     * 4预售,5待进货,6待售 7 售罄
     */
    private Integer status;
    /**
     * 主图
     */
    private List<String> mainPics;
    /**
     * 详情图
     */
    private List<String> descPics;
    /**
     * 同一货号下的商品
     */
    private List<SameCommodityRes> sameCommodity;

}
