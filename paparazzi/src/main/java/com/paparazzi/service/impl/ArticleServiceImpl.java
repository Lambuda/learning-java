package com.paparazzi.service.impl;

import com.paparazzi.dao.ArticleMapper;
import com.paparazzi.domain.Article;
import com.paparazzi.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Resource
	private ArticleMapper articleMapper;

	@Override
	public List<Article> queryArticlesByUserId(Integer id) {
		List<Article> articles = articleMapper.selectByUserId(id);
		return articles;
	}

	@Transactional
	@Override
	public void createArticle(Article article) {
		articleMapper.insert(article);
	}

	@Transactional
	@Override
	public void updateArticle(Article article) {
		articleMapper.updateByPrimaryKey(article);
	}

	@Transactional
	@Override
	public void deleteArticle(Integer id) {
		articleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Article> queryAll() {
		return articleMapper.queryAll();
	}
}
