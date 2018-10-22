package com.sweet.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sweet.api.commons.CommodityCacheKey;
import com.sweet.api.entity.Commodity;
import com.sweet.api.entity.CommodityPics;
import com.sweet.api.entity.req.CommodityListReq;
import com.sweet.api.entity.res.CommodityRes;
import com.sweet.api.entity.res.SameCommodityRes;
import com.sweet.api.entity.vo.CommodityVo;
import com.sweet.api.mapper.CommodityMapper;
import com.sweet.api.service.ICommodityService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author zhang.hp
 * @since 2018-09-17
 */
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements ICommodityService {

    private static final Logger logger = LoggerFactory.getLogger(CommodityServiceImpl.class);

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Commodity getCommodityByNo(String commodityNo) {
        Assert.hasText(commodityNo, "商品编号为空");
        return commodityMapper.getCommodityByNo(commodityNo);
    }

    @Override
    public List<CommodityVo> getAllCommodityByItemNo(String itemNo) {
        Assert.hasText(itemNo, "货号为空");
        List<CommodityVo> list = null;
        String key = CommodityCacheKey.getItemNoCacheKey(itemNo);
        boolean getDB = true;
        try {
            if (redisTemplate.hasKey(key)) {
                String json = redisTemplate.opsForValue().get(key);
                list = JSON.parseArray(json, CommodityVo.class);
                getDB = false;
            }
        } catch (Exception e) {
            logger.error("通过redis获取值出错，key:{}", key, e);
        }
        if (getDB || list == null) {
            list = commodityMapper.getAllCommodityByItemNo(itemNo);
            if (list != null && list.size() > 0) {
                for (CommodityVo vo : list) {
                    final List<CommodityPics> pics = vo.getPics();
                    List<String> mainPics = new ArrayList<>();
                    List<String> descPics = new ArrayList<>();
                    if (pics != null && pics.size() > 0) {
                        for (CommodityPics pic : pics) {
                            final Integer type = pic.getType();
                            //大图
                            if (1 == type) {
                                mainPics.add(pic.getImage());
                                //详情图
                            } else if (2 == type) {
                                descPics.add(pic.getImage());
                            }
                        }
                    }
                    vo.setMainPics(mainPics);
                    vo.setDescPics(descPics);
                }
            }
            redisTemplate.opsForValue().set(key, JSON.toJSONString(list), 1 * 60 * 60, TimeUnit.SECONDS);
        }
        return list;
    }

    @Override
    public CommodityRes getCommodityDetailJson(String commodityNo) {
        //获取商品基本信息
        final Commodity commodity = getCommodityByNo(commodityNo);
        if (commodity == null) {
            return null;
        }
        final String itemNo = commodity.getItemNo();
        //获取同一货号下的所有商品
        final List<CommodityVo> sameItemNoCommodityVos = getAllCommodityByItemNo(itemNo);
        //组装json格式数据
        CommodityRes res = new CommodityRes();
        res = convertCommodityToCommodityRes(commodity, res);
        //组装图片信息
        if (sameItemNoCommodityVos != null && sameItemNoCommodityVos.size() > 0) {
            List<SameCommodityRes> sames = new ArrayList<>(sameItemNoCommodityVos.size());
            for (CommodityVo vo : sameItemNoCommodityVos) {
                final String no = vo.getCommodityNo();
                SameCommodityRes same = new SameCommodityRes();
                boolean isDefault = false;
                if (StringUtils.equalsIgnoreCase(no, commodityNo)) {
                    res.setMainPics(vo.getMainPics());
                    res.setDescPics(vo.getDescPics());
                    isDefault = true;
                }
                same.setDefault(isDefault);
                same.setCommodityNo(no);
                same.setPropNo(vo.getProp().getPropNo());
                same.setPropName(vo.getProp().getPropName());
                sames.add(same);
            }
            res.setSameCommodity(sames);
        }
        return res;
    }

    private CommodityRes convertCommodityToCommodityRes(Commodity commodity, CommodityRes res) {
        Assert.notNull(commodity, "商品信息为空");
        res.setId(commodity.getId());
        res.setCommodityNo(commodity.getCommodityNo());
        res.setCommodityName(commodity.getCommodityName());
        res.setSellPoint(commodity.getSellPoint());
        res.setDefaultPic(commodity.getDefaultPic());
        res.setCommodityDesc(commodity.getCommodityDesc());
        res.setBrandName(commodity.getBrandName());
        res.setItemNo(commodity.getItemNo());
        res.setSalePrice(commodity.getSalePrice());
        res.setMarketPrice(commodity.getMarketPrice());
        res.setCostPrice(commodity.getCostPrice());
        res.setStock(commodity.getStock());
        res.setPropNo(commodity.getPropNo());
        Integer status = commodity.getCommodityStatus();
        if (commodity.getStock() <= 0) {
            status = 7;
        }
        res.setStatus(status);
        res.setShowDate(commodity.getShowDate());
        res.setDownDate(commodity.getDownDate());
        return res;
    }


    @Override
    public List<CommodityVo> getCommodityList(List<String> nos, boolean shelve, boolean instock) {
        if (nos == null) {
            return null;
        }
        if (nos.size() == 0) {
            return null;
        }
        return commodityMapper.getCommodityList(nos, shelve, instock);
    }

    @Override
    public List<CommodityVo> getCommoditySearchList(CommodityListReq commodityListReq) {
        return commodityMapper.getCommoditySearchList(commodityListReq);
    }
}
