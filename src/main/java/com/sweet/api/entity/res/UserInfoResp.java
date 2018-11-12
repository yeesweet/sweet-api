package com.sweet.api.entity.res;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sweet.api.commons.SuperEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 小程序用户信息
 * </p>
 *
 * @author wangsai
 * @since 2018-11-09
 */
@Data
public class UserInfoResp{

    private String userId;
    private String userName;
    private String avatarUrl;
}
