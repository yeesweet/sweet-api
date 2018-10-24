package com.sweet.api.controller;


import com.sweet.api.commons.ResponseMessage;
import com.sweet.api.entity.ShoppingCartBaseInfo;
import com.sweet.api.entity.req.AddCartReq;
import com.sweet.api.entity.vo.ShoppingCartResultVo;
import com.sweet.api.entity.vo.ShoppingCartVo;
import com.sweet.api.service.IShoppingcartService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 购物车表 前端控制器
 * </p>
 *
 * @author zhang.hp
 * @since 2018-10-15
 */
@RestController
public class ShoppingcartController {
    @Autowired
    private IShoppingcartService shoppingcartService;

    @RequestMapping("carList")
    public Object carList(String loginId, Integer linkBuy) {
        if (null == linkBuy) {
            return new ResponseMessage<>(0, "linkBuy为空");
        }
        ShoppingCartBaseInfo info = new ShoppingCartBaseInfo();
        info.setLinkBuy(linkBuy);
        info.setLoginId(loginId);
        ShoppingCartVo shoppingCartVo = shoppingcartService.checkAndCalculate(info, false);
        return new ResponseMessage<>(1, "success", shoppingCartVo);
    }

    @PostMapping("addToCart")
    public Object addCommodityToCart(@RequestBody List<AddCartReq> cartReqs, String loginId, Integer linkBuy) {
        if (null == linkBuy) {
            return new ResponseMessage<>(0, "linkBuy为空");
        }
        if (null == cartReqs) {
            return new ResponseMessage<>(0, "商品为空");
        }
        if (cartReqs.size() <= 0) {
            return new ResponseMessage<>(0, "商品为空");
        }
        ShoppingCartBaseInfo info = new ShoppingCartBaseInfo();
        info.setLinkBuy(linkBuy);
        info.setLoginId(loginId);
        Map<String, Integer> map = new HashMap<>(cartReqs.size());
        for (AddCartReq req : cartReqs) {
            final String commodityNo = req.getCommodityNo();
            final Integer count = req.getCount();
            if (StringUtils.isNoneBlank(commodityNo) && count != null && count > 0) {
                map.put(commodityNo, count);
            }
        }
        ShoppingCartResultVo resultVo = shoppingcartService.addProductToCart(map, info);
        final boolean successFlag = resultVo.getSuccessFlag();
        final String resultMsg = resultVo.getResultMsg();
        int code = 0;
        if (successFlag) {
            code = 1;
        }
        return new ResponseMessage<>(code, resultMsg);
    }

    @PostMapping("removeFromCart")
    public Object removeFromCart(@RequestBody String[] rowIds, String loginId, Integer linkBuy){
        ShoppingCartBaseInfo info = new ShoppingCartBaseInfo();
        info.setLinkBuy(linkBuy);
        info.setLoginId(loginId);
        final ShoppingCartResultVo resultVo = shoppingcartService.removeProductFromCart(rowIds, info);
        final boolean successFlag = resultVo.getSuccessFlag();
        final String resultMsg = resultVo.getResultMsg();
        int code = 0;
        if (successFlag) {
            code = 1;
        }
        return new ResponseMessage<>(code, resultMsg);
    }
    @PostMapping("updateFromCart")
    public Object updateFromCart(@RequestBody List<AddCartReq> cartReqs, String loginId, Integer linkBuy) {
        if (null == linkBuy) {
            return new ResponseMessage<>(0, "linkBuy为空");
        }
        if (null == cartReqs) {
            return new ResponseMessage<>(0, "商品为空");
        }
        if (cartReqs.size() <= 0) {
            return new ResponseMessage<>(0, "商品为空");
        }
        ShoppingCartBaseInfo info = new ShoppingCartBaseInfo();
        info.setLinkBuy(linkBuy);
        info.setLoginId(loginId);
        Map<String, Integer> map = new HashMap<>(cartReqs.size());
        for (AddCartReq req : cartReqs) {
            final String rowId = req.getRowId();
            final Integer count = req.getCount();
            if (StringUtils.isNoneBlank(rowId) && count != null && count > 0) {
                map.put(rowId, count);
            }
        }
        ShoppingCartResultVo resultVo = shoppingcartService.updateProductFromCart(map, info);
        final boolean successFlag = resultVo.getSuccessFlag();
        final String resultMsg = resultVo.getResultMsg();
        int code = 0;
        if (successFlag) {
            code = 1;
        }
        return new ResponseMessage<>(code, resultMsg);
    }
    @PostMapping("modifyStatus")
    public Object modifyStatus(@RequestBody List<AddCartReq> cartReqs, String loginId, Integer linkBuy) {
        if (null == linkBuy) {
            return new ResponseMessage<>(0, "linkBuy为空");
        }
        if (null == cartReqs) {
            return new ResponseMessage<>(0, "商品为空");
        }
        if (cartReqs.size() <= 0) {
            return new ResponseMessage<>(0, "商品为空");
        }
        ShoppingCartBaseInfo info = new ShoppingCartBaseInfo();
        info.setLinkBuy(linkBuy);
        info.setLoginId(loginId);
        Map<String, Integer> map = new HashMap<>(cartReqs.size());
        for (AddCartReq req : cartReqs) {
            final String rowId = req.getRowId();
            final Integer status = req.getStatus();
            if (StringUtils.isNoneBlank(rowId) && status != null ) {
                map.put(rowId, status);
            }
        }
        ShoppingCartResultVo resultVo = shoppingcartService.modifyProductBuyStatus(map, info);
        final boolean successFlag = resultVo.getSuccessFlag();
        final String resultMsg = resultVo.getResultMsg();
        int code = 0;
        if (successFlag) {
            code = 1;
        }
        return new ResponseMessage<>(code, resultMsg);
    }
}

