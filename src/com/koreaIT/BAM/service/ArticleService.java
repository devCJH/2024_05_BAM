package com.koreaIT.BAM.service;

import com.koreaIT.BAM.dao.ArticleDao;

public class ArticleService {

	private ArticleDao articleDao;
	
	public ArticleService() {
		this.articleDao = new ArticleDao();
	}

	public int writeArticle(int memberId, String title, String body, int viewCnt) {
		return articleDao.writeArticle(memberId, title, body, viewCnt);
	}

}
