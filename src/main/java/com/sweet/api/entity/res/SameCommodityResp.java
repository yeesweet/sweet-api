package com.sweet.api.entity.res;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhang.hp
 * @date 2018/9/18.
 */
@Data
public class SameCommodityResp implements Serializable {
    private String commodityNo;
    private String propNo;
    private String propName;
    private boolean isDefault;
}
