package com.sweet.api.entity.res;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

/**
 * 页面管理模块
 * @author wang.s2
 *
 */
@Data
public class CmsModuleResp{

	//模块id
	private Long id;
	//模块名称
	private String moduleName;
	//模块样式
	private Integer moduleStyle;
	//模块类型(1：普通模块 2：推荐功能模块3：轮播图4：优购快报8：推荐功能模块二)
	private Integer moduleType;
	//排序编号
	private Integer sortNo;
	//模块间距
	private Integer space;
	//模块左右间距
	private Integer spaceLr;
	//模块宽度
	private Integer width;
	//模块高度
	private Integer height;
	//频道id
	private Long pageManagerId;
	//模块子表集合
	private List<CmsModuleDetailsResp> moduleDetails;

	/**
	 * 行
	 */
	private Integer rows = 0;
	/**
	 * 列
	 */
	private Integer cols = 0;

	/**
	 * 页数
	 */
	@TableField(value = "moduleDetailPages",exist = false)
	private Integer moduleDetailPages=0;

	/**
	 * 背景类型(1:背景图片;2:背景颜色)
	 */
	@TableField("type_bg")
	private Integer typeBg;

	/**
	 * 背景图片链接或者颜色值
	 */
	private String bg;

	public Integer getModuleDetailPages() {
		if(moduleStyle == 108){
			if(moduleDetails!=null && moduleDetails.size()>0){
				if(rows == 0 || cols == 0){
					moduleDetailPages=0;
				}else{
					int count=moduleDetails.size();
					int mod=count%(rows*cols);
					if(mod==0){
						moduleDetailPages=count/(rows*cols);
					}else{
						moduleDetailPages=count/(rows*cols)+1;
					}
				}
			}
		}
		return moduleDetailPages;
	}

	public void setModuleDetailPages(Integer moduleDetailPages) {
		this.moduleDetailPages = moduleDetailPages;
	}
}
