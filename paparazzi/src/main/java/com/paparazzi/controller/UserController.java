package com.paparazzi.controller;

import com.paparazzi.domain.Result;
import com.paparazzi.domain.SysUser;
import com.paparazzi.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;


@Api(description = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController {

	@Resource
	private SysUserService sysUserService;

	@ApiOperation(value = "新增用户", notes = "新增注册")
	@RequestMapping(value = "/createUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Result<String> createUser(@RequestBody SysUser sysUser) {
		Result<String> result = new Result<String>();
		try {
			if (sysUser == null) {
				result.setError("参数NULL");
				return result;
			}
			SysUser sysUser2 = sysUserService.queryUserByName(sysUser.getName());
			if (sysUser2 != null) {result.setError("用户已存在，请更换用户名重试");} else {
				sysUser.setCreateTime(new Date());
				sysUser.setUpdateTime(new Date());
				sysUserService.createSysUser(sysUser);
			}
		} catch (Exception e) {
			result.setError("系统错误:" + e.getMessage());
		}
		result.setMsg("用户注册成功");
		return result;
	}

	@ApiOperation(value = "根据用户名查询用户", notes = "校验用户存在不存在")
	@RequestMapping(value = "/queryUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Result<SysUser> queryUser(@RequestParam("name") String name) {
		Result<SysUser> result = new Result<SysUser>();
		SysUser sysUser = sysUserService.queryUserByName(name);
		result.setData(sysUser);
		return result;
	}

}
