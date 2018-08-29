package com.sweet.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sweet.api.commons.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.Date;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author zhang.hp
 * @since 2018-08-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class User extends SuperEntity<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 登陆名
     */
    @TableField("login_name")
    private String loginName;
    /**
     * 用户名
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 密码加密盐
     */
    private String salt;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 用户类别
     */
    @TableField("user_type")
    private Integer userType;
    /**
     * 用户状态
     */
    private Integer status;
    /**
     * 所属机构
     */
    @TableField("organization_id")
    private Integer organizationId;
    /**da
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;


}
