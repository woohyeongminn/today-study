package com.ogong.pms.web.study;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.ibatis.session.SqlSession;
import com.ogong.pms.dao.MemberDao;
import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;

@WebServlet("/study/add")
public class StudyAddController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  MemberDao memberDao;
  StudyDao studyDao;
  SqlSession sqlSession;

  @Override
  public void init() {
    ServletContext 웹애플리케이션공용저장소 = getServletContext();
    sqlSession = (SqlSession) 웹애플리케이션공용저장소.getAttribute("sqlSession");
    memberDao = (MemberDao) 웹애플리케이션공용저장소.getAttribute("memberDao");
    studyDao = (StudyDao) 웹애플리케이션공용저장소.getAttribute("studyDao");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {
      Study study = new Study();

      study.setStudyTitle(request.getParameter("studytitle"));
      study.setOwner((Member) request.getSession().getAttribute("loginUser"));
      study.setSubjectNo(Integer.parseInt(request.getParameter("subjectno")));
      study.setArea(request.getParameter("area"));
      study.setNumberOfPeple(Integer.parseInt(request.getParameter("numberofpeple")));
      study.setFaceNo(Integer.parseInt(request.getParameter("faceno")));
      study.setIntroduction(request.getParameter("introduction"));
      System.out.println(study);

      studyDao.insert(study);
      studyDao.insertGuilder(study.getStudyNo(), ((Member) request.getSession().getAttribute("loginUser")).getPerNo());
      studyDao.updateGuilder(study.getStudyNo(), ((Member) request.getSession().getAttribute("loginUser")).getPerNo());
      sqlSession.commit();

      // request.setAttribute("member", member);
      response.sendRedirect("list");
      // request.getRequestDispatcher("StudyAdd.jsp").forward(request, response);

    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", e);
      request.getRequestDispatcher("/Error.jsp").forward(request, response);
    }
  }
}