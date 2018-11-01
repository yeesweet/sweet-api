package com.sweet.api.service;

import com.sweet.api.entity.bean.UserAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IUserAddressService {

    public List<UserAddress> selectUserAddressList(UserAddress userAddress);

    public UserAddress selectById(@Param("id") String id);

    public void deleteById(@Param("id") String id);

    public void insert(UserAddress userAddress);

    public void updateById(UserAddress userAddress);

}