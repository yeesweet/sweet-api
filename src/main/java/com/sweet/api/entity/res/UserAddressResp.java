package com.sweet.api.entity.res;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 收货地址
 */
@Data
public class UserAddressResp {
    private String id;
    //用户id
    private String userId;
    //收货人姓名
    private String name;
    //收货人电话号码
    private String phone;
    //收货地址省名称
    private String provinceName;
    //收货地址省编号
    private String province;
    //收货地址城市名称
    private String cityName;
    //收货地址城市编号
    private String city;
    //收货地址区名称
    private String districtName;
    //收货地址区编号
    private String district;
    //收货详细地址
    private String address;
    //0 非默认地址 1 默认地址
    private Integer defaultAddress;
    //邮政编码
    private String zipCode;

    private List<SystemAreaResp> provinceList;
    private List<SystemAreaResp> cityList;
    private List<SystemAreaResp> districtList;

}