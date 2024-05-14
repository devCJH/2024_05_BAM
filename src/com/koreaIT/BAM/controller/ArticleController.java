package com.koreaIT.BAM.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.BAM.container.Container;
import com.koreaIT.BAM.dto.Article;
import com.koreaIT.BAM.dto.Member;
import com.koreaIT.BAM.util.Util;

public class ArticleController extends Controller {
	
	private List<Article> articles;
	private List<Member> members;
	
	public ArticleController(Scanner sc) {
		this.sc = sc;
		this.articles = Container.articles;
		this.members = Container.members;
		this.lastId = 1;
	}

	@Override
	public void doAction(String cmd, String methodName) {
		this.cmd = cmd;
		
		switch(methodName) {
		case "write":
			doWrite();
			break;
		case "list":
			showList();
			break;
		case "detail":
			showDetail();
			break;
		case "modify":
			doModify();
			break;
		case "delete":
			doDelete();
			break;
		default:
			System.out.println("존재하지 않는 명령어 입니다");
		}
	}
	
	private void doWrite() {
		if (isLogined() == false) {
			System.out.println("로그인 하고 와");
			return;
		}
		
		System.out.printf("제목 : ");
		String title = sc.nextLine().trim();
		System.out.printf("내용 : ");
		String body = sc.nextLine().trim();

		Article article = new Article(lastId, Util.getDateStr(), loginedMember.getId(), title, body, 0);
		articles.add(article);

		System.out.println(lastId + "번 글이 생성되었습니다");
		lastId++;
	}
	
	public void showList() {
		if (articles.size() == 0) {
			System.out.println("존재하는 게시글이 없습니다");
			return;
		}

		String searchKeyword = cmd.substring("article list".length()).trim();
		
		List<Article> printArticles = articles;
		
		if (searchKeyword.length() > 0) {
			System.out.println("검색어 : " + searchKeyword);
			
			printArticles = new ArrayList<>();
			
			for (Article article : articles) {
				if (article.getTitle().contains(searchKeyword)) {
					printArticles.add(article);
				}
			}
			
			if (printArticles.size() == 0) {
				System.out.println("검색결과가 없습니다");
				return;
			}
		}
		
		System.out.println("번호	|	제목	|		날짜		|	작성자	|	조회수");

		for (int i = printArticles.size() - 1; i >= 0; i--) {
			Article article = printArticles.get(i);
			
			String writerLoingId = getLoginIdByMemberId(article.getMemberId());
			
			System.out.printf("%d	|	%s	|	%s	|	%s	|	%d\n", article.getId(), article.getTitle(),
					article.getRegDate(), writerLoingId, article.getViewCnt());
		}
	}

	public void showDetail() {
		int id = getCmdNum(cmd);

		if (id == 0) {
			System.out.println("명령어가 올바르지 않습니다");
			return;
		}

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.println(id + "번 게시물이 존재하지 않습니다");
			return;
		}

		foundArticle.increaseViewCnt();

		String writerLoingId = getLoginIdByMemberId(foundArticle.getMemberId());
		
		System.out.println("번호 : " + foundArticle.getId());
		System.out.println("날짜 : " + foundArticle.getRegDate());
		System.out.println("작성자 : " + writerLoingId);
		System.out.println("제목 : " + foundArticle.getTitle());
		System.out.println("내용 : " + foundArticle.getBody());
		System.out.println("조회수 : " + foundArticle.getViewCnt());
	}
	
	public void doModify() {
		if (isLogined() == false) {
			System.out.println("로그인 하고 와");
			return;
		}
		
		int id = getCmdNum(cmd);

		if (id == 0) {
			System.out.println("명령어가 올바르지 않습니다");
			return;
		}

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.println(id + "번 게시물이 존재하지 않습니다");
			return;
		}

		System.out.printf("수정할 제목 : ");
		String title = sc.nextLine().trim();
		System.out.printf("수정할 내용 : ");
		String body = sc.nextLine().trim();

		foundArticle.setTitle(title);
		foundArticle.setBody(body);

		System.out.println(id + "번 게시물이 수정되었습니다");
	}

	public void doDelete() {
		if (isLogined() == false) {
			System.out.println("로그인 하고 와");
			return;
		}
		
		int id = getCmdNum(cmd);

		if (id == 0) {
			System.out.println("명령어가 올바르지 않습니다");
			return;
		}

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.println(id + "번 게시물이 존재하지 않습니다");
			return;
		}

		articles.remove(foundArticle);

		System.out.println(id + "번 게시물이 삭제되었습니다");
	}
	
	private String getLoginIdByMemberId(int memberId) {
		for (Member member : members) {
			if (memberId == member.getId()) {
				return member.getLoginId();
			}
		}
		return null;
	}
	
	private Article getArticleById(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {
				return article;
			}
		}

		return null;
	}
	
	private int getCmdNum(String cmd) {
		String[] cmdBits = cmd.split(" ");

		try {
			int id = Integer.parseInt(cmdBits[2]);
			return id;
		} catch (NumberFormatException e) {
			return 0;
		} catch (ArrayIndexOutOfBoundsException e) {
			return 0;
		}
	}
	
	@Override
	public void makeTestData() {
		System.out.println("테스트용 게시글 데이터를 5개 생성했습니다");
		
		for (int i = 1; i <= 5; i++) {
			articles.add(new Article(lastId++, Util.getDateStr(), (int) (Math.random() * 3) + 1, "제목" + i, "내용" + i, i * 10));
		}
	}
}
