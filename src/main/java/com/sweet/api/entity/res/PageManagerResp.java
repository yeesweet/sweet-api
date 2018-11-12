package com.sweet.api.entity.res;

import lombok.Data;

import java.util.List;

/**
 * <p>
 * 页面返回类
 * </p>
 *
 * @author wang.s
 * @since 2018-08-21
 */
@Data
public class PageManagerResp {

    /**
     * 页面id
     */
	private Long id;
    /**
     * 页面名称
     */
	private String name;
    /**
     * 顶部标题
     */
	private String topName;
    /**
     * 背景类型(1:背景图片;2:背景颜色)
     */
	private Integer typeBg;
    /**
     * 背景图片链接或者颜色值
     */
	private String bg;

    private List<CmsModuleResp> moduleList;

}
