package com.sweet.api.commons;

/**
 * @author zhang.hp
 * @date 2018/10/11.
 */
public class CommodityCacheKey {

    /**
     * 获取规格缓存key
     * @param itemNo
     * @return
     */
    public static String getItemNoCacheKey(String itemNo) {
        StringBuilder sb=new StringBuilder("commodity:");
        sb.append("itemNo:");
        sb.append(itemNo);
        return sb.toString();
    }
}
