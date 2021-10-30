package com.ogong.pms.servlet.ceoMember;

import java.io.IOException;
import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import com.ogong.pms.dao.CeoMemberDao;
import com.ogong.pms.domain.CeoMember;

@WebServlet("/ceomember/updateform")
public class CeoUpdateFormController extends GenericServlet  {
  private static final long serialVersionUID = 1L;

  CeoMemberDao ceoMemberDao;

  @Override
  public void init(ServletConfig config) throws ServletException {
    ServletContext 웹애플리케이션공용저장소 = config.getServletContext();
    ceoMemberDao = (CeoMemberDao) 웹애플리케이션공용저장소.getAttribute("ceoMemberDao");
  }

  // 기업회원 개인정보 수정은 이름,이메일,비밀번호만 가능
  @Override
  public void service(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {

    System.out.println();
    System.out.println("▶ 기업 프로필 수정"); 
    System.out.println();

    try {
      int no = Integer.parseInt(request.getParameter("no"));
      //int no = 5;
      CeoMember ceoMember = ceoMemberDao.findByNo(no);

      if (ceoMember == null) {
        throw new Exception("해당 번호의 회원이 없습니다.");
      } 

      request.setAttribute("ceoMember", ceoMember);
      request.getRequestDispatcher("/ceoMember/CeoMemberUpdateForm.jsp").forward(request, response);


    } catch (Exception e) {
      request.setAttribute("error", e);
      request.getRequestDispatcher("/Error.jsp").forward(request, response);
    }
  }
}






