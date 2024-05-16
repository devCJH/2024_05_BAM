package com.koreaIT.BAM.service;

import com.koreaIT.BAM.dao.MemberDao;
import com.koreaIT.BAM.dto.Member;

public class MemberService {
	private MemberDao memberDao;

	public MemberService() {
		this.memberDao = new MemberDao();
	}
	
	public void joinMember(String loginId, String loginPw, String name) {
		memberDao.joinMember(loginId, loginPw, name);
	}
	
	public boolean loginIdDupChk(String loginId) {
		return memberDao.loginIdDupChk(loginId);
	}

	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}

	public String getLoginIdByMemberId(int memberId) {
		return memberDao.getLoginIdByMemberId(memberId);
	}
	
	
}
