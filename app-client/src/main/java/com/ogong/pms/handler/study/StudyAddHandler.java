package com.ogong.pms.handler.study;

import java.util.ArrayList;
import org.apache.ibatis.session.SqlSession;
import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class StudyAddHandler implements Command {

  StudyDao studyDao;
  SqlSession sqlSession;

  public StudyAddHandler(StudyDao studyDao, SqlSession sqlSession) {
    this.studyDao = studyDao;
    this.sqlSession = sqlSession;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 스터디 등록");
    System.out.println();

    Study study = new Study();
    Member owner = AuthPerMemberLoginHandler.getLoginUser();

    // 스터디명
    String studyTitle;
    while (true) {
      studyTitle = Prompt.inputString(" 스터디명 : ");
      if (studyTitle.length() == 0) {
        System.out.println(" >> 한 글자 이상 입력해 주세요.");
        System.out.println();
        continue;
      }
      break;
    }
    study.setStudyTitle(studyTitle);

    // 분야
    System.out.println();
    System.out.println(" [ Category ]");
    System.out.println(" 1. 어학");
    System.out.println(" 2. 자격증");
    System.out.println(" 3. 취업");
    System.out.println(" 4. IT");
    System.out.println(" 5. 예체능");
    System.out.println(" 6. 기타");
    System.out.println();


    while (true) {
      try {
        int subjectNo =Prompt.inputInt(" 분야 : ");
        switch (subjectNo) {
          case 1 : study.setSubjectNo(1); break;
          case 2 : study.setSubjectNo(2); break;
          case 3 : study.setSubjectNo(3); break;
          case 4 : study.setSubjectNo(4); break;
          case 5 : study.setSubjectNo(5); break;
          case 6 : study.setSubjectNo(6); break;
          default : System.out.println(" >> 다시 선택하세요.\n"); continue;
        }

      } catch (NumberFormatException e) {
        System.out.println(" >> 숫자만 입력하세요.\n");
        continue;
      }
      break;
    }

    // 지역
    String area;
    while (true) {
      System.out.println();
      area = Prompt.inputString(" 지역 : ");
      if (area.contains("시") || area.contains("구") || area.contains("도")) {
        study.setArea(area);
        break;
      } System.out.println(" >> @@시 / @@도 / @@구 등을 입력해 주세요.");
    }

    // 인원수
    int nop = 0;
    System.out.println();
    while (true) {
      try {
        nop = Prompt.inputInt(" 인원수(1~30) : ");
        if ((nop == 0) || (nop > 30)) {
          System.out.println(" >> 인원수는 1명 이상 30명 이하로만 입력 가능합니다.");
          System.out.println();
          continue;
        }
      } catch (NumberFormatException e) {
        System.out.println(" >> 숫자만 입력하세요.");
        System.out.println();
        continue;
      }
      break;
    }
    study.setNumberOfPeple(nop);


    System.out.println();
    System.out.println(" [ 대면상태 ]");
    System.out.println(" 1. 대면");
    System.out.println(" 2. 비대면");
    System.out.println(" 3. 대면/비대면");

    while (true) {
      try {
        int subjectNo =Prompt.inputInt(" 대면 : ");
        switch (subjectNo) {
          case 1 : study.setFaceNo(1); break;
          case 2 : study.setFaceNo(2); break;
          case 3 : study.setFaceNo(3); break;
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
      System.out.println();
      introduction = Prompt.inputString(" 소개글 : ");
      if (introduction.length() == 0) {
        System.out.println(" >> 한 글자 이상 입력해 주세요.");
        continue;
      }
      break;
    }
    study.setIntroduction(introduction);
    study.setOwner(owner);

    // 작성자,구성원,캘린더,자유게시판
    study.setOwner(AuthPerMemberLoginHandler.getLoginUser());
    study.setMembers(new ArrayList<>());
    study.setWatingMember(new ArrayList<>());
    study.setMyStudyCalender(new ArrayList<>());
    study.setMyStudyFreeBoard(new ArrayList<>());
    study.setMyStudyToDo(new ArrayList<>());
    study.setBookMarkMember(new ArrayList<>());

    System.out.println();
    String input = Prompt.inputString(" 등록하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 등록이 취소되었습니다.");

      return;
    }
    try {
      studyDao.insert(study);
      studyDao.insertGuilder(study.getStudyNo(), owner.getPerNo());
      studyDao.updateGuilder(study.getStudyNo(), owner.getPerNo());
      sqlSession.commit();
      System.out.println(" >> 스터디가 등록되었습니다.");
    } catch (Exception e) {
      System.out.println(" 스터디 등록 오류!");
      sqlSession.rollback();
    }
  }
}