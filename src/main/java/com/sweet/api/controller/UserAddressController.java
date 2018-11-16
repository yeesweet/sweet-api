package com.sweet.api.controller;

import com.sweet.api.annotation.WechatAccess;
import com.sweet.api.commons.ResponseMessage;
import com.sweet.api.entity.SystemArea;
import com.sweet.api.entity.UserAddress;
import com.sweet.api.entity.req.UserAddressReq;
import com.sweet.api.entity.res.SessionUserInfo;
import com.sweet.api.entity.res.SystemAreaResp;
import com.sweet.api.entity.res.UserAddressResp;
import com.sweet.api.service.ISystemAreaService;
import com.sweet.api.service.IUserAddressService;
import com.sweet.api.util.StringUtil;
import com.sweet.api.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * 地址管理接口
 * @author wang.s
 */
@RestController
@RequestMapping("userAddress")
@Slf4j
public class UserAddressController {

	private static final Logger logger = LoggerFactory.getLogger(UserAddressController.class);

	@Autowired
	private IUserAddressService userAddressService;
	@Autowired
	private ISystemAreaService systemAreaService;

	/**
	 * 查询地址管理列表
	 *
	 * @param
	 * @return
	 */
	@RequestMapping("/getUserAddressList")
	@WechatAccess
	public ResponseMessage getUserAddressList(SessionUserInfo sessionUserInfo) {
		List<UserAddress> userAddressList = userAddressService.selectUserAddressList(sessionUserInfo.getUserId());
		List<UserAddressResp> userAddressRespList = new ArrayList<>();
		if (userAddressList != null && userAddressList.size() > 0) {
			for (int i = 0; i < userAddressList.size(); i++) {
				UserAddress userAddress = userAddressList.get(i);
				UserAddressResp userAddressResp = new UserAddressResp();
				BeanUtils.copyProperties(userAddress, userAddressResp);
				userAddressRespList.add(userAddressResp);
			}
		}
		return new ResponseMessage(userAddressRespList);
	}

	/**
	 * 获取省市区列表
	 *
	 * @param parentNo
	 * @param level
	 * @return
	 */
	@RequestMapping("/getArea")
	@WechatAccess
	public ResponseMessage getArea(String parentNo, Integer level) {
		if (level == null) {
			return new ResponseMessage(-1, "获取省市区级别不能为空");
		}
		List<SystemArea> systemAreaList = new ArrayList<>();
		if (StringUtils.isNotBlank(parentNo)) {
			systemAreaList = systemAreaService.getSystemAreaList(parentNo, level);
		}
		if (!StringUtils.isNotBlank(parentNo)) {
			systemAreaList = systemAreaService.getSystemAreaList("root", 1);
		}
		List<SystemAreaResp> systemAreaRespList = new ArrayList<>();
		if (systemAreaList != null && systemAreaList.size() > 0) {
			for (int i = 0; i < systemAreaList.size(); i++) {
				SystemArea systemArea = systemAreaList.get(i);
				SystemAreaResp systemAreaResp = new SystemAreaResp();
				BeanUtils.copyProperties(systemArea, systemAreaResp);
				systemAreaRespList.add(systemAreaResp);
			}
		}
		return new ResponseMessage(systemAreaRespList);
	}

	/**
	 * 添加收货地址
	 * @param vo
	 * @return
	 */
	@PostMapping(value = "saveAddress")
	@WechatAccess
	public ResponseMessage saveAddress(UserAddressReq vo,SessionUserInfo sessionUserInfo) {
		String userId = sessionUserInfo.getUserId();

		if (StringUtils.isNotBlank(vo.getName()) && (vo.getName().length() > 100)) {
			return new ResponseMessage(-1, "收货人姓名长度不能大于100.");
		}

		// 一个账户最多保存10个地址
		List<UserAddress> userAddressList = userAddressService.selectUserAddressList(userId);
		if (userAddressList != null && userAddressList.size() >= 10) {
			return new ResponseMessage(-1, "一个账户最多保存10个地址！");
		}
		try {
			if (StringUtils.isBlank(vo.getName())) {
				return new ResponseMessage(-1, "请输入收货人姓名3-25个字符");
			}
			if (StringUtils.isNotBlank(vo.getName())) {
				//收货人姓名
				String name = vo.getName();
				byte[] b = new byte[0];
				b = name.getBytes("gb2312");
				if (b.length < 3 || b.length > 25) {
					return new ResponseMessage(-1, "请输入收货人姓名3-25个字符");
				}
				if (ValidateUtil.getByteTotal(name) < 3 || ValidateUtil.getByteTotal(name) > 25) {
					return new ResponseMessage(-1, "请输入收货人姓名3-25个字符");
				}
				//判断收货人姓名是否全为数字和规定的字符
				boolean isNum = name.matches("[0-9,.'_－ 　 ．，＇＿－]*");
				if (isNum) {
					return new ResponseMessage(-1, "您输入的真实姓名有误，不能全是数字和特殊符号!");
				}
				//如果不是全为数字和规定的字符，那么判断是否有其他非法字符
				if (ValidateUtil.compare(name)) {
					return new ResponseMessage(-1, "您输入的真实姓名有误，不能全是数字和特殊符号!");
				}

			}
			if (null == vo.getProvince()) {
				return new ResponseMessage(-1, "请选择您所在省份");
			}
			if (null == vo.getProvinceName()) {
				return new ResponseMessage(-1, "请选择您所在省份名称");
			}
			if (null == vo.getCity()) {
				return new ResponseMessage(-1, "请选择您所在城市");
			}
			if (null == vo.getCityName()) {
				return new ResponseMessage(-1, "请选择您所在城市名称");
			}
			if (null == vo.getDistrict()) {
				return new ResponseMessage(-1, "请选择您所在区县");
			}
			if (null == vo.getDistrictName()) {
				return new ResponseMessage(-1, "请选择您所在区县名称");
			}
			if (StringUtils.isBlank(vo.getAddress())) {
				return new ResponseMessage(-1, "您输入收货人地址有误，5-120个字符");
			}
			if (StringUtils.isNotBlank(vo.getAddress())) {
				String address = vo.getAddress();
				byte[] b = address.getBytes("gb2312");
				if (b.length < 6 || b.length > 120) {
					return new ResponseMessage(-1, "您输入收货人地址有误，5-120个字符");
				}
				if (ValidateUtil.getByteTotal(address) < 6 || ValidateUtil.getByteTotal(address) > 120) {
					return new ResponseMessage(-1, "您输入收货人地址有误，5-120个字符");
				}
				//判断详细地址是否全为数字和规定的字符
				boolean isNum = address.matches("[0-9,.'_－ 　 ．，＇＿－]*");
				if (isNum) {
					return new ResponseMessage(-1, "您输入收货人地址有误，5-120个字符");
				}
				//如果不是全为数字和规定的字符，那么判断是否有其他非法字符
				if (ValidateUtil.compare(address)) {
					return new ResponseMessage(-1, "您输入收货人地址有误，5-120个字符");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null == vo.getPhone()) {
			return new ResponseMessage(-1, "请填写手机号");
		}
		UserAddress userAddress = new UserAddress();
		BeanUtils.copyProperties(vo,userAddress);
		userAddress.setUserId(userId);
		if(StringUtils.isNotBlank(vo.getId())){
			userAddressService.updateById(userAddress);
		}
		if(!StringUtils.isNotBlank(vo.getId())){
			userAddress.setDefaultAddress(0);
			userAddress.setId(StringUtil.getUUID());
			userAddressService.insert(userAddress);
		}
		return new ResponseMessage();
	}

	/**
	 * 进入编辑页
	 * @param id
	 * @return
	 */
	@RequestMapping("/toEditAddress")
	@WechatAccess
	public ResponseMessage toEditAddress(String id) {
		UserAddress userAddress = userAddressService.selectById(id);
		UserAddressResp userAddressResp = new UserAddressResp();
		if(userAddress != null){
			BeanUtils.copyProperties(userAddress,userAddressResp);
			userAddressResp.setProvinceList(setArea("root",1));
			userAddressResp.setCityList(setArea(userAddressResp.getProvince(),2));
			userAddressResp.setDistrictList(setArea(userAddressResp.getCity(),3));
		}
		return new ResponseMessage(userAddressResp);
	}

	/**
	 * 查询地址列表
	 * @param parentNo
	 * @param level
	 * @return
	 */
	public List<SystemAreaResp> setArea(String parentNo, Integer level){
		List<SystemArea> systemAreaList = systemAreaService.getSystemAreaList(parentNo, level);
		List<SystemAreaResp> systemAreaRespList = new ArrayList<>();
		if (systemAreaList != null && systemAreaList.size() > 0) {
			for (int i = 0; i < systemAreaList.size(); i++) {
				SystemArea systemArea = systemAreaList.get(i);
				SystemAreaResp systemAreaResp = new SystemAreaResp();
				BeanUtils.copyProperties(systemArea, systemAreaResp);
				systemAreaRespList.add(systemAreaResp);
			}
		}
		return systemAreaRespList;
	}

	/**
	 * 删除收货地址
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteAddress")
	@WechatAccess
	public ResponseMessage deleteAddress(String id,SessionUserInfo sessionUserInfo) {
		String userId=sessionUserInfo.getUserId();
		userAddressService.updateAddressByIdAndUserId(id,userId);
		return new ResponseMessage();
	}


	/**
	 * 修改默认地址
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateDefaultAddress")
	@WechatAccess
	public ResponseMessage updateDefaultAddress(String id,SessionUserInfo sessionUserInfo) {
		String userId=sessionUserInfo.getUserId();
		userAddressService.updateDefaultAddressByUserId(0,userId);
		UserAddress userAddress = new UserAddress();
		userAddress.setId(id);
		userAddress.setDefaultAddress(1);
		userAddressService.updateById(userAddress);
		return new ResponseMessage();
	}
}
