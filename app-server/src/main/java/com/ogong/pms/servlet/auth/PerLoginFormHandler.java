package com.ogong.pms.servlet.auth;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login/form")
public class PerLoginFormHandler extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("   <title>로그인</title>");
    out.println("</head>");
    out.println("<style>");
    out.println(" label {");
    out.println("    margin-right: 5px;");
    out.println("    text-align: right;");
    out.println("    display: inline-block;");
    out.println("    width: 60px;");
    out.println("   }");
    out.println("</style>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>[로그인]</h1>");

    out.println("<form action='../member/login'>");
    out.println(
        "<label for='f-email'>이메일</label> <input id='f-email' type='email' name='email'><br>");
    out.println(
        "<label for='f-password'>암호</label> <input id='f-password' type='password' name='password'><br>");


    out.println("<button>로그인</button><br>"); 
    out.println("</form>");

    out.println("</body>");
    out.println("</html>");
  } 
}