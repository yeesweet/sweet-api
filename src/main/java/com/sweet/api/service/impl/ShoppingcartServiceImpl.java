package com.sweet.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sweet.api.commons.Md5Encrypt;
import com.sweet.api.constants.ShoppingCartConstant;
import com.sweet.api.entity.Commodity;
import com.sweet.api.entity.ShoppingCart;
import com.sweet.api.entity.ShoppingCartBaseInfo;
import com.sweet.api.entity.vo.CommodityColumnVo;
import com.sweet.api.entity.vo.CommodityVo;
import com.sweet.api.entity.vo.ShoppingCartResultVo;
import com.sweet.api.entity.vo.ShoppingCartVo;
import com.sweet.api.mapper.ShoppingcartMapper;
import com.sweet.api.service.ICommodityService;
import com.sweet.api.service.IShoppingcartService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author zhang.hp
 * @since 2018-10-15
 */
@Service
public class ShoppingcartServiceImpl extends ServiceImpl<ShoppingcartMapper, ShoppingCart> implements IShoppingcartService {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingcartServiceImpl.class);

    @Autowired
    private ICommodityService commodityService;

    @Autowired
    private ShoppingcartMapper shoppingcartMapper;

    @Override
    public ShoppingCartResultVo addProductToCart(String commodityNo, int count, ShoppingCartBaseInfo info) {
        ShoppingCartResultVo resultVo = new ShoppingCartResultVo();
        if (StringUtils.isBlank(commodityNo)) {
            resultVo.setSuccessFlag(false);
            resultVo.setResultMsg("commodityNo为空");
            return resultVo;
        }
        if (count <= 0) {
            resultVo.setSuccessFlag(false);
            resultVo.setResultMsg("count小于等于0");
            return resultVo;
        }
        if (info == null) {
            resultVo.setSuccessFlag(false);
            resultVo.setResultMsg("loginId为空");
            return resultVo;
        }
        final String loginId = info.getLoginId();
        if (StringUtils.isBlank(loginId)) {
            resultVo.setSuccessFlag(false);
            resultVo.setResultMsg("loginId为空");
            return resultVo;
        }
        try {
            //校验商品
            Commodity commodity = commodityService.getCommodityByNo(commodityNo);
            if (null == commodity) {
                resultVo.setSuccessFlag(false);
                resultVo.setResultMsg(commodityNo + "不存在");
                return resultVo;
            }
            final Integer linkBuy = info.getLinkBuy();
            //立即购买先清空临时购物车
            if (ShoppingCartConstant.IS_LINK_BUY == linkBuy) {
                clearShoppingCart(info);
            }
            //购物车数量校验
            Integer num = getShoppingCartProductNum(info);
            if (ShoppingCartConstant.IS_LINK_BUY == linkBuy) {
                num = 0;
            }
            final int maxShoppingCartNum = ShoppingCartConstant.MAX_SHOPPING_CART_NUM;
            if (num + count > maxShoppingCartNum) {
                resultVo.setSuccessFlag(false);
                resultVo.setResultMsg("购物车数量太多，请删除后再操作");
                return resultVo;
            }
            //判断是否存在
            final String itemMd5 = Md5Encrypt.md5(loginId + "@" + linkBuy + "@" + commodityNo);
            ShoppingCart shoppingCart = selectOne(new QueryWrapper<ShoppingCart>().eq("item_md5", itemMd5));
            //加车或者更新购物车
            if (shoppingCart == null) {
                shoppingCart = new ShoppingCart();
                shoppingCart.setLoginId(loginId);
                shoppingCart.setLinkBuy(linkBuy);
                shoppingCart.setSpMd5(Md5Encrypt.md5(loginId + "@" + linkBuy));
                shoppingCart.setItemMd5(Md5Encrypt.md5(loginId + "@" + linkBuy + "@" + commodityNo));
                shoppingCart.setCommodityNo(commodityNo);
                shoppingCart.setNum(count);
                shoppingCart.setIsChecked(1);
                shoppingCart.setAddPrice(commodity.getSalePrice());
                shoppingCart.setAddTime(new Date());
                shoppingCart.setDelFlag(1);
                insert(shoppingCart);
                resultVo.setSuccessFlag(true);
                resultVo.setResultMsg("添加购物车成功");
            } else {
                final Integer delFlag = shoppingCart.getDelFlag();
                if (delFlag == 1) {
                    Integer exitNum = shoppingCart.getNum();
                    shoppingCart.setNum(exitNum + count);
                } else {
                    shoppingCart.setNum(count);
                    shoppingCart.setAddTime(new Date());
                    shoppingCart.setDelFlag(1);
                }
                shoppingCart.setIsChecked(1);
                updateById(shoppingCart);
                resultVo.setSuccessFlag(true);
                resultVo.setResultMsg("添加购物车成功");
            }
        } catch (Exception e) {
            resultVo.setSuccessFlag(false);
            resultVo.setResultMsg("添加购物车失败");
            logger.error(commodityNo + "添加购物车失败:", e);
        }
        return resultVo;
    }

    @Override
    public ShoppingCartResultVo addProductToCart(Map<String, Integer> productMap, ShoppingCartBaseInfo info) {
        ShoppingCartResultVo resultVo = new ShoppingCartResultVo();
        if (productMap == null) {
            resultVo.setSuccessFlag(false);
            resultVo.setResultMsg("商品信息为空");
            return resultVo;
        }
        if (productMap.size() <= 0) {
            resultVo.setSuccessFlag(false);
            resultVo.setResultMsg("商品信息为空");
            return resultVo;
        }
        Set<String> set = productMap.keySet();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String commodityNo = iterator.next();
            Integer count = productMap.get(commodityNo);
            resultVo = this.addProductToCart(commodityNo, count, info);
        }
        return resultVo;
    }

    @Override
    public ShoppingCartResultVo removeProductFromCart(String rowId, ShoppingCartBaseInfo info) {
        return removeProductFromCart(new String[]{rowId}, info);
    }

    @Override
    public ShoppingCartResultVo removeProductFromCart(String[] rowIds, ShoppingCartBaseInfo info) {
        ShoppingCartResultVo resultVo = new ShoppingCartResultVo();
        if (info == null) {
            resultVo.setSuccessFlag(false);
            resultVo.setResultMsg("loginId为空");
            return resultVo;
        }
        final String loginId = info.getLoginId();
        if (StringUtils.isBlank(loginId)) {
            resultVo.setSuccessFlag(false);
            resultVo.setResultMsg("loginId为空");
            return resultVo;
        }
        if (null == rowIds) {
            resultVo.setSuccessFlag(false);
            resultVo.setResultMsg("rowId为空");
            return resultVo;
        }
        if (rowIds.length <= 0) {
            resultVo.setSuccessFlag(false);
            resultVo.setResultMsg("rowId为空");
            return resultVo;
        }
        //逻辑删除
        List<ShoppingCart> carts = new ArrayList<>(rowIds.length);
        for (String id : rowIds) {
            ShoppingCart cart = new ShoppingCart();
            cart.setId(id);
            cart.setDelFlag(0);
            cart.setIsChecked(0);
            carts.add(cart);
        }
        boolean success = updateBatch(carts, loginId);
        if (success) {
            resultVo.setSuccessFlag(true);
            resultVo.setResultMsg("刪除成功");
        } else {
            resultVo.setSuccessFlag(false);
            resultVo.setResultMsg("刪除失败");
        }
        return resultVo;
    }

    @Override
    public ShoppingCartResultVo updateProductFromCart(String rowId, int count, ShoppingCartBaseInfo info) {
        Map<String, Integer> productMap = new HashMap<>();
        productMap.put(rowId, count);
        return this.updateProductFromCart(productMap, info);
    }

    @Override
    public ShoppingCartResultVo updateProductFromCart(Map<String, Integer> productMap, ShoppingCartBaseInfo info) {
        ShoppingCartResultVo resultVo = new ShoppingCartResultVo();
        if (null == productMap) {
            resultVo.setSuccessFlag(false);
            resultVo.setResultMsg("rowId为空");
            return resultVo;
        }
        if (productMap.size() <= 0) {
            resultVo.setSuccessFlag(false);
            resultVo.setResultMsg("rowId为空");
            return resultVo;
        }
        if (info == null) {
            resultVo.setSuccessFlag(false);
            resultVo.setResultMsg("loginId为空");
            return resultVo;
        }
        final String loginId = info.getLoginId();
        if (StringUtils.isBlank(loginId)) {
            resultVo.setSuccessFlag(false);
            resultVo.setResultMsg("loginId为空");
            return resultVo;
        }
        List<ShoppingCart> carts = new ArrayList<>();
        Set<String> set = productMap.keySet();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String rowId = iterator.next();
            Integer count = productMap.get(rowId);
            ShoppingCart cart = new ShoppingCart();
            cart.setId(rowId);
            cart.setNum(count);
            carts.add(cart);
        }
        boolean success = updateBatch(carts, loginId);
        if (success) {
            resultVo.setSuccessFlag(true);
            resultVo.setResultMsg("修改成功");
        } else {
            resultVo.setSuccessFlag(false);
            resultVo.setResultMsg("修改失败");
        }
        return resultVo;
    }

    @Override
    public void clearShoppingCart(ShoppingCartBaseInfo info) {
        Assert.notNull(info, "loginId为空");
        Assert.hasText(info.getLoginId(), "loginId为空");
        String spMd5 = info.getLoginId() + "@" + info.getLinkBuy();
        ShoppingCart cart = new ShoppingCart();
        cart.setDelFlag(0);
        cart.setIsChecked(0);
        update(cart, new UpdateWrapper<ShoppingCart>().eq("sp_md5", spMd5));
    }

    @Override
    public Integer getShoppingCartProductNum(ShoppingCartBaseInfo info) {
        Assert.notNull(info, "loginId为空");
        Assert.hasText(info.getLoginId(), "loginId为空");
        Integer num = shoppingcartMapper.getShoppingCartProductNum(info.getLoginId());
        if (null == num) {
            num = 0;
        }
        return num;
    }

    @Override
    public ShoppingCartResultVo modifyProductBuyStatus(Map<String, Integer> rowIdBuyStatus, ShoppingCartBaseInfo info) {
        ShoppingCartResultVo resultVo = new ShoppingCartResultVo();
        if (info == null) {
            resultVo.setSuccessFlag(false);
            resultVo.setResultMsg("loginId为空");
            return resultVo;
        }
        final String loginId = info.getLoginId();
        if (StringUtils.isBlank(loginId)) {
            resultVo.setSuccessFlag(false);
            resultVo.setResultMsg("loginId为空");
            return resultVo;
        }
        if (rowIdBuyStatus == null) {
            resultVo.setSuccessFlag(false);
            resultVo.setResultMsg("rowId为空");
            return resultVo;
        }
        if (rowIdBuyStatus.size() <= 0) {
            resultVo.setSuccessFlag(false);
            resultVo.setResultMsg("rowId为空");
            return resultVo;
        }
        List<ShoppingCart> carts = new ArrayList<>();
        Set<String> set = rowIdBuyStatus.keySet();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            final String rowId = iterator.next();
            final Integer status = rowIdBuyStatus.get(rowId);
            ShoppingCart cart = new ShoppingCart();
            cart.setId(rowId);
            cart.setIsChecked(status);
            carts.add(cart);
        }
        boolean success = updateBatch(carts, loginId);
        if (success) {
            resultVo.setSuccessFlag(true);
            resultVo.setResultMsg("修改成功");
        } else {
            resultVo.setSuccessFlag(false);
            resultVo.setResultMsg("修改失败");
        }
        return resultVo;
    }

    @Override
    public ShoppingCartVo checkAndCalculate(ShoppingCartBaseInfo info, boolean calculateFreight) {
        if (null == info) {
            return null;
        }
        final String loginId = info.getLoginId();
        if (StringUtils.isBlank(loginId)) {
            return null;
        }
        final Integer linkBuy = info.getLinkBuy();
        if (null == linkBuy) {
            return null;
        }
        //合并临时购物车和数据库购物车
        final String spMd5 = Md5Encrypt.md5(loginId + "@" + linkBuy);
        List<ShoppingCart> carts = null;
        if (ShoppingCartConstant.NOT_LINK_BUY == linkBuy) {
            carts = selectList(new QueryWrapper<ShoppingCart>().eq("login_id", loginId)
                    .eq("del_flag", 1)
                    .orderByDesc("add_time"));
        } else {
            carts = selectList(new QueryWrapper<ShoppingCart>().eq("sp_md5", spMd5)
                    .eq("del_flag", 1)
                    .orderByDesc("add_time"));
        }
        if (carts == null || carts.size() == 0) {
            return null;
        }
        //批量查询购物车商品
        List<String> nos = new ArrayList<>(carts.size());
        for (ShoppingCart cart : carts) {
            final String commodityNo = cart.getCommodityNo();
            nos.add(commodityNo);
        }
        final List<CommodityVo> commodityList = commodityService.getCommodityList(nos, false, false);
        //封装数据
        ShoppingCartVo shoppingCartVo = new ShoppingCartVo();
        shoppingCartVo.setLoginId(loginId);
        shoppingCartVo.setLinkBuy(linkBuy);
        Integer buyNum = 0;
        double totalAmount = 0;
        double preferentialAmount = 0;
        List<CommodityColumnVo> vos = new ArrayList<>();
        for (ShoppingCart cart : carts) {
            final String commodityNo = cart.getCommodityNo();
            final Integer num = cart.getNum();
            final Integer isChecked = cart.getIsChecked();
            if (ShoppingCartConstant.BUY_STATUS_CHECKED == isChecked) {
                buyNum += num;
            }
            for (CommodityVo commodity : commodityList) {
                final String no = commodity.getCommodityNo();
                if (StringUtils.equals(commodityNo, no)) {
                    CommodityColumnVo commodityColumnVo = new CommodityColumnVo();
                    commodityColumnVo.setId(cart.getId());
                    commodityColumnVo.setAddCartPrice(cart.getAddPrice());
                    commodityColumnVo.setAddDate(cart.getAddTime());
                    commodityColumnVo.setBuyStatus(isChecked);
                    commodityColumnVo.setCommodityNo(commodityNo);
                    commodityColumnVo.setCommodityName(commodity.getCommodityName());
                    commodityColumnVo.setSellPoint(commodity.getSellPoint());
                    commodityColumnVo.setDefaultPic(commodity.getDefaultPic());
                    commodityColumnVo.setCommodityDesc(commodity.getCommodityDesc());
                    commodityColumnVo.setBrandName(commodity.getBrandName());
                    commodityColumnVo.setItemNo(commodity.getItemNo());
                    commodityColumnVo.setSalePrice(commodity.getSalePrice());
                    commodityColumnVo.setMarketPrice(commodity.getMarketPrice());
                    commodityColumnVo.setCostPrice(commodity.getCostPrice());
                    commodityColumnVo.setStock(commodity.getStock());
                    commodityColumnVo.setPropNo(commodity.getPropNo());
                    commodityColumnVo.setNum(cart.getNum());
                    Integer status = commodity.getCommodityStatus();
                    if (commodity.getStock() <= 0) {
                        status = 7;
                    }
                    commodityColumnVo.setStatus(status);
                    commodityColumnVo.setShowDate(commodity.getShowDate());
                    commodityColumnVo.setDownDate(commodity.getDownDate());
                    commodityColumnVo.setSalesQuantity(commodity.getSalesQuantity());
                    vos.add(commodityColumnVo);
                    //计算金额
                    if (ShoppingCartConstant.BUY_STATUS_CHECKED == isChecked) {
                        totalAmount += commodity.getSalePrice();
                        preferentialAmount += commodity.getMarketPrice() - commodity.getSalePrice();
                    }
                    break;
                }
            }
        }
        if (totalAmount > ShoppingCartConstant.POSTAGE_FREE_AMOUNT) {
            shoppingCartVo.setIsPostageFree(1);
        } else {
            shoppingCartVo.setIsPostageFree(0);
            shoppingCartVo.setRePurTofreightAmount(ShoppingCartConstant.POSTAGE_FREE_AMOUNT - totalAmount);
        }
        //计算运费
        if (calculateFreight) {
            if (totalAmount > ShoppingCartConstant.POSTAGE_FREE_AMOUNT) {
                preferentialAmount += ShoppingCartConstant.SHOPPING_CART_POSTAGE;
            } else {
                totalAmount += ShoppingCartConstant.SHOPPING_CART_POSTAGE;
            }
        }
        shoppingCartVo.setSaleAmount(totalAmount);
        shoppingCartVo.setPreferentialAmount(preferentialAmount);
        shoppingCartVo.setBuyAmount(totalAmount);
        shoppingCartVo.setOrderAmountLimit(ShoppingCartConstant.LEAST_ORDER_AMOUNT_LIMIT);
        if (totalAmount > ShoppingCartConstant.LEAST_ORDER_AMOUNT_LIMIT) {
            shoppingCartVo.setLackOrderAmount(0);
        } else {
            shoppingCartVo.setLackOrderAmount(ShoppingCartConstant.LEAST_ORDER_AMOUNT_LIMIT - totalAmount);
        }
        shoppingCartVo.setBuyNum(buyNum);
        shoppingCartVo.setCommodityColumnVoList(vos);
        return shoppingCartVo;
    }

    @Override
    public boolean updateBatch(List<ShoppingCart> carts, String loginId) {
        if (StringUtils.isBlank(loginId)) {
            return false;
        }
        if (null == carts) {
            return false;
        }
        if (carts.size() <= 0) {
            return false;
        }
        try {
            Integer success = shoppingcartMapper.updateBatch(carts, loginId);
            if (success > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error(loginId + "_批量修改购物车失败:", e);
            return false;
        }
    }
}
