package com.paparazzi.controller;


import com.paparazzi.config.ServerConfig;
import com.paparazzi.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Api(description = "系统接口")
@RestController
@RequestMapping("/sys")
public class SysController {

	@Resource
	private ServerConfig serverConfig;


	@ApiOperation(value = "获取系统URL", notes = "获取系统URL")
	@RequestMapping(value = "/getUrl", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Result<String> getUrl() {
		Result<String> result = new Result<String>();
		String url = serverConfig.getUrl();
		result.setData(url);
		return result;
	}

	@ApiOperation(value = "用户头像图片上传", notes = "图片上传")
	@RequestMapping(value = "/uploads", method = RequestMethod.POST)
	@ResponseBody
	public Result<String> uploads(HttpServletRequest request, MultipartFile file) {

		Result<String> result =  new Result<>();
		try {
			//上传目录地址
			String uploadDir = ResourceUtils.getURL("classpath:").getPath() + "static/img/";
			//如果目录不存在，自动创建文件夹
			File dir = new File(uploadDir);
			if (!dir.exists()) {
				dir.mkdir();
			}
			//调用上传方法
			String filename = executeUpload(uploadDir, file);
			result.setMsg("上传成功，图片地址为：" + serverConfig.getUrl()+"/img/" + filename );
			result.setData(serverConfig.getUrl()+"/img/" + filename);
		} catch (Exception e) {
			result.setError("系统异常:" + e.getMessage());
  		}
		return result;
	}


	/**
	 * 文件上传
	 * @param uploadDir 上传文件目录
	 * @param file 上传对象
	 * @throws Exception
	 */
	private String executeUpload(String uploadDir, MultipartFile file) throws Exception {

		//文件后缀名
		//String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		//上传文件名
		String filename = file.getOriginalFilename();
		//服务器端保存的文件对象
		File serverFile = new File(uploadDir + filename);
		//将上传的文件写入到服务器端文件内
		file.transferTo(serverFile);
		return filename;
	}

}
