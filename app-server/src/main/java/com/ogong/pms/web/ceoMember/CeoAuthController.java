package com.ogong.pms.web.ceoMember;

import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ogong.pms.dao.CeoMemberDao;
import com.ogong.pms.domain.CeoMember;

@Controller
public class CeoAuthController {

  @Autowired CeoMemberDao ceoMemberDao;
  @Autowired ServletContext sc;

  // ๋ก๊ทธ์ธ ํผ
  @GetMapping("/ceomember/form")
  public ModelAndView ceoLoginForm() {

    ModelAndView mv = new ModelAndView();

    mv.addObject("pageTitle", "๐ ์ค๋์ ๊ณต๋ถ ๋ก๊ทธ์ธ");
    mv.addObject("contentUrl", "ceoMember/CeoMemberLoginForm.jsp");
    mv.setViewName("template1");

    return mv;
  }


  // ๋ก๊ทธ์ธ
  @PostMapping("/ceomember/login")
  public ModelAndView ceoLogin(String email, String password, String saveEmail, HttpServletResponse response, HttpSession session) throws Exception {
    Cookie cookie = null;

    if (saveEmail != null) {
      cookie = new Cookie("email", email);
      cookie.setMaxAge(60 * 60 * 24 * 7);
      // cookie.setPath(getServletContext().getContextPath() + "/ceomember");

    } else {
      cookie = new Cookie("email", "");
      cookie.setMaxAge(0);        // ์ ํจ๊ธฐ๊ฐ์ 0์ผ๋ก ์ค์ , ์น๋ธ๋ผ์ฐ์ ๊ฐ ๋ฐ๋ ์ฆ์ ๋ฌดํจํ ์ฟ ํค๊ฐ ๋๋ค
    }

    response.addCookie(cookie);

    CeoMember ceoMember = ceoMemberDao.findByEmailAndPassword(email, password);

    ModelAndView mv = new ModelAndView();

    response.setContentType("text/html; charset=utf-8");
    PrintWriter out = response.getWriter();

    if (ceoMember != null && ceoMember.getCeoStatus() == CeoMember.CEO) {
      if (ceoMember.getActive() == CeoMember.INUSER) {
        session.setAttribute("loginCeoUser", ceoMember);
        mv.setViewName("redirect:../index");
      }

    } else {
      //      mv.addObject("pageTitle", "ํด๋น ๊ณ์ ์ด ์กด์ฌํ์ง ์์ต๋๋ค.");
      //      mv.addObject("contentUrl", "auth/LoginFail.jsp");
      //      mv.setViewName("template1");
      out.println("<script> alert('์์ด๋ ๋๋ ๋น๋ฐ๋ฒํธ๊ฐ ํ๋ฆฝ๋๋ค.');");
      out.println("history.go(-1); </script>");
      out.close();
    }
    return mv;
  }


  // ๋ก๊ทธ์์
  @GetMapping("/ceomember/logout")
  public ModelAndView ceoLogout(HttpSession session) throws Exception {

    session.invalidate();
    ModelAndView mv = new ModelAndView();

    mv.setViewName("redirect:../index");
    return mv;
  }
}