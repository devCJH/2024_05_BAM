package com.koreaIT.BAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.BAM.dto.Article;
import com.koreaIT.BAM.dto.Member;
import com.koreaIT.BAM.util.Util;

public class App {

	private List<Article> articles;
	private List<Member> members;
	private int lastArticleId;
	private int lastMemberId;

	public App() {
		this.articles = new ArrayList<>();
		this.members = new ArrayList<>();
		this.lastArticleId = 1;
		this.lastMemberId = 1;
	}

	public void run() {
		System.out.println("== 프로그램 시작 ==");

		makeTestData();

		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.printf("명령어) ");
			String cmd = sc.nextLine().trim();

			if (cmd.equals("exit")) {
				break;
			}

			if (cmd.length() == 0) {
				System.out.println("명령어를 입력해주세요");
				continue;
			}

			if (cmd.equals("member join")) {
				
				String loginId = null;
				String loginPw = null;
				String loginPwChk = null;
				String name = null;
				
				while (true) {
					System.out.printf("아이디 : ");
					loginId = sc.nextLine().trim();
					
					if (loginId.length() == 0) {
						System.out.println("아이디는 필수 입력정보입니다");
						continue;
					}
					
					if (loginIdDupChk(loginId) == false) {
						System.out.println("[" + loginId + "] 은(는) 이미 사용중인 아이디입니다");
						continue;
					}
					
					System.out.println("[" + loginId + "] 은(는) 사용가능한 아이디입니다");
					break;
				}
				
				while (true) {
					System.out.printf("비밀번호 : ");
					loginPw = sc.nextLine().trim();
					
					if (loginPw.length() == 0) {
						System.out.println("비밀번호는 필수 입력정보입니다");
						continue;
					}
					
					System.out.printf("비밀번호 확인 : ");
					loginPwChk = sc.nextLine().trim();
					
					if (loginPw.equals(loginPwChk) == false) {
						System.out.println("비밀번호를 다시 입력해주세요");
						continue;
					}
					break;
				}
				
				while (true) {
					System.out.printf("이름 : ");
					name = sc.nextLine().trim();
					
					if (name.length() == 0) {
						System.out.println("이름은 필수 입력정보입니다");
						continue;
					}
					break;
				}

				Member member = new Member(lastMemberId, Util.getDateStr(), loginId, loginPw, name);
				members.add(member);

				System.out.println("[" + loginId + "] 회원님의 가입이 완료되었습니다");
				lastMemberId++;

			} else if (cmd.equals("article write")) {
				System.out.printf("제목 : ");
				String title = sc.nextLine().trim();
				System.out.printf("내용 : ");
				String body = sc.nextLine().trim();

				Article article = new Article(lastArticleId, Util.getDateStr(), title, body, 0);
				articles.add(article);

				System.out.println(lastArticleId + "번 글이 생성되었습니다");
				lastArticleId++;

			} else if (cmd.startsWith("article list")) {
				if (articles.size() == 0) {
					System.out.println("존재하는 게시글이 없습니다");
					continue;
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
						continue;
					}
				}
				
				System.out.println("번호	|	제목	|		날짜		|	조회수");

				for (int i = printArticles.size() - 1; i >= 0; i--) {
					Article article = printArticles.get(i);
					System.out.printf("%d	|	%s	|	%s	|	%d\n", article.getId(), article.getTitle(),
							article.getRegDate(), article.getViewCnt());
				}

			} else if (cmd.startsWith("article detail ")) {
				int id = getCmdNum(cmd);

				if (id == 0) {
					System.out.println("명령어가 올바르지 않습니다");
					continue;
				}

				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					System.out.println(id + "번 게시물이 존재하지 않습니다");
					continue;
				}

				foundArticle.increaseViewCnt();

				System.out.println("번호 : " + foundArticle.getId());
				System.out.println("날짜 : " + foundArticle.getRegDate());
				System.out.println("제목 : " + foundArticle.getTitle());
				System.out.println("내용 : " + foundArticle.getBody());
				System.out.println("조회수 : " + foundArticle.getViewCnt());

			} else if (cmd.startsWith("article modify ")) {
				int id = getCmdNum(cmd);

				if (id == 0) {
					System.out.println("명령어가 올바르지 않습니다");
					continue;
				}

				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					System.out.println(id + "번 게시물이 존재하지 않습니다");
					continue;
				}

				System.out.printf("수정할 제목 : ");
				String title = sc.nextLine().trim();
				System.out.printf("수정할 내용 : ");
				String body = sc.nextLine().trim();

				foundArticle.setTitle(title);
				foundArticle.setBody(body);

				System.out.println(id + "번 게시물이 수정되었습니다");

			} else if (cmd.startsWith("article delete ")) {
				int id = getCmdNum(cmd);

				if (id == 0) {
					System.out.println("명령어가 올바르지 않습니다");
					continue;
				}

				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					System.out.println(id + "번 게시물이 존재하지 않습니다");
					continue;
				}

				articles.remove(foundArticle);

				System.out.println(id + "번 게시물이 삭제되었습니다");

			} else {
				System.out.println("존재하지 않는 명령어 입니다");
			}

		}

		sc.close();

		System.out.println("== 프로그램 끝 ==");
	}

	private Article getArticleById(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {
				return article;
			}
		}

		return null;
	}
	
	private boolean loginIdDupChk(String loginId) {
		for (Member member : members) {
			if (member.getLoginId().equals(loginId)) {
				return false;
			}
		}
		return true;
	}

	private int getCmdNum(String cmd) {
		String[] cmdBits = cmd.split(" ");

		try {
			int id = Integer.parseInt(cmdBits[2]);
			return id;
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	private void makeTestData() {
		System.out.println("테스트용 게시글 데이터를 5개 생성했습니다");

		for (int i = 1; i <= 5; i++) {
			articles.add(new Article(lastArticleId++, Util.getDateStr(), "제목" + i, "내용" + i, i * 10));
		}
	}
}
