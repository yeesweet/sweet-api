package com.sweet.api.entity.bean;

import lombok.Data;

/**
 * 地址表
 */
@Data
public class SystemArea {
    private String id;

    private String name;

    private String no;

    private String isleaf;

    private Integer child;

    private Integer level;

    private Integer sort;

    private String post;
    //区号
    private String code;

    private Long timestamp;
    //是否生效(1 生效 0 不生效)
    private Short isUsable;
    //是否货到付款:1-支持;0-不支持
    private Short isCashOnDelivy;
    //可配送区
    private String butTheServiceArea;
    //不可配送区
    private String notServiceArea;
    //无线平台是否支持货到付款:1-支持;0-不支持
    private Short isCashOnDelivyMobile;
    //是否支持电子发票(0:否；1：是)
    private Short isSupportInvoice;
}