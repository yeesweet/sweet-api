package com.sweet.api.mapper;

import com.sweet.api.entity.ShoppingCart;
import com.sweet.api.commons.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 购物车表 Mapper 接口
 * </p>
 *
 * @author zhang.hp
 * @since 2018-10-15
 */
public interface ShoppingcartMapper extends SuperMapper<ShoppingCart> {

    Integer getShoppingCartProductNum(@Param("loginId") String loginId);

    Integer updateBatch(@Param("carts") List<ShoppingCart> carts, @Param("loginId") String loginId);
}

