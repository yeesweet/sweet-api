package com.sweet.api.constants;

/**
 * @author zhang.hp
 * @date 2018/10/17.
 */
public class ShoppingCartConstant {
    /**
     * 购物车最大商品数量
     */
    public final static int MAX_SHOPPING_CART_NUM = 100;
    /**
     * 0 表示立即购买(不加入购物车)
     */
    public static final int IS_LINK_BUY = 0;
    /**
     * 1 表示正常加入购物车
     */
    public static final int NOT_LINK_BUY = 1;
    /**
     * 1 表示选中状态
     */
    public static final int BUY_STATUS_CHECKED = 1;
    /**
     * 0 表示不选中状态
     */
    public static final int BUY_STATUS_UNCHECKED = 0;
    /**
     * 购物车缓存默认天数
     */
    public static int SHOPPING_CART_CACHE_DAYS_DEFAULT = 1;
    /**
     * 邮费
     */
    public static double SHOPPING_CART_POSTAGE = 15;
    /**
     * 满多少免邮费
     */
    public static double POSTAGE_FREE_AMOUNT = 79;
    /**
     * 最小成单金额
     */
    public static double LEAST_ORDER_AMOUNT_LIMIT = 1;
}
