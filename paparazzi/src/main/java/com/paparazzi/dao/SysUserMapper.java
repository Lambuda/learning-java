package com.paparazzi.dao;

import com.paparazzi.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface SysUserMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(SysUser record);

	int insertSelective(SysUser record);

	SysUser selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(SysUser record);

	int updateByPrimaryKey(SysUser record);

	//根据用户名 查询 用户
	SysUser queryUser(String username);

	//查询用户名是否存在
	Integer queryCountUser(String username);
}