package com.ogong.pms.handler.study;

import java.util.List;
import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class StudySearchHandler implements Command {

  StudyDao studyDao;

  public StudySearchHandler(StudyDao studyDao) {
    this.studyDao = studyDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 스터디 검색");
    System.out.println();

    System.out.println(" 지역 / 분야 / 스터디명으로 검색할 수 있습니다.");
    String input = Prompt.inputString(" 검색어 : ");
    System.out.println();

    int count = 0;

    List<Study> studyList = studyDao.findByKeyword(input);

    if (studyList.isEmpty()) {
      System.out.println("해당 검색어의 스터디가 존재하지 않습니다.");
      return;
    }
    for (Study searchStudy : studyList) {

      System.out.printf(" \n (%s)\n", searchStudy.getStudyNo());
      System.out.printf(" [%s]\n", searchStudy.getStudyTitle());
      System.out.printf(" >> 조장 : %s\n", searchStudy.getOwner().getPerNickname());
      System.out.printf(" >> 분야 : %s\n", searchStudy.getSubjectName());
      System.out.printf(" >> 지역 : %s\n", searchStudy.getArea());
      System.out.printf(
          " >> 인원수 : %s/%s명\n", searchStudy.getCountMember(), searchStudy.getNumberOfPeple());
      System.out.printf(" >> 대면 : %s\n", searchStudy.getFaceName());
      System.out.printf(" >> 소개글 : %s\n", searchStudy.getIntroduction());
      count++;
    }

    if (count == 0) {
      System.out.println(" >> 검색어를 다시 입력해 주세요.");
      return;
    }
  }
}

