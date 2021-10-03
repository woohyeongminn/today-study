package com.ogong.pms.handler.admin;

import java.util.HashMap;
import com.ogong.pms.domain.Admin;
import com.ogong.pms.handler.AuthAdminLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;

public class AdminDetailHandler implements Command {

  RequestAgent requestAgent;

  public AdminDetailHandler(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 내 프로필");
    System.out.println();

    int no = AuthAdminLoginHandler.getLoginAdmin().getMasterNo();

    HashMap<String,String> params = new HashMap<>();
    params.put("adminNo", String.valueOf(no));

    requestAgent.request("admin.selectOne", params);

    Admin adminpro = requestAgent.getObject(Admin.class);

    System.out.printf(" 닉네임 : %s\n", adminpro.getMasterNickname());
    System.out.printf(" 이메일 : %s\n", adminpro.getMasterEmail());

    request.setAttribute("inputNo", adminpro.getMasterNo());

    System.out.println();
    System.out.println("1. 수정");
    System.out.println("0. 이전");

    while (true) {
      int selectAdminNo = Prompt.inputInt("선택> ");
      switch (selectAdminNo) {
        case 1: request.getRequestDispatcher("/admin/update").forward(request); return;
        case 0: return;
        default :
          System.out.println(" >> 번호를 다시 선택해 주세요.");
      }
    }
  }
}
