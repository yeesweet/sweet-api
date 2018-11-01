package com.sweet.api.mapper;

import com.sweet.api.entity.bean.UserAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserAddressMapper {

    public List<UserAddress> selectUserAddressList(UserAddress userAddress);

    public UserAddress selectById(@Param("id") String id);

    public void deleteById(@Param("id") String id);

    public void insert(UserAddress userAddress);

    public void updateById(UserAddress userAddress);

}