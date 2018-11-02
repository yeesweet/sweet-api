package com.sweet.api.service.impl;

import com.sweet.api.entity.bean.UserAddress;
import com.sweet.api.mapper.UserAddressMapper;
import com.sweet.api.service.IUserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class UserAddressServiceImpl implements IUserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddress> selectUserAddressList(String userId) {
        return userAddressMapper.selectUserAddressList(userId);
    }

    @Override
    public UserAddress selectById(String id) {
        return userAddressMapper.selectById(id);
    }

    @Override
    public void updateAddressByIdAndUserId(String id,String userId) {
        userAddressMapper.updateAddressByIdAndUserId(id,userId);
    }

    @Override
    public void insert(UserAddress userAddress) {
        userAddressMapper.insert(userAddress);
    }

    @Override
    public void updateById(UserAddress userAddress) {
        userAddressMapper.updateById(userAddress);
    }

    @Override
    public void updateDefaultAddressByUserId(Integer defaultAddress, String userId) {
        userAddressMapper.updateDefaultAddressByUserId(defaultAddress,userId);
    }
}
