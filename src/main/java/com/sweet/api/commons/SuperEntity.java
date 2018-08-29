package com.sweet.api.commons;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * 演示实体父类
 */
@Data
public class SuperEntity<T extends Model> extends Model<T> {

    /**
     * 主键ID , 这里故意演示注解可以无
     */
    @TableId("id")
    private Long id;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
