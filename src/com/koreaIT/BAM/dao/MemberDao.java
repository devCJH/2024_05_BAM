package com.koreaIT.BAM.dao;

import java.util.List;

import com.koreaIT.BAM.container.Container;
import com.koreaIT.BAM.dto.Member;
import com.koreaIT.BAM.util.Util;

public class MemberDao {
	private List<Member> members;
	private int lastId;
	
	public MemberDao() {
		this.lastId = 1;
		this.members = Container.members;
	}
	
	public void joinMember(String loginId, String loginPw, String name) {
		members.add(new Member(lastId, Util.getDateStr(), loginId, loginPw, name));
		lastId++;
	}
	
	private Member getMemberByLoginId(String loginId) {
		for (Member member : members) {
			if (member.getLoginId().equals(loginId)) {
				return member;
			}
		}
		return null;
	}
	
	public boolean loginIdDupChk(String loginId) {
		Member member = getMemberByLoginId(loginId);
		
		if (member != null) {
			return false;
		}
		return true;
	}
}
