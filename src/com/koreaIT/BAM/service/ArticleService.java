package com.koreaIT.BAM.service;

import java.util.List;

import com.koreaIT.BAM.dao.ArticleDao;
import com.koreaIT.BAM.dto.Article;

public class ArticleService {

	private ArticleDao articleDao;
	
	public ArticleService() {
		this.articleDao = new ArticleDao();
	}

	public int writeArticle(int memberId, String title, String body, int viewCnt) {
		return articleDao.writeArticle(memberId, title, body, viewCnt);
	}
	
	public List<Article> getPrintArticles(String searchKeyword) {
		return articleDao.getPrintArticles(searchKeyword);
	}

	public Article getArticleById(int id) {
		return articleDao.getArticleById(id);
	}

	public void increaseViewCnt(Article foundArticle) {
		articleDao.increaseViewCnt(foundArticle);
	}
	
	public void modifyArticle(Article foundArticle, String title, String body) {
		articleDao.modifyArticle(foundArticle, title, body);
	}

	public void deleteArticle(Article foundArticle) {
		articleDao.deleteArticle(foundArticle);
	}
}
