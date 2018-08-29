package com.sweet.api.service.impl;

import com.sweet.api.entity.User;
import com.sweet.api.mapper.UserMapper;
import com.sweet.api.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author zhang.hp
 * @since 2018-08-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
