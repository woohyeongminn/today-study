package com.ogong.pms.servlet.myStudy.comment;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ogong.pms.dao.CommentDao;
import com.ogong.pms.dao.FreeBoardDao;
import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Comment;
import com.ogong.pms.domain.FreeBoard;
import com.ogong.pms.domain.Study;

@WebServlet("/comment/updateform")
public class CommentUpdateFormController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  StudyDao studyDao;
  FreeBoardDao freeBoardDao;
  CommentDao commentDao;

  @Override
  public void init(ServletConfig config) throws ServletException {
    ServletContext 웹애플리케이션공용저장소 = config.getServletContext();
    studyDao = (StudyDao) 웹애플리케이션공용저장소.getAttribute("studyDao");
    freeBoardDao = (FreeBoardDao) 웹애플리케이션공용저장소.getAttribute("freeBoardDao");
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {
      int studyNo = Integer.parseInt(request.getParameter("studyNo"));
      Study study = studyDao.findByNo(studyNo);

      int freeBoardNo = Integer.parseInt(request.getParameter("freeBoardNo"));
      FreeBoard freeBoard = freeBoardDao.findByNo(freeBoardNo, studyNo);

      List<Comment> commentList = commentDao.findAll(freeBoard.getFreeBoardNo());

      request.setAttribute("study", study);
      request.setAttribute("freeBoard", freeBoard);
      request.setAttribute("commentList", commentList);
      request.getRequestDispatcher("/myStudy/freeBoardDetail.jsp").forward(request, response);

    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", e);
      request.getRequestDispatcher("/Error.jsp").forward(request, response);
    }
  }
}