package com.sweet.api.service.impl;

import com.sweet.api.entity.bean.SystemArea;
import com.sweet.api.entity.bean.UserAddress;
import com.sweet.api.mapper.SystemAreaMapper;
import com.sweet.api.mapper.UserAddressMapper;
import com.sweet.api.service.ISystemAreaService;
import com.sweet.api.service.IUserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class SystemAreaServiceImpl implements ISystemAreaService {

    @Autowired
    private SystemAreaMapper systemAreaMapper;

    @Override
    public List<SystemArea> getSystemAreaList(String no, int level) {
        return systemAreaMapper.getSystemAreaList(no, level);
    }

    @Override
    public String getSystemAreaNameByNo(String no) {
        return systemAreaMapper.getSystemAreaNameByNo(no);
    }
}
