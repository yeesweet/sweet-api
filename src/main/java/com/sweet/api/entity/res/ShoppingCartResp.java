package com.sweet.api.entity.res;

import com.sweet.api.entity.vo.CommodityColumnVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhang.hp
 * @date 2018/10/25.
 */
@Data
public class ShoppingCartResp implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 购买总数量
     */
    private int buyNum;

    /**
     * 销售总金额(优惠前原金额)
     */
    private double saleAmount = 0;
    /**
     * 优惠总金额 = 市场价优惠+免运费
     */
    private double preferentialAmount = 0;
    /**
     * 购买总金额(应付，含运费)
     */
    private double buyAmount = 0;
    /**
     * 是否符合满额免运费活动 1 满足 0  不满足
     */
    private int isPostageFree;

    /**
     * 再购买**元商品，即可免运费
     */
    private double rePurTofreightAmount = 0;

    /**
     * 最小成单金额
     */
    private double orderAmountLimit;

    /**
     * 成单不足金额 = 最小成单金额- 商品金额
     */
    private double lackOrderAmount;

    /**
     * 购物车有效商品
     */
    private List<CommodityColumnVo> commodities;
    /**
     * 购物车失效商品
     */
    private List<CommodityColumnVo> abateCommodities;

    /**
     * 立即购买  1：正常加入购物车，0：立即购买
     */
    private Integer linkBuy;
}