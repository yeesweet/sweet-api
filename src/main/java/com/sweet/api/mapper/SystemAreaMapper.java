package com.sweet.api.mapper;

import com.sweet.api.entity.bean.SystemArea;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemAreaMapper {

    public List<SystemArea> getSystemAreaList(@Param("no") String no, @Param("level") int level);

    public String getSystemAreaNameByNo(String no);

    public List<SystemArea> getSystemAreaByNoList(List<String> noList);

}