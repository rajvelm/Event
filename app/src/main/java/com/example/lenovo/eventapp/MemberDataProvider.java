package com.example.lenovo.eventapp;

/**
 * Created by lenovo on 10/27/2016.
 */

public class MemberDataProvider {
    private int memberId,memberStatus,memberAdmin;
    private String memberName,memberEmail,memberPass,memberSignup,memberLastLogin;
    //private Boolean memberStatus,memberAdmin;

    public MemberDataProvider(int memberId, String memberName, String memberEmail, String memberPass, String memberSignup,
                              String memberLastLogin,int memberStatus, int memberAdmin)
    {
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.memberPass = memberPass;
        this.memberSignup = memberSignup;
        this.memberLastLogin = memberLastLogin;
        this.memberStatus = memberStatus;
        this.memberAdmin = memberAdmin;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberPass() {
        return memberPass;
    }

    public void setMemberPass(String memberPass) {
        this.memberPass = memberPass;
    }

    public String getMemberSignup() {
        return memberSignup;
    }

    public void setMemberSignup(String memberSignup) {
        this.memberSignup = memberSignup;
    }

    public String getMemberLastLogin() {
        return memberLastLogin;
    }

    public void setMemberLastLogin(String memberLastLogin) {
        this.memberLastLogin = memberLastLogin;
    }

    public int getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(int memberStatus) {
        this.memberStatus = memberStatus;
    }

    public int getMemberAdmin() {
        return memberAdmin;
    }

    public void setMemberAdmin(int memberAdmin) {
        this.memberAdmin = memberAdmin;
    }
}
