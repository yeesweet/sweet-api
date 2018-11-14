package com.sweet.api.test.order;

import com.sweet.api.Application;
import com.sweet.api.commons.PageFinder;
import com.sweet.api.entity.Order;
import com.sweet.api.service.IOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author zhang.hp
 * @date 2018/9/18.
 */
@RunWith(SpringRunner.class)
@EnableConfigurationProperties
@SpringBootTest(classes = Application.class)
public class OrderTest {

    @Autowired
    private IOrderService orderService;

    @Test
    public void getMyOrdersTest() {
        Integer type = 0;
        String userId = "4905fa2246454afcab6c47956b133a68";
        final PageFinder<Order> myOrders = orderService.getMyOrders(userId, type, 2, 1);
        final List<Order> data = myOrders.getData();
        Assert.notNull(myOrders, "测试通过");
    }
    @Test
    public void getOrderDetailTest() {
        String orderNo = "L123457";
        String userId = "4905fa2246454afcab6c47956b133a68";
        final Order order = orderService.getOrderDetail(userId, orderNo);
        Assert.notNull(order, "测试通过");
    }

}
