package com.ogong.pms.handler;

import com.ogong.pms.domain.Member;
import com.ogong.util.Prompt;

public class MemberFindIdPwHandler implements Command {

  PromptPerMember promptPerMember;

  public MemberFindIdPwHandler(PromptPerMember promptPerMember) {
    this.promptPerMember = promptPerMember;
  }

  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("1. 이메일 찾기");
    System.out.println("2. 비밀번호 찾기");
    int selectNo = Prompt.inputInt("선택> ");
    switch (selectNo) {
      case 1 : wantPerEmail(); break;
      case 2 : wantByPerPw(); break;
      default : return;
    }
  }


  public void wantPerEmail() {
    System.out.println();
    System.out.println("▶ 이메일 찾기");
    while (true) {
      System.out.println();
      String inputNick =  Prompt.inputString(" 닉네임 : ");
      Member member = promptPerMember.findByMemberNick(inputNick);
      if (member == null) {
        System.out.println(" >> 해당 닉네임이 존재하지 않습니다.");
        return;
      } else {
        System.out.println();
        System.out.printf(" '%s님'의 이메일 >> ", member.getPerNickname());
        System.out.println(member.getPerEmail());
      }
      break;
    }
    String input = Prompt.inputString(" 비밀번호 찾기로 넘어가시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 찾기를 종료합니다.");
      return;
    } 
    wantByPerPw();
  }

  public void wantByPerPw() {
    System.out.println();
    System.out.println("▶ 임시 비밀번호 발급");
    while (true) {
      System.out.println();
      String inputEmail =  Prompt.inputString(" 이메일 : ");
      Member member = promptPerMember.findByMemberEmail(inputEmail);
      if (member == null) {
        System.out.println(" >> 해당 이메일이 존재하지 않습니다.");
        continue;
      } else {
        System.out.printf(" '%s님'의 임시 비밀번호 : ", member.getPerNickname());
        System.out.println(member.getPerPassword().hashCode());
        System.out.println();
        System.out.println(" >> 로그인 후 비밀번호를 변경해 주세요.");
        String hashPW = String.valueOf(member.getPerPassword().hashCode());
        member.setPerPassword(hashPW);
      }
      break;
    }
  }
}
