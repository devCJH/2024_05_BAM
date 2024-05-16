package com.koreaIT.BAM.service;

import com.koreaIT.BAM.dao.MemberDao;

public class MemberService {
	private MemberDao memberDao;

	public MemberService() {
		this.memberDao = new MemberDao();
	}

	public boolean loginIdDupChk(String loginId) {
		return memberDao.loginIdDupChk(loginId);
	}

	public void joinMember(String loginId, String loginPw, String name) {
		memberDao.joinMember(loginId, loginPw, name);
	}
	
	
}
