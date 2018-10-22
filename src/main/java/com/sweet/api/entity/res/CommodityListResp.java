package com.sweet.api.entity.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author wangsai
 * @date 2018/10/22.
 */
@Data
@ToString
public class CommodityListResp {

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
     * 销售价
     */
    private Double salePrice;
    /**
     * 市场价
     */
    private Double marketPrice;
}
