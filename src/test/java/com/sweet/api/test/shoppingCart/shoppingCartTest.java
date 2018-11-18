package com.sweet.api.test.shoppingCart;

import com.sweet.api.Application;
import com.sweet.api.entity.ShoppingCart;
import com.sweet.api.entity.ShoppingCartBaseInfo;
import com.sweet.api.entity.vo.ShoppingCartResultVo;
import com.sweet.api.service.IShoppingcartService;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhang.hp
 * @date 2018/10/18.
 */
@RunWith(SpringRunner.class)
@EnableConfigurationProperties
@SpringBootTest(classes = Application.class)
public class shoppingCartTest {

    @Autowired
    private IShoppingcartService shoppingcartService;

    @Test
    public void addProductToCartTest() {
        String commodityNo = "10002897";
        int count = 4;
        ShoppingCartBaseInfo info = getShoppingCartBaseInfo();
        final ShoppingCartResultVo resultVo = shoppingcartService.addProductToCart(commodityNo, count, info);
        final Boolean successFlag = resultVo.getSuccessFlag();
        Assert.assertTrue(successFlag);
    }

    @Test
    public void addProductToCartBatchTest() {
        ShoppingCartBaseInfo info = getShoppingCartBaseInfo();
        Map<String, Integer> map = new HashMap<>();
        map.put("10002895", 2);
        map.put("10002896", 2);
        final ShoppingCartResultVo resultVo = shoppingcartService.addProductToCart(map, info);
        final Boolean successFlag = resultVo.getSuccessFlag();
        Assert.assertTrue(successFlag);
    }

    @Test
    public void removeProductFromCartTest() {
        String rowId = "041785ad82644961a8da66ed3fc9e6e9";
        ShoppingCartBaseInfo info = getShoppingCartBaseInfo();
        final ShoppingCartResultVo resultVo = shoppingcartService.removeProductFromCart(rowId, info);
        final Boolean successFlag = resultVo.getSuccessFlag();
        Assert.assertTrue(successFlag);
    }

    @Test
    public void removeProductFromCartBatchTest() {
        ShoppingCartBaseInfo info = getShoppingCartBaseInfo();
        String[] rowIds = {"041785ad82644961a8da66ed3fc9e6e9", "239eba0dcc1d4ddc940744d252a2b051"};
        final ShoppingCartResultVo resultVo = shoppingcartService.removeProductFromCart(rowIds, info);
        final Boolean successFlag = resultVo.getSuccessFlag();
        Assert.assertTrue(successFlag);
    }

    @Test
    public void updateProductFromCartTest() {
        String rowId = "041785ad82644961a8da66ed3fc9e6e9";
        ShoppingCartBaseInfo info = getShoppingCartBaseInfo();
        final ShoppingCartResultVo resultVo = shoppingcartService.updateProductFromCart(rowId, 3, info);
        final Boolean successFlag = resultVo.getSuccessFlag();
        Assert.assertTrue(successFlag);
    }
    @Test
    public void getShoppingCartProductNumTest(){
        final Integer num = shoppingcartService.getShoppingCartProductNum(getShoppingCartBaseInfo());
        Assert.assertSame(num,9);
    }
    @Test
    public void clearShoppingCartTest(){
        shoppingcartService.clearShoppingCart(getShoppingCartBaseInfo().getLoginId());
    }
    @Test
    public void updateBatchTest(){
        List<ShoppingCart> carts = new ArrayList<>();
        ShoppingCart cart = new ShoppingCart();
        cart.setId("041785ad82644961a8da66ed3fc9e6e9");
        cart.setNum(3);
        cart.setDelFlag(1);
        cart.setIsChecked(1);
        carts.add(cart);
        final boolean b = shoppingcartService.updateBatch(carts, getShoppingCartBaseInfo().getLoginId());
        Assert.assertTrue(b);
    }
    private ShoppingCartBaseInfo getShoppingCartBaseInfo() {
        ShoppingCartBaseInfo info = new ShoppingCartBaseInfo();
        info.setLoginId("11111");
        info.setLinkBuy(1);
        return info;
    }
}
