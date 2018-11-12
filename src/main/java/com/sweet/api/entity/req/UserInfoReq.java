package com.sweet.api.entity.req;

import lombok.Data;

/**
 * <p>
 * 小程序用户信息
 * </p>
 *
 * @author wangsai
 * @since 2018-11-09
 */
@Data
public class UserInfoReq {

    private String userName;
    private String avatarUrl;
}
