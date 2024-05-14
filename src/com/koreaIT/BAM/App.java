package com.koreaIT.BAM;

import java.util.Scanner;

import com.koreaIT.BAM.controller.ArticleController;
import com.koreaIT.BAM.controller.Controller;
import com.koreaIT.BAM.controller.MemberController;

public class App {
	public void run() {
		System.out.println("== 프로그램 시작 ==");

		Scanner sc = new Scanner(System.in);

		MemberController memberController = new MemberController(sc);
		ArticleController articleController = new ArticleController(sc);
		
		memberController.makeTestData();
		articleController.makeTestData();
		
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

			String[] cmdBits = cmd.split(" ");
			
			if (cmdBits.length < 2) {
				System.out.println("존재하지 않는 명령어 입니다");
				continue;
			}
			
			String controllerName = cmdBits[0];
			String methodName = cmdBits[1];
			
			
			switch (controllerName + "/" + methodName) {
			case "article/write":
			case "article/modify":
			case "article/delete":
			case "member/logout":
				if (Controller.isLogined() == false) {
					System.out.println("로그인부터 하고 와");
					continue;
				}
				break;
			case "member/join":
			case "member/login":
				if (Controller.isLogined()) {
					System.out.println("로그아웃부터 하고 와");
					continue;
				}
				break;
			}
			
			Controller controller = null;
			
			if (controllerName.equals("article")) {
				controller = articleController;
			} else if (controllerName.equals("member")) {
				controller = memberController;
			} else {
				System.out.println("존재하지 않는 명령어 입니다");
				continue;
			}
			
			controller.doAction(cmd, methodName);
		}

		sc.close();

		System.out.println("== 프로그램 끝 ==");
	}
}