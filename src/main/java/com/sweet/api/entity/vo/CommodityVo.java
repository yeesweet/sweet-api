package com.sweet.api.entity.vo;

import com.sweet.api.entity.Commodity;
import com.sweet.api.entity.CommodityPics;
import com.sweet.api.entity.CommodityProp;
import lombok.Data;

import java.util.List;

/**
 * @author zhang.hp
 * @date 2018/9/17.
 */
@Data
public class CommodityVo extends Commodity {
    private List<CommodityPics> pics;
    private List<String> mainPics;
    private List<String> descPics;
    private CommodityProp prop;
}
