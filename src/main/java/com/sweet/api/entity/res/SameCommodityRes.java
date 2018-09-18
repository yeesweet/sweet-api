package com.sweet.api.entity.res;

import lombok.Data;
import lombok.ToString;

/**
 * @author zhang.hp
 * @date 2018/9/18.
 */
@Data
@ToString
public class SameCommodityRes {
    private String commodityNo;
    private String propNo;
    private String propName;
    private boolean isDefault;
}
