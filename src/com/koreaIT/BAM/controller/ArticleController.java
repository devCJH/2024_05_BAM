package com.koreaIT.BAM.controller;

import java.util.List;
import java.util.Scanner;

import com.koreaIT.BAM.dto.Article;
import com.koreaIT.BAM.service.ArticleService;
import com.koreaIT.BAM.service.MemberService;

public class ArticleController extends Controller {
	
	private ArticleService articleService;
	private MemberService memberService;
	
	public ArticleController(Scanner sc) {
		this.sc = sc;
		this.articleService = new ArticleService();
		this.memberService = new MemberService();
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
		System.out.printf("제목 : ");
		String title = sc.nextLine().trim();
		System.out.printf("내용 : ");
		String body = sc.nextLine().trim();

		int articleNumber = articleService.writeArticle(1, title, body, 0);
		
		System.out.println(articleNumber + "번 글이 생성되었습니다");
	}
	
	public void showList() {
		String searchKeyword = cmd.substring("article list".length()).trim();
		
		List<Article> printArticles = articleService.getPrintArticles(searchKeyword);

		if (printArticles.size() == 0) {
			System.out.println("게시물이 없습니다");
			return;
		}
		
		System.out.println("번호	|	제목	|		날짜		|	작성자	|	조회수");

		for (int i = printArticles.size() - 1; i >= 0; i--) {
			Article article = printArticles.get(i);
			
			String writerLoingId = memberService.getLoginIdByMemberId(article.getMemberId());
			
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

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			System.out.println(id + "번 게시물이 존재하지 않습니다");
			return;
		}

		articleService.increaseViewCnt(foundArticle);

		String writerLoingId = memberService.getLoginIdByMemberId(foundArticle.getMemberId());
		
		System.out.println("번호 : " + foundArticle.getId());
		System.out.println("날짜 : " + foundArticle.getRegDate());
		System.out.println("작성자 : " + writerLoingId);
		System.out.println("제목 : " + foundArticle.getTitle());
		System.out.println("내용 : " + foundArticle.getBody());
		System.out.println("조회수 : " + foundArticle.getViewCnt());
	}
	
	public void doModify() {
		int id = getCmdNum(cmd);

		if (id == 0) {
			System.out.println("명령어가 올바르지 않습니다");
			return;
		}

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			System.out.println(id + "번 게시물이 존재하지 않습니다");
			return;
		}
		
		if (foundArticle.getMemberId() != loginedMember.getId()) {
			System.out.println("해당 게시물에 대한 권한이 없습니다");
			return;
		}

		System.out.printf("수정할 제목 : ");
		String title = sc.nextLine().trim();
		System.out.printf("수정할 내용 : ");
		String body = sc.nextLine().trim();

		articleService.modifyArticle(foundArticle, title, body);
		
		System.out.println(id + "번 게시물이 수정되었습니다");
	}

	public void doDelete() {
		int id = getCmdNum(cmd);

		if (id == 0) {
			System.out.println("명령어가 올바르지 않습니다");
			return;
		}

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			System.out.println(id + "번 게시물이 존재하지 않습니다");
			return;
		}

		if (foundArticle.getMemberId() != loginedMember.getId()) {
			System.out.println("해당 게시물에 대한 권한이 없습니다");
			return;
		}
		
		articleService.deleteArticle(foundArticle);

		System.out.println(id + "번 게시물이 삭제되었습니다");
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
			articleService.writeArticle((int) (Math.random() * 3) + 1, "제목" + i, "내용" + i, i * 10);
		}
	}
}
