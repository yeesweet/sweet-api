package com.sweet.api.service;

import com.sweet.api.entity.bean.UserAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IUserAddressService {

    /**
     * 查询地址管理列表
     * @param userId
     * @return
     */
    public List<UserAddress> selectUserAddressList(String userId);

    /**
     * 根据id查询地址
     * @param id
     * @return
     */
    public UserAddress selectById(String id);

    /**
     * 查询默认地址
     * @return
     */
    public UserAddress selectDefaultAddress(String userId);

    /**
     * 根据id和userId修改地址
     * @param id
     * @param userId
     */
    public void updateAddressByIdAndUserId(String id,String userId);

    public void insert(UserAddress userAddress);

    public void updateById(UserAddress userAddress);

    /**
     * 修改地址默认地址状态u
     * @param defaultAddress
     * @param userId
     */
    public void updateDefaultAddressByUserId(Integer defaultAddress,String userId);

}