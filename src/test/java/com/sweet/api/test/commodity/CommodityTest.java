package com.sweet.api.test.commodity;

import com.sweet.api.Application;
import com.sweet.api.entity.Commodity;
import com.sweet.api.entity.vo.CommodityVo;
import com.sweet.api.service.ICommodityService;
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
public class CommodityTest {

    @Autowired
    private ICommodityService commodityService;

    @Test
    public void getCommodityByNoTest(){
        String  commodityNo = "10002897";
        final Commodity commodity = commodityService.getCommodityByNo(commodityNo);
        Assert.notNull(commodity,"测试通过");
    }

    @Test
    public void getAllCommodityByItemNoTest(){
        String  itemNo = "1111";
        final List<CommodityVo> vos = commodityService.getAllCommodityByItemNo(itemNo);
        Assert.notEmpty(vos,"测试通过");
    }
}
