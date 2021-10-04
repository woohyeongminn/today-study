package com.ogong.pms.handler;

import java.util.HashMap;
import com.ogong.menu.Menu;
import com.ogong.pms.domain.CeoMember;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;

public class AuthCeoMemberLoginHandler extends AbstractLoginHandler implements Command  {

  RequestAgent requestAgent;

  public static CeoMember loginCeoMember;
  public static CeoMember getLoginCeoMember() {
    return loginCeoMember;
  }

  public AuthCeoMemberLoginHandler(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  // ----------------------------------------------------------------------

  @Override
  public void execute(CommandRequest request) throws Exception {

    System.out.println();
    String inputEmail = Prompt.inputString(" 이메일 : ");
    String inputPassword = Prompt.inputString(" 비밀번호 : ");

    HashMap<String,String> params = new HashMap<>();
    params.put("email", inputEmail);
    params.put("password", inputPassword);

    requestAgent.request("ceoMember.selectOneByEmailPassword", params);

    if (requestAgent.getStatus().equals(RequestAgent.SUCCESS)) {
      CeoMember ceoMember = requestAgent.getObject(CeoMember.class);
      System.out.printf("\n >> '%s'님 환영합니다!\n", ceoMember.getCeoBossName());
      loginCeoMember = ceoMember;
      accessLevel = Menu.CEO_LOGIN;
      return;
    } else if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("이메일과 암호가 일치하는 회원을 찾을 수 없습니다.");
      return;
    }
  }
}