package com.sweet.api.mapper;

import com.sweet.api.entity.bean.UserAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserAddressMapper {

    public List<UserAddress> selectUserAddressList(String userId);

    public UserAddress selectById(@Param("id") String id);

    public UserAddress selectDefaultAddress(@Param("userId")String userId);

    public UserAddress selectLatestAddress(@Param("userId")String userId);

    public void updateAddressByIdAndUserId(@Param("id") String id,@Param("userId")String userId);
    public void insert(UserAddress userAddress);

    public void updateById(UserAddress userAddress);

    public void updateDefaultAddressByUserId(@Param("defaultAddress") Integer defaultAddress,@Param("userId")String userId);

}