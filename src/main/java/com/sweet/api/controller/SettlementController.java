package com.sweet.api.controller;

import com.sweet.api.entity.res.SessionUserInfo;
import com.sweet.api.util.BeanUtils;
import com.sweet.api.commons.ResponseMessage;
import com.sweet.api.constants.ShoppingCartConstant;
import com.sweet.api.entity.ShoppingCartBaseInfo;
import com.sweet.api.entity.UserAddress;
import com.sweet.api.entity.req.SettlementParamReq;
import com.sweet.api.entity.res.SettlementResp;
import com.sweet.api.entity.vo.CommodityColumnVo;
import com.sweet.api.entity.vo.ShoppingCartResultVo;
import com.sweet.api.entity.vo.ShoppingCartVo;
import com.sweet.api.service.IShoppingcartService;
import com.sweet.api.service.IUserAddressService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 结算
 *
 * @author zhang.hp
 * @date 2018/10/25.
 */
@RestController
public class SettlementController {

    private static final Logger logger = LoggerFactory.getLogger(SettlementController.class);

    @Autowired
    private IShoppingcartService shoppingcartService;

    @Autowired
    private IUserAddressService userAddressService;

    @RequestMapping("checkout")
    public Object settlement(SettlementParamReq param, SessionUserInfo user) {

        //校验是否登录
        if (null == user) {
            return new ResponseMessage<>(1, "请您先登录");
        }

        final String userId = user.getUserId();
        if (StringUtils.isBlank(userId)) {
            return new ResponseMessage<>(1, "请您先登录");
        }

        final Integer linkBuy = param.getLinkBuy();
        if (null == linkBuy) {
            return new ResponseMessage<>(1, "linkBuy为空");
        }

        ShoppingCartBaseInfo info = new ShoppingCartBaseInfo();
        info.setLoginId(userId);
        info.setLinkBuy(linkBuy);
        try {
            /**
             * 立即购买逻辑
             * 1、先加车
             * 2、修改其它商品为uncheck状态
             */
            if (linkBuy == 0) {
                final String commodityNo = param.getCommodityNo();
                if (StringUtils.isBlank(commodityNo)) {
                    return new ResponseMessage<>(1, "立即购买商品编码为空");
                }
                final Integer count = param.getCount();
                if (count == null || count <= 0) {
                    return new ResponseMessage<>(1, "立即购买商为空");
                }
                ShoppingCartResultVo resultVo = shoppingcartService.addProductToCart(commodityNo, count, info);
                if (!resultVo.getSuccessFlag()) {
                    return new ResponseMessage<>(1, "立即购买,添加购物车失败");
                }
                //修改商品状态
                Map<String, Integer> rowIdBuyStatus = new HashMap<>();
                final ShoppingCartVo cartVo = shoppingcartService.checkAndCalculate(info, false);
                final List<CommodityColumnVo> columnVoList = cartVo.getCommodityColumnVoList();
                for (CommodityColumnVo columnVo : columnVoList) {
                    final String no = columnVo.getCommodityNo();
                    if (StringUtils.equalsIgnoreCase(no, commodityNo)) {
                        rowIdBuyStatus.put(columnVo.getId(), ShoppingCartConstant.BUY_STATUS_CHECKED);
                    } else {
                        rowIdBuyStatus.put(columnVo.getId(), ShoppingCartConstant.BUY_STATUS_UNCHECKED);
                    }
                }
                resultVo = shoppingcartService.modifyProductBuyStatus(rowIdBuyStatus, info);
                if (!resultVo.getSuccessFlag()) {
                    return new ResponseMessage<>(1, "立即购买,添加购商品状态失败");
                }
            }
            //正常购买结算
            ShoppingCartVo shoppingCartVo = shoppingcartService.checkAndCalculate(info, true);
            if (shoppingCartVo == null) {
                return new ResponseMessage<>(1, "购物车没有商品，去逛逛！");
            }
            SettlementResp resp = BeanUtils.copy(shoppingCartVo, SettlementResp.class);
            //筛选出购买的商品
            final List<CommodityColumnVo> commodityColumnVoList = shoppingCartVo.getCommodityColumnVoList();
            List<CommodityColumnVo> buyCommoidtis = new ArrayList<>();
            for (CommodityColumnVo vo : commodityColumnVoList) {
                final Integer buyStatus = vo.getBuyStatus();
                if (ShoppingCartConstant.BUY_STATUS_CHECKED == buyStatus) {
                    buyCommoidtis.add(vo);
                }
            }
            resp.setBuyCommodities(buyCommoidtis);
            //获取用户的默认收货地址
            UserAddress defaultAddress = userAddressService.selectDefaultAddress(userId);
            resp.setDefaultAddress(defaultAddress);
            return new ResponseMessage<>(0, "success", resp);
        } catch (Exception e) {
            logger.error("结算出错：",e);
            return new ResponseMessage<>(1, "结算失败");
        }
    }
}
