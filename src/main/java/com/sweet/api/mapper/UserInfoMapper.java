package com.sweet.api.mapper;

import com.sweet.api.entity.UserInfo;
import com.sweet.api.commons.SuperMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 小程序用户信息表 Mapper 接口
 * </p>
 *
 * @author wangsai
 * @since 2018-11-09
 */
public interface UserInfoMapper extends SuperMapper<UserInfo> {

    /**
     * 根据openId unionId查询用户信息
     * @param openId
     * @param unionId
     * @return
     */
    public UserInfo selectOne(@Param("openId") String openId, @Param("unionId") String unionId);

}
