package com.sweet.api.service;

import com.sweet.api.entity.bean.UserAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IUserAddressService {

    public List<UserAddress> selectUserAddressList(String userId);

    public UserAddress selectById(String id);

    public void updateAddressByIdAndUserId(String id,String userId);

    public void insert(UserAddress userAddress);

    public void updateById(UserAddress userAddress);

    public void updateDefaultAddressByUserId(Integer defaultAddress,String userId);

}