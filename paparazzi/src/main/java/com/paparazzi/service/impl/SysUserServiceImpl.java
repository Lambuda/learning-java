package com.paparazzi.service.impl;

import com.paparazzi.dao.ArticleMapper;
import com.paparazzi.dao.SysUserMapper;
import com.paparazzi.domain.SysUser;
import com.paparazzi.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Resource
	private SysUserMapper sysUserMapper;
	@Resource
	private ArticleMapper articleMapper;

	@Transactional
	@Override
	public void createSysUser(SysUser sysUser) {
		sysUserMapper.insert(sysUser);
	}

	@Override
	public SysUser queryUserByName(String username) {
		return sysUserMapper.queryUser(username);
	}

	@Override
	public Integer queryCount(String username) {
		return sysUserMapper.queryCountUser(username);
	}

}
