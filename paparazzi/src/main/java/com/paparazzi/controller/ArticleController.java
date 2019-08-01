package com.paparazzi.controller;


import com.paparazzi.domain.Article;
import com.paparazzi.domain.Result;
import com.paparazzi.service.ArticleService;
import com.paparazzi.service.RemarkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Api(description = "文章接口")
@RestController
@RequestMapping("/article")
public class ArticleController {


	@Resource
	private ArticleService articleService;

	@ApiOperation(value = "根据用户id查询所有文章", notes = "查询")
	@RequestMapping(value = "/queryArticles", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Result<List<Article> > queryArticles(@RequestParam("userId") Integer userId) {
		Result<List<Article> > result = new Result<>();
		try {
			List<Article> articles = articleService.queryArticlesByUserId(userId);
			result.setData(articles);
		} catch (Exception e) {
			result.setError("系统异常:" + e.getMessage());
		}
		return result;
	}

	@ApiOperation(value = "查询所有文章", notes = "查询")
	@RequestMapping(value = "/queryAll", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Result<List<Article> > queryAll() {
		Result<List<Article> > result = new Result<>();
		try {
			List<Article> articles = articleService.queryAll();
			result.setData(articles);
		} catch (Exception e) {
			result.setError("系统异常:" + e.getMessage());
		}
		return result;
	}


	@ApiOperation(value = "创建文章", notes = "用户 id 不能为 NULL")
	@RequestMapping(value = "/createArticle", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Result<String> createArticle(@RequestBody Article article) {
		Result<String> result = new Result<>();
		try {
			article.setCreateTime(new Date());
			article.setUpdateTime(new Date());
			articleService.createArticle(article);
		} catch (Exception e) {
			result.setError("系统异常:" + e.getMessage());
		}
		return result;
	}

	@ApiOperation(value = "更新文章", notes = "更新")
	@RequestMapping(value = "/updateArticle", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Result<String> updateArticle(@RequestBody Article article) {
		Result<String> result = new Result<>();
		try {
			article.setUpdateTime(new Date());
			articleService.updateArticle(article);
		} catch (Exception e) {
			result.setError("系统异常:" + e.getMessage());
		}
		return result;
	}

	@ApiOperation(value = "删除文章", notes = "删除")
	@RequestMapping(value = "/deleteArticle", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Result<String> deleteArticle(@RequestParam("id") Integer id) {
		Result<String> result = new Result<>();
		try {
			articleService.deleteArticle(id);
		} catch (Exception e) {
			result.setError("系统异常:" + e.getMessage());
		}
		return result;
	}
}
