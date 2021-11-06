package com.ogong.pms.servlet.myStudy.guilder;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ogong.pms.dao.MemberDao;
import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;


@WebServlet("/mystudy/guilder/list")
public class GuilderListController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  StudyDao studyDao;
  MemberDao memberDao;

  @Override
  public void init(ServletConfig config) throws ServletException {
    ServletContext 웹애플리케이션공용저장소 = config.getServletContext();
    memberDao = (MemberDao) 웹애플리케이션공용저장소.getAttribute("memberDao");
    studyDao = (StudyDao) 웹애플리케이션공용저장소.getAttribute("studyDao");
  }


  // 스터디 구성원 목록
  @Override
  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {

      int perNo = Integer.parseInt(request.getParameter("perNo"));      //로그인 유저
      Member member = memberDao.findByNo(perNo);

      int studyNo = Integer.parseInt(request.getParameter("studyNo"));
      Study myStudy = studyDao.findByMyNo(studyNo, perNo);


      List<Member> guilders = studyDao.findByGuildersAll(myStudy.getStudyNo());
      if (!guilders.isEmpty()) {
        myStudy.setWatingMember(guilders);
        request.setAttribute("guildersList", guilders);
      }

      List<Member> waitingGuilder = studyDao.findByWaitingGuilderAll(myStudy.getStudyNo());
      if (!waitingGuilder.isEmpty()) {
        myStudy.setWatingMember(waitingGuilder);
        request.setAttribute("waitingGuilderList", waitingGuilder);
      }

      request.setAttribute("member", member);
      request.setAttribute("study", myStudy);
      request.getRequestDispatcher("/myStudy/guilder/GuilderList.jsp").forward(request, response);

    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", e);
      request.getRequestDispatcher("/Error.jsp").forward(request, response);
    }
  }

}