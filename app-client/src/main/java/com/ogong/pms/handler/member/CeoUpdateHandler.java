package com.ogong.pms.handler.member;

import org.apache.ibatis.session.SqlSession;
import com.ogong.menu.Menu;
import com.ogong.pms.dao.CeoMemberDao;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.handler.AuthCeoMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class CeoUpdateHandler implements Command {

  CeoMemberDao ceoMemberDao;
  SqlSession sqlSession;

  public CeoUpdateHandler(CeoMemberDao ceoMemberDao, SqlSession sqlSession) {
    this.ceoMemberDao = ceoMemberDao;
    this.sqlSession = sqlSession;
  }

  // 기업회원 개인정보 수정은 이름,이메일,비밀번호만 가능
  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 기업 프로필 수정"); 
    System.out.println();

    int no = (int) request.getAttribute("inputCeoNo");

    CeoMember ceoMember = ceoMemberDao.findByNo(no);

    System.out.println("1. 이름");
    System.out.println("2. 닉네임");
    System.out.println("3. 사진");
    System.out.println("4. 전화번호");
    System.out.println("5. 이메일");
    System.out.println("6. 비밀번호");
    System.out.println("0. 이전");
    System.out.println();
    int selectNo = Prompt.inputInt(" 수정하고 싶은 정보를 선택해 주세요. > ");

    String ceoName = ceoMember.getCeoName();
    String ceoNickName = ceoMember.getCeoNickname();
    String ceophoto = ceoMember.getCeoPhoto();
    String ceoTel = ceoMember.getCeoTel();
    String ceoEmail = ceoMember.getCeoEmail();
    String ceoPassword = ceoMember.getCeoPassword();

    switch (selectNo) {
      case 1:
        ceoName = Prompt.inputString(" 이름(" + ceoMember.getCeoName()  + ") : ");
        break;
      case 2:
        ceoNickName = Prompt.inputString(" 닉네임(" + ceoMember.getCeoNickname()  + ") : ");
        break;
      case 3:
        ceophoto = Prompt.inputString(" 사  진(" + ceoMember.getCeoPhoto()  + ") : ");
        break;
      case 4:
        ceoTel = Prompt.inputString(" 전화번호(" + ceoMember.getCeoTel()  + ") : ");
        break;
      case 5:
        ceoEmail = Prompt.inputString(" 이메일(" + ceoMember.getCeoEmail() + ") : ");
        break;
      case 6:
        while (true) {
          ceoPassword = Prompt.inputString(" 변경할 비밀번호 : ");
          if (ceoPassword.length() < 8 || (!ceoPassword.contains("!") && !ceoPassword.contains("@")
              && !ceoPassword.contains("#") && !ceoPassword.contains("$")
              && !ceoPassword.contains("^") && !ceoPassword.contains("%")
              && !ceoPassword.contains("&") && !ceoPassword.contains("*"))) {
            System.out.println(" >> 8자 이상 특수문자를 포함시켜 주세요.");
            continue;
          }
          break;
        }
      case 0: return;
      default : 
        System.out.println(" >> 올바른 번호를 입력해 주세요.");
        return;
    }

    //String ceoStoreName = Prompt.inputString(" 점포명(" + ceoMember.getCeoStoreName() + ") : ");
    //String ceoStoreDetailAddress = Prompt.inputString("점포주소(" + ceoMember.getCeoStoreDetailAddress() + ") : ");

    System.out.println();
    String input = Prompt.inputString(" 정말 변경하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 회원 변경을 취소하였습니다.");
      return;
    }

    if (selectNo == 1) {
      ceoMember.setCeoName(ceoName);
      ceoMemberDao.updateName(ceoMember);
      sqlSession.commit();

    } else if (selectNo == 2) {
      ceoMember.setCeoNickname(ceoNickName);
      ceoMemberDao.updateNickName(ceoMember);
      sqlSession.commit();

    } else if (selectNo == 3) {
      ceoMember.setCeoPhoto(ceophoto);
      ceoMemberDao.updatePhoto(ceoMember);
      sqlSession.commit();

    } else if (selectNo == 4) {
      ceoMember.setCeoTel(ceoTel);
      ceoMemberDao.updateTel(ceoMember);
      sqlSession.commit();

    } else if (selectNo == 5) {
      ceoMember.setCeoEmail(ceoEmail);
      ceoMemberDao.updateEmail(ceoMember);
      sqlSession.commit();

    } else if (selectNo == 6) {
      ceoMember.setCeoPassword(ceoPassword);
      ceoMemberDao.updatePassword(ceoMember);
      sqlSession.commit();

      AuthCeoMemberLoginHandler.loginCeoMember = null;
      AuthCeoMemberLoginHandler.accessLevel = Menu.LOGOUT;
      System.out.println("\n >> 회원 정보를 변경하였습니다.\n");
      System.out.println(" >> 다시 로그인 해 주세요.");
      return;
    }

    System.out.println(" >> 회원 정보를 변경하였습니다.");

  }
}







