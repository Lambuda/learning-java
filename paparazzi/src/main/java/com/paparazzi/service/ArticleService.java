package com.paparazzi.service;

import com.paparazzi.domain.Article;

import java.util.List;

public interface ArticleService {


	// 根据用户id 查询 用户所有文章
	List<Article> queryArticlesByUserId(Integer id);

	// 创建文章
	void createArticle(Article article);


	void updateArticle(Article article);

	void deleteArticle(Integer id);

	List<Article> queryAll();
}
