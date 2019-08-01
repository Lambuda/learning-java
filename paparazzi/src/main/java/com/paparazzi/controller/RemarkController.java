package com.paparazzi.controller;


import com.paparazzi.domain.Remark;
import com.paparazzi.domain.Result;
import com.paparazzi.service.RemarkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Api(description = "评论接口")
@RestController
@RequestMapping("/remark")
public class RemarkController {


	@Resource
	private RemarkService remarkService;

	@ApiOperation(value = "根据文章id查询所有评论", notes = "查询")
	@RequestMapping(value = "/queryRemarks", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Result<List<Remark>> queryRemarks(@RequestParam("articleId") Integer articleId) {
		Result<List<Remark> > result = new Result<>();
		try {
			List<Remark> remarks = remarkService.queryByArticleId(articleId);
			result.setData(remarks);
		} catch (Exception e) {
			result.setError("系统异常:" + e.getMessage());
		}
		return result;
	}


	@ApiOperation(value = "创建评论", notes = "用户id及文章id 不能为NULL")
	@RequestMapping(value = "/createRemark", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Result<String> createRemark(@RequestBody Remark remark) {
		Result<String> result = new Result<>();
		try {
			remark.setCreateTime(new Date());
			remark.setUpdateTime(new Date());
			remarkService.createRemark(remark);
		} catch (Exception e) {
			result.setError("系统异常:" + e.getMessage());
		}
		return result;
	}

	@ApiOperation(value = "更新评论", notes = "更新")
	@RequestMapping(value = "/updateRemark", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Result<String> updateRemark(@RequestBody Remark remark) {
		Result<String> result = new Result<>();
		try {
			remark.setUpdateTime(new Date());
			remarkService.updateRemark(remark);
		} catch (Exception e) {
			result.setError("系统异常:" + e.getMessage());
		}
		return result;
	}

	@ApiOperation(value = "删除评论", notes = "删除")
	@RequestMapping(value = "/deleteRemark", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Result<String> deleteRemark(@RequestParam("id") Integer id) {
		Result<String> result = new Result<>();
		try {
			remarkService.deleteRemark(id);
		} catch (Exception e) {
			result.setError("系统异常:" + e.getMessage());
		}
		return result;
	}
}
