package com.ogong.pms.web.admin;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.ibatis.session.SqlSession;
import com.ogong.pms.dao.AdminDao;
import com.ogong.pms.domain.Admin;

@WebServlet("/admin/update")
public class AdminUpdateController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  AdminDao adminDao;
  SqlSession sqlSession;

  @Override
  public void init() throws ServletException {
    ServletContext 웹애플리케이션공용저장소 = getServletContext();
    sqlSession = (SqlSession) 웹애플리케이션공용저장소.getAttribute("sqlSession");
    adminDao = (AdminDao) 웹애플리케이션공용저장소.getAttribute("adminDao");
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {
      Admin loginAdmin = (Admin) request.getSession().getAttribute("loginAdmin");
      Admin admin = adminDao.findByAdminNo(loginAdmin.getMasterNo());

      if (admin == null) {
        throw new Exception(" >> 다시 선택해 주세요.");
      } 

      admin.setMasterNickname(request.getParameter("nickName"));
      admin.setMasterEmail(request.getParameter("email"));
      admin.setMasterPassword(request.getParameter("password"));

      adminDao.updateNickname(admin);
      adminDao.updateEmail(admin);
      adminDao.updatePassword(admin);

      sqlSession.commit();

      response.sendRedirect("logout");

    } catch (Exception e) {
      request.setAttribute("error", e);
      request.getRequestDispatcher("/Error.jsp").forward(request, response);
    }
  }
}