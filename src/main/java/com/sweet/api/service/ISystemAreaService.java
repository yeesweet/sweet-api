package com.sweet.api.service;

import com.sweet.api.entity.bean.SystemArea;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ISystemAreaService {

    public List<SystemArea> getSystemAreaList(@Param("no") String no, @Param("level") int level);

    public String getSystemAreaNameByNo(String no);

    public Map<String,String> getNameMapByNoList(List<String> noList);

}