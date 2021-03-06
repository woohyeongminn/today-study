package com.ogong.pms.handler.member;

import org.apache.ibatis.session.SqlSession;
import com.ogong.menu.Menu;
import com.ogong.pms.dao.CeoMemberDao;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.handler.AuthCeoMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class CeoDeleteHandler implements Command {

  CeoMemberDao ceoMemberDao;
  SqlSession sqlSession;

  public CeoDeleteHandler(CeoMemberDao ceoMemberDao, SqlSession sqlSession) {
    this.ceoMemberDao = ceoMemberDao;
    this.sqlSession = sqlSession;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 기업회원 탈퇴");
    System.out.println();

    if (AuthCeoMemberLoginHandler.getLoginCeoMember() == null) {
      System.out.println(" >> 로그인 하세요.");
      return;
    }

    int no;
    try {
      no = (int) request.getAttribute("inputCeoNo");
    } catch (NullPointerException e) {
      no = AuthCeoMemberLoginHandler.getLoginCeoMember().getCeoNo();
    }

    CeoMember ceoMember = ceoMemberDao.findByNo(no);

    System.out.println(" << 이메일 확인 >>");
    String inputEmail = Prompt.inputString(" 이메일을 입력하세요 : ");

    if (!ceoMember.getCeoEmail().equals(inputEmail)) {
      System.out.println();
      System.out.println(" >> 이메일이 일치하지 않습니다.");
      return;
    } 

    System.out.println();
    System.out.println(" << 비밀번호 확인 >>");
    String inputPW = Prompt.inputString(" 비밀번호를 입력하세요 : ");

    CeoMember ceo = ceoMemberDao.findByEmailAndPassword(inputEmail, inputPW);

    if (ceo == null) {
      System.out.println();
      System.out.println(" >> 비밀번호가 일치하지 않습니다.");
      return;
    }

    System.out.println();
    String input = Prompt.inputString(" 정말 탈퇴하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 회원 탈퇴를 취소하였습니다.");
      return;
    }

    if (input.equals("네")) {
      // 카페삭제
      //    requestAgent.request("cafe.selectList", null);
      //
      //    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      //      System.out.println(" >> 스터디카페가 없습니다.");
      //      return;
      //    }
      //
      //    Collection<Cafe> cafeCollection = requestAgent.getObjects(Cafe.class);
      //    List<Cafe> cafeList = new ArrayList<>(cafeCollection);
      //
      //    for (int i = cafeList.size() -1; i >= 0; i--) {
      //      if (cafeList.get(i).getCeoMember().getCeoNo() == ceoMember.getCeoNo()) {
      //
      //        HashMap<String,String> cafeParams = new HashMap<>();
      //        cafeParams.put("cafeNo", String.valueOf(cafeList.get(i).getNo()));
      //
      //        requestAgent.request("cafe.delete", cafeParams);
      //
      //        if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      //          System.out.println(" >> 스터디 삭제가 실패되었습니다.");
      //          return;
      //        }
      //      }
      //    }

      ceoMember.setCeoBossName("Deleted Member("+ ceoMember.getCeoBossName() +")");
      ceoMember.setCeoEmail("Deleted Email");
      ceoMember.setCeoPassword("Deleted Password");
      ceoMember.setCeoPhoto("Deleted Photo");
      ceoMember.setCeoLicenseNo("Deleted LicenseNo");
      ceoMember.setCeoStatus(CeoMember.CEO);
      ceoMember.setActive(CeoMember.OUTUSER);

      ceoMemberDao.updateActive(ceoMember);
      sqlSession.commit();
      AuthCeoMemberLoginHandler.loginCeoMember = null;
      AuthCeoMemberLoginHandler.accessLevel = Menu.LOGOUT;

      System.out.println();
      System.out.println(" >> 회원 탈퇴를 완료하였습니다.");
      return;
    }
  }
}
