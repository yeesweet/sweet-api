package com.sweet.api.entity.res;

import lombok.Data;

import java.util.List;

/**
 * 地址
 */
@Data
public class SystemAreaCityResp {
    //名称
    private String name;
    //编码
    private String no;
    private List<SystemAreaResp> areas;
}