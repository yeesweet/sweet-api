package com.sweet.api.entity.req;

import lombok.Data;

import java.util.List;

/**
 * 商品列表查询条件参数
 * Created by wangsai on 2018/10/12.
 */
@Data
public class CommodityListReq {

    //商品名称
    private String commodityName;
    //商品编号列表
    private List<String> nos;
    //排序方式 1 销量  2 新品  3 价格升序 4 价格降序
    private Integer sortOrder;
    //列表类型 1 搜索列表 2 促销专题列表 3 点击二级分类
    private Integer type;
    //参数id
    private Long paramId;

}
