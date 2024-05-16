package com.koreaIT.BAM.container;

import com.koreaIT.BAM.dao.ArticleDao;
import com.koreaIT.BAM.dao.MemberDao;
import com.koreaIT.BAM.service.ArticleService;
import com.koreaIT.BAM.service.MemberService;

public class Container {
	
	public static MemberDao memberDao;
	public static ArticleDao articleDao;
	public static MemberService memberService;
	public static ArticleService articleService;
	
	static {
		memberDao = new MemberDao();
		articleDao = new ArticleDao();
		memberService = new MemberService();
		articleService = new ArticleService();
	}
}
