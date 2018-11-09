package com.sweet.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sweet.api.commons.SuperEntity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;

/**
 * <p>
 * 小程序用户信息表
 * </p>
 *
 * @author wangsai
 * @since 2018-11-09
 */
@Data
@TableName("tbl_user_info")
public class UserInfo extends SuperEntity<UserInfo> {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;
    @TableField("user_name")
    private String userName;
    @TableField("avatar_url")
    private String avatarUrl;
    @TableField("open_id")
    private String openId;
    @TableField("union_id")
    private String unionId;
    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;
    /**
     * 0未绑定 1绑定
     */
    @TableField("bind_status")
    private Integer bindStatus;
    @TableField("deleted")
    private Integer deleted;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
