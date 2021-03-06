package com.ogong.pms.handler.admin;

import java.util.Collection;
import com.ogong.pms.dao.CeoMemberDao;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;

public class AdminCeoMemberListHandler implements Command  {

  CeoMemberDao ceoMemberDao;

  public AdminCeoMemberListHandler(CeoMemberDao ceoMemberDao) {
    this.ceoMemberDao = ceoMemberDao;
  }

  // 관리자가 사용
  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 기업회원 목록");
    System.out.println();

    Collection<CeoMember> ceoMemberList = ceoMemberDao.findAll();

    for (CeoMember ceoMember : ceoMemberList) {
      System.out.printf(" (%d) 이름:%s | 닉네임 : %s | 이메일 : %s | 가입일 : %s\n",
          ceoMember.getCeoNo(),
          ceoMember.getCeoName(),
          ceoMember.getCeoNickname(),
          ceoMember.getCeoEmail(),
          ceoMember.getCeoRegisteredDate());
      System.out.println();
    }
  }
}
