package com.ogong.pms.handler.myStudy.guilder;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class GuilderEntrustHandler implements Command { 

  StudyDao studyDao;
  SqlSession sqlSession;

  public GuilderEntrustHandler(StudyDao studyDao, SqlSession sqlSession) {
    this.studyDao = studyDao;
    this.sqlSession = sqlSession;
  }


  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 조장 권한 위임");
    System.out.println();

    int inputNo = (int) request.getAttribute("inputNo");

    Member member = AuthPerMemberLoginHandler.getLoginUser();

    Study myStudy = studyDao.findByNo(inputNo);

    List<Member> guilder = studyDao.findByGuildersAll(myStudy.getStudyNo());
    myStudy.setMembers(guilder);

    List<Member> guilders = myStudy.getMembers();

    if (guilders.isEmpty()) {
      System.out.println(" >> 해당 스터디 구성원이 없습니다.");
      return;
    }

    System.out.println(" <구성원>"); 
    for (int i = 0; i < guilders.size(); i++) {
      System.out.println(guilders.get(i).getPerNickname());
    }

    System.out.println("----------------------");
    System.out.println();

    String inputGuilderNick = Prompt.inputString(" >> 조장 권한을 위임해 줄 구성원을 선택하세요 : ");

    for (Member entrustGuilder : guilders) {
      if (entrustGuilder.getPerNickname().equals(inputGuilderNick)) {
        System.out.println();
        System.out.printf(
            " '%s'님에게 조장 권한을 위임하시겠습니까?", entrustGuilder.getPerNickname());

        String input = Prompt.inputString(" (네 / 아니오) ");

        if (!input.equalsIgnoreCase("네")) {
          System.out.println();
          System.out.println(" >> 다시 진행해 주세요.");
          return;
        } 

        System.out.printf(" >> '%s'님이 조장이 되었습니다.\n", entrustGuilder.getPerNickname());

        System.out.println();
        String inputGuilder = Prompt.inputString(
            " >> 구성원으로 다시 돌아가시겠습니까? (네 / 아니오) ");

        if (!inputGuilder.equalsIgnoreCase("네")) {
          try {
            //studyDao.deleteGuilder(myStudy.getStudyNo(), entrustGuilder.getPerNo());
            studyDao.updateOwner(myStudy.getStudyNo(), entrustGuilder.getPerNo());
            studyDao.updateGuilderExpulsion(myStudy.getStudyNo(), member.getPerNo());
            sqlSession.commit();
          } catch (Exception e) {
            System.out.println(" 해당 스터디 탈퇴 오류!");
            sqlSession.rollback();
          }
          System.out.println();
          System.out.println(" >> 해당 스터디에서 탈퇴되었습니다.");
          return;
        }
        try { 
          studyDao.updateOwner(myStudy.getStudyNo(), entrustGuilder.getPerNo());
          sqlSession.commit();
        } catch (Exception e) {
          System.out.println(" 구성원 등록 오류!");
          sqlSession.rollback();
        }
        System.out.println();
        System.out.println(" >> 구성원이 되었습니다.");
        return;
      }
    }
    System.out.println();
    System.out.println(" >> 구성원의 닉네임을 다시 입력하세요.");

  }
}
