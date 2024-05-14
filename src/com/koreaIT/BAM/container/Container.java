package com.koreaIT.BAM.container;

import java.util.ArrayList;
import java.util.List;

import com.koreaIT.BAM.dto.Article;
import com.koreaIT.BAM.dto.Member;

public class Container {
	public static List<Member> members;
	public static List<Article> articles;
	
	static {
		members = new ArrayList<>();
		articles = new ArrayList<>();
	}
}
