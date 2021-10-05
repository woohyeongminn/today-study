package com.ogong.pms.handler.myStudy.calender;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ogong.pms.domain.Calender;
import com.ogong.pms.domain.Study;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;

public class MyStudyCalenderListHandler implements Command {

  RequestAgent requestAgent;

  public MyStudyCalenderListHandler(RequestAgent requestAgent) {
    this.requestAgent = requestAgent;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 일정 목록");
    System.out.println();

    int no = (int) request.getAttribute("inputNo");

    HashMap<String, String> params = new HashMap<>();
    params.put("studyNo", String.valueOf(no));

    requestAgent.request("study.selectOne", params);

    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
      System.out.println("스터디 조회 실패");
      return;
    }

    Study study = requestAgent.getObject(Study.class);

    Date now = new Date(System.currentTimeMillis());
    System.out.printf("      << %d월 >>\n", now.getMonth() + 1 );
    System.out.println();

    List<Calender> tempCal = new ArrayList<>();
    for (Calender calender : study.getMyStudyCalender()) {
      if (now.getMonth() + 1 == calender.getMonth()) {
        System.out.printf(
            " 중요도<%s>\n[ %d월 %d일 %s요일 ]\n %s\n",
            calender.getImportanceCalender(),
            calender.getMonth(), 
            calender.getDay(),
            calender.getDayOftheWeek(),
            calender.getCalenderContent());
        tempCal.add(calender);
        System.out.println();
      }
    }

    boolean selectBack = selectCategory(no, tempCal, request);
    if (!selectBack) {
      return;
    }

    int selectMonth; 
    while (true) {
      selectMonth = Prompt.inputInt(" >> 다른 '월' 일정 보기 : ");
      System.out.println();
      if (selectMonth > 12 || selectMonth < 1) {
        System.out.println(" >> 정확한 '월'을 입력해 주세요.\n");
        continue;
      }
      System.out.printf("      << %d월 >>\n", selectMonth);
      System.out.println();

      List<Calender> selecMonthCal = new ArrayList<>();
      for (Calender calender : study.getMyStudyCalender()) {
        if (selectMonth == calender.getMonth()) {
          selecMonthCal.add(calender);
          System.out.printf(
              " 중요도<%s> \n[ %d월 %d일 %s요일 ]\n %s\n",
              calender.getImportanceCalender(),
              calender.getMonth(), 
              calender.getDay(),
              calender.getDayOftheWeek(),
              calender.getCalenderContent());
          System.out.println();
        }
      }

      selectBack = selectCategory(no, selecMonthCal, request);
      if (!selectBack) {
        return;
      }

    }
  }

  private boolean selectCategory(int no, List<Calender> calenderList, CommandRequest request) {

    request.setAttribute("inputNo", no);

    if (calenderList.isEmpty()) {
      System.out.println();
      System.out.println(" 등록된 일정이 없습니다.");
      System.out.println("---------------------");
      System.out.println("1. 다른 월");
      System.out.println("2. 등록");
      System.out.println("3. 취소");
      int selectNo = Prompt.inputInt("선택> ");
      System.out.println();
      switch (selectNo) {
        case 1 : return true;
        case 2 : request.getRequestDispatcher("/myStudy/calenderAdd"); break;
        case 3 : return false;
        default : return false;
      }
    }

    if (!calenderList.isEmpty()) {
      System.out.println("---------------------");
      System.out.println("1. 상세");
      System.out.println("2. 다른 월");
      System.out.println("3. 등록");
      System.out.println("4. 취소");
      int selectNo = Prompt.inputInt("선택> ");
      System.out.println();
      switch (selectNo) {
        case 1 : request.getRequestDispatcher("/myStudy/calenderDetail"); break;
        case 2 : return true;
        case 3 : request.getRequestDispatcher("/myStudy/calenderAdd"); break;
        case 4 : return false;
        default : return false;
      }
    }
    return true;
  }

}