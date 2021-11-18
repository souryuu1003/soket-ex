package com.soket.exam.dto;

import java.sql.Timestamp;

public class MemberDTO {
	
	private String memberId          = null;
	private String memberName          = null;
	private String memberPassword          = null;
	private String memberPhone          = null;
	private String memberEmail          = null;
	private Timestamp memberIndate		= null;
	private String memberPicture		= null;
	private String fileIdx = null;
	
	private String memberDelete	= null;
	private String memberSalt = null;
	
	private String memberSession = null;
	private Integer row           	= null;
	private Integer offset           = null;
	
	private String memberAdmin = null;
	
	private String totalScore = null;
	private String nowScore = null;
	private String itemSet = null;
	private String memberHost = null;
	private String memberPlay = null;
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberPassword() {
		return memberPassword;
	}
	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public Timestamp getMemberIndate() {
		return memberIndate;
	}
	public void setMemberIndate(Timestamp memberIndate) {
		this.memberIndate = memberIndate;
	}
	public String getMemberPicture() {
		return memberPicture;
	}
	public void setMemberPicture(String memberPicture) {
		this.memberPicture = memberPicture;
	}
	public String getFileIdx() {
		return fileIdx;
	}
	public void setFileIdx(String fileIdx) {
		this.fileIdx = fileIdx;
	}
	public String getMemberDelete() {
		return memberDelete;
	}
	public void setMemberDelete(String memberDelete) {
		this.memberDelete = memberDelete;
	}
	public String getMemberSalt() {
		return memberSalt;
	}
	public void setMemberSalt(String memberSalt) {
		this.memberSalt = memberSalt;
	}
	public String getMemberSession() {
		return memberSession;
	}
	public void setMemberSession(String memberSession) {
		this.memberSession = memberSession;
	}
	public Integer getRow() {
		return row;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public String getMemberAdmin() {
		return memberAdmin;
	}
	public void setMemberAdmin(String memberAdmin) {
		this.memberAdmin = memberAdmin;
	}
	public String getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}
	public String getNowScore() {
		return nowScore;
	}
	public void setNowScore(String nowScore) {
		this.nowScore = nowScore;
	}
	public String getItemSet() {
		return itemSet;
	}
	public void setItemSet(String itemSet) {
		this.itemSet = itemSet;
	}
	public String getMemberHost() {
		return memberHost;
	}
	public void setMemberHost(String memberHost) {
		this.memberHost = memberHost;
	}
	public String getMemberPlay() {
		return memberPlay;
	}
	public void setMemberPlay(String memberPlay) {
		this.memberPlay = memberPlay;
	}
	
	
}
