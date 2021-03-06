package com.ogong.pms.handler.myStudy;

import org.apache.ibatis.session.SqlSession;
import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class MyStudyUpdateHandler implements Command {

  StudyDao studyDao;
  SqlSession sqlSession;

  public MyStudyUpdateHandler(StudyDao studyDao, SqlSession sqlSession) {
    this.studyDao = studyDao;
    this.sqlSession = sqlSession;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 스터디 수정");
    System.out.println();

    int inputNo = (int) request.getAttribute("inputNo");

    Study myStudy = studyDao.findByNo(inputNo);

    if (myStudy.getOwner().getPerNo() != AuthPerMemberLoginHandler.getLoginUser().getPerNo()) {
      System.out.println(" >> 수정 권한이 없습니다.");
      return;
    }

    // 스터디명
    String studyTitle;
    while (true) {
      studyTitle = Prompt.inputString(" 스터디명(" + myStudy.getStudyTitle()  + ") : ");
      if (studyTitle.length() == 0) {
        System.out.println("한 글자 이상 입력해주세요.");
        continue;
      }
      break;
    }
    myStudy.setStudyTitle(studyTitle);

    // 인원수
    int nop = 0;
    while (true) {
      try {
        nop = Prompt.inputInt(" 인원수(" + myStudy.getNumberOfPeple() + ") : ");
        if (nop < myStudy.getNumberOfPeple()) {
          System.out.println(" >> 현재 참여 중인 인원보다 적게 수정할 수 없습니다.");
          continue;
        }
      }catch (NumberFormatException e) {
        System.out.println("숫자만 입력하세요.");
        continue;
      }
      break;
    }
    myStudy.setNumberOfPeple(nop);

    System.out.println();
    System.out.println(" [ 대면상태 ]");
    System.out.println(" 1. 대면");
    System.out.println(" 2. 비대면");
    System.out.println(" 3. 대면/비대면");

    while (true) {
      try {
        int subjectNo =Prompt.inputInt(" 대면 : ");
        switch (subjectNo) {
          case 1 : myStudy.setFaceNo(1); break;
          case 2 : myStudy.setFaceNo(2); break;
          case 3 : myStudy.setFaceNo(3); break;
          default : System.out.println(" >> 다시 선택하세요.\n"); continue;
        }
      } catch (NumberFormatException e) {
        System.out.println(" >> 숫자만 입력하세요.");
        System.out.println();
        continue;
      }
      break;
    }

    // 소개글
    String introduction;
    while (true) {
      introduction = Prompt.inputString(" 소개글(" + myStudy.getIntroduction() + ") : ");
      if (introduction.length() == 0) {
        System.out.println("한 글자 이상 입력해주세요.");
        continue;
      }
      break;
    }
    myStudy.setIntroduction(introduction);

    System.out.println();
    String input = Prompt.inputString(" 정말 수정하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 스터디 수정을 취소하였습니다.");
      return;
    }

    try {
      studyDao.update(myStudy);
      sqlSession.commit();
    } catch (Exception e) {
      System.out.println(" 스터디 수정 오류!");
      sqlSession.rollback();
    }
    System.out.println(" >> 스터디가 수정되었습니다.");
  }
}