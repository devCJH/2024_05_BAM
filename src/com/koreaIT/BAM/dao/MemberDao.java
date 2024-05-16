package com.koreaIT.BAM.dao;

import java.util.ArrayList;
import java.util.List;

import com.koreaIT.BAM.dto.Member;
import com.koreaIT.BAM.util.Util;

public class MemberDao {
	private List<Member> members;
	private int lastId;
	
	public MemberDao() {
		this.lastId = 1;
		this.members = new ArrayList<>();
	}
	
	public void joinMember(String loginId, String loginPw, String name) {
		members.add(new Member(lastId, Util.getDateStr(), loginId, loginPw, name));
		lastId++;
	}
	
	public boolean loginIdDupChk(String loginId) {
		Member member = getMemberByLoginId(loginId);
		
		if (member != null) {
			return false;
		}
		return true;
	}
	
	public Member getMemberByLoginId(String loginId) {
		for (Member member : members) {
			if (member.getLoginId().equals(loginId)) {
				return member;
			}
		}
		return null;
	}
	
	public String getLoginIdByMemberId(int memberId) {
		for (Member member : members) {
			if (memberId == member.getId()) {
				return member.getLoginId();
			}
		}
		return null;
	}
}
