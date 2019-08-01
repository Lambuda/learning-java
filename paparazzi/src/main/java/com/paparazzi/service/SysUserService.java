package com.paparazzi.service;

import com.paparazzi.domain.SysUser;

public interface SysUserService {

	void createSysUser(SysUser sysUser);


	// 登录名 唯一
	SysUser queryUserByName(String username);


	Integer queryCount(String username);

}
