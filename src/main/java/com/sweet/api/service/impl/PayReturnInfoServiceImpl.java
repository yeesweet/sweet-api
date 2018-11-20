package com.sweet.api.service.impl;

import com.sweet.api.entity.PayReturnInfo;
import com.sweet.api.mapper.PayReturnInfoMapper;
import com.sweet.api.service.IPayReturnInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付回调结果表 服务实现类
 * </p>
 *
 * @author zhang.hp
 * @since 2018-11-20
 */
@Service
public class PayReturnInfoServiceImpl extends ServiceImpl<PayReturnInfoMapper, PayReturnInfo> implements IPayReturnInfoService {

}
