package com.ogong.pms.handler.member;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.ogong.menu.Menu;
import com.ogong.pms.dao.MemberDao;
import com.ogong.pms.domain.Member;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class MemberUpdateHandler implements Command {

  MemberDao memberDao;
  SqlSession sqlSession;

  public MemberUpdateHandler(MemberDao memberDao, SqlSession sqlSession) {
    this.memberDao = memberDao;
    this.sqlSession = sqlSession;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 프로필 수정");
    System.out.println();

    int no = (int) request.getAttribute("memberNo");

    List<Member> memberList = memberDao.findAll();
    Member member = memberDao.findByNo(no);

    System.out.println("1. 이름");
    System.out.println("2. 닉네임");
    System.out.println("3. 사진");
    System.out.println("4. 전화번호");
    System.out.println("5. 이메일");
    System.out.println("6. 비밀번호");
    System.out.println("0. 이전");
    System.out.println();
    int selectNo = Prompt.inputInt(" 수정하고 싶은 정보를 선택해 주세요. > ");

    String perName = member.getPerName();
    String perNickName = member.getPerNickname();
    String perPhoto = member.getPerPhoto();
    String perTel = member.getPerTel();
    String perEmail = member.getPerEmail();
    String perPassword = member.getPerPassword();

    switch (selectNo) {
      case 1:
        LOOP: while (true) {
          perName = Prompt.inputString(" 이  름(" + member.getPerName() + ") : ");
          for (Member preMemberName : memberList) {
            if (perName.equals(preMemberName.getPerName())) {
              System.out.println(" >> 이미 사용 중인 이름입니다.");
              continue LOOP;
            }
          }
          break;
        }
        break;

      case 2:
        perNickName = Prompt.inputString(" 닉네임(" + member.getPerNickname() + ") : ");
        while (true) {
          for (Member comparisonMember : memberList) {
            if (perNickName.equals(comparisonMember.getPerNickname())) {
              System.out.println(" >> 이미 사용중인 닉네임입니다.");
              continue;
            }
          }
          break;
        }
        break;

      case 3:
        perPhoto = Prompt.inputString(" 사  진(" + member.getPerPhoto() + ") : ");
        break;

      case 4:
        LOOP: while (true) {
          perTel = Prompt.inputString(" 전화번호(" + member.getPerTel() + ") : ");
          // [삭제] 입력 형식 불명확
          // if (perTel.length() > 10 && perTel.length() < 11) {
          // System.out.println(" >> 올바른 형식의 전화번호를 입력해 주세요.");
          // continue;
          // }

          for (Member perMemberTel : memberList) {
            if (perTel.equals(perMemberTel.getPerTel())) {
              System.out.println(" >> 이미 사용 중인 전화번호입니다.");
              continue LOOP;
            }
          }
          break;
        }
        break;

      case 5:
        while (true) {
          perEmail = Prompt.inputString(" 이메일(" + member.getPerEmail() + ") : ");
          if (!perEmail.contains("@") || !perEmail.contains(".com") || perEmail.length() < 6) {
            System.out.println(" >> 정확한 이메일 양식으로 입력해 주세요.");
            continue;
          }
          break;
        }
        break;

      case 6:
        while (true) {
          perPassword = Prompt.inputString(" 변경할 비밀번호 : ");
          if (perPassword.length() < 8 || (!perPassword.contains("!") && !perPassword.contains("@")
              && !perPassword.contains("#") && !perPassword.contains("$")
              && !perPassword.contains("^") && !perPassword.contains("%")
              && !perPassword.contains("&") && !perPassword.contains("*"))) {
            System.out.println(" >> 8자 이상 특수문자를 포함시켜 주세요.");
            continue;
          }
          break;
        }

        while (true) {
          String pw = Prompt.inputString(" 비밀번호 확인 : ");
          if (!pw.equals(perPassword)) {
            System.out.println("\n >> 확인 실패!");
            continue;
          } else {
            System.out.println("\n >> 확인 완료!");
          }
          break;
        }
        break;

      case 0: return;

      default:
        System.out.println(" >> 올바른 번호를 입력해 주세요.");
        return;
    }

    System.out.println();
    String input = Prompt.inputString(" 정말 변경하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 회원 변경을 취소하였습니다.");
      return;
    }

    if (selectNo == 1) {
      member.setPerName(perName);
      memberDao.updateName(member);
      sqlSession.commit();

    } else if (selectNo == 2) {
      member.setPerNickname(perNickName);
      memberDao.updateNickname(member);
      sqlSession.commit();

    } else if (selectNo == 3) {
      member.setPerPhoto(perPhoto);
      memberDao.updatePhoto(member);
      sqlSession.commit();

    } else if (selectNo == 4) {
      member.setPerTel(perTel);
      memberDao.updateTel(member);
      sqlSession.commit();

    } else if (selectNo == 5) {
      member.setPerEmail(perEmail);
      memberDao.updateEmail(member);
      sqlSession.commit();

    } else if (selectNo == 6) {
      member.setPerPassword(perPassword);
      memberDao.updatePassword(member);
      sqlSession.commit();

      AuthPerMemberLoginHandler.loginUser = null;
      AuthPerMemberLoginHandler.accessLevel = Menu.LOGOUT;
      System.out.println("\n >> 회원 정보를 변경하였습니다.\n");
      System.out.println(" >> 다시 로그인 해 주세요.");
      return;
    }

    System.out.println(" >> 회원 정보를 변경하였습니다.");

  }
}
