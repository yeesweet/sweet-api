package com.sweet.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sweet.api.entity.ShoppingCart;
import com.sweet.api.entity.ShoppingCartBaseInfo;
import com.sweet.api.entity.res.ShoppingCartResp;
import com.sweet.api.entity.vo.ShoppingCartResultVo;
import com.sweet.api.entity.vo.ShoppingCartVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 购物车表 服务类
 * </p>
 *
 * @author zhang.hp
 * @since 2018-10-15a
 */
public interface IShoppingcartService extends IService<ShoppingCart> {

    /**
     * 添加购物车
     * @param commodityNo
     * @param count
     * @param info
     * @return
     */
    ShoppingCartResultVo addProductToCart(String commodityNo, int count, ShoppingCartBaseInfo info);

    /**
     * 批量添加购物车
     * @param productMap
     * @param info
     * @return
     */
    ShoppingCartResultVo addProductToCart(Map<String, Integer> productMap, ShoppingCartBaseInfo info);

    /**
     * 删除商品
     *
     * @param rowId
     * @param info
     */
    ShoppingCartResultVo removeProductFromCart(String rowId, ShoppingCartBaseInfo info);

    /**
     * 批量删除商品
     *
     * @param rowIds
     * @param info
     */
    ShoppingCartResultVo removeProductFromCart(String[] rowIds, ShoppingCartBaseInfo info);
    /**
     * 更新商品数量
     *
     * @param rowId
     * @param count
     * @param info
     * @return
     */
    ShoppingCartResultVo updateProductFromCart(String rowId, int count, ShoppingCartBaseInfo info);

    /**
     * 批量更新商品数量
     *
     * @param productMap
     * @param info
     * @return
     */
    ShoppingCartResultVo updateProductFromCart(Map<String, Integer> productMap, ShoppingCartBaseInfo info);

    /**
     * 清空购物车
     * @param loginId
     */
    void clearShoppingCart(String loginId);

    /**
     * 获取购物车数量
     * @param info
     * @return
     */
    Integer getShoppingCartProductNum(ShoppingCartBaseInfo info);

    /**
     * 更新购物车选中信息
     * ShoppingCartConstant.BUY_STATUS_CHECKED = 1 选中
     * ShoppingCartConstant.BUY_STATUS_UNCHECKED = 0 不选中
     *
     * @param rowIdBuyStatus       Map<rowId,buyStatus>
     * @param info
     * @return
     */
    ShoppingCartResultVo modifyProductBuyStatus(Map<String, Integer> rowIdBuyStatus, ShoppingCartBaseInfo info);

    /**
     * 检查和计算购物车
     * @param info
     * @param calculateFreight 是否计算运费
     * @return
     */
    ShoppingCartVo checkAndCalculate(ShoppingCartBaseInfo info, boolean calculateFreight);

    /**
     * 根据login，批量更新购物车
     * @param carts
     * @param loginId
     * @return
     */
    boolean updateBatch(List<ShoppingCart> carts,String loginId);

    /**
     * 获取购物车商品列表
     * @param info
     * @return
     */

    ShoppingCartResp getCartListResp(ShoppingCartBaseInfo info);

    /**
     * 清空已勾选购物车 提交订单后使用
     * @param logInId
     */
    void cleanCheckedShoppingcart(String logInId);
}
