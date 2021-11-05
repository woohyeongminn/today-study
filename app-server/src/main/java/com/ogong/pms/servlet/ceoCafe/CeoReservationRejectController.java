package com.ogong.pms.servlet.ceoCafe;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.ibatis.session.SqlSession;
import com.ogong.pms.dao.CafeDao;
import com.ogong.pms.dao.CafeReservationDao;

@WebServlet("/ceomember/cafe/reser/reject")
public class CeoReservationRejectController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  CafeDao cafeDao;
  CafeReservationDao cafeReservationDao;
  SqlSession sqlSession;

  @Override
  public void init(ServletConfig config) throws ServletException {
    ServletContext 웹애플리케이션공용저장소 = config.getServletContext();
    cafeDao = (CafeDao) 웹애플리케이션공용저장소.getAttribute("cafeDao");
    cafeReservationDao = (CafeReservationDao) 웹애플리케이션공용저장소.getAttribute("cafeReservationDao");
    sqlSession = (SqlSession) 웹애플리케이션공용저장소.getAttribute("sqlSession");
  }


  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {

      int no = Integer.parseInt(request.getParameter("no"));  // 예약번호
      int ceoNo = Integer.parseInt(request.getParameter("ceono"));  // 기업회원 번호


      cafeReservationDao.deleteReservation(no, 5);
      sqlSession.commit();

      response.sendRedirect("list?ceono=" + ceoNo );


      //    } else if (reserDate.toLocalDate().compareTo(today.toLocalDate()) == 0) {
      //      System.out.println(" >> 당일 예약은 거절 불가능합니다.");
      //      return;
      //    } else {
      //      System.out.println(" >> 지난 예약은 선택할 수 없습니다.");
      //      return;
      //    }

    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", e);
      request.getRequestDispatcher("/Error.jsp").forward(request, response);
    }
  }
}