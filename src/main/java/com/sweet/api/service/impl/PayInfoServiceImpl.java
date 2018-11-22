package com.sweet.api.service.impl;

import com.sweet.api.entity.PayInfo;
import com.sweet.api.mapper.PayInfoMapper;
import com.sweet.api.service.IPayInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付信息表 服务实现类
 * </p>
 *
 * @author zhang.hp
 * @since 2018-11-21
 */
@Service
public class PayInfoServiceImpl extends ServiceImpl<PayInfoMapper, PayInfo> implements IPayInfoService {

}
