package com.ogong.pms.web.auth;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ogong.pms.dao.AdminDao;
import com.ogong.pms.domain.Admin;

@Controller
public class AuthAdminController {

  @Autowired AdminDao adminDao;
  @Autowired ServletContext sc;

  @GetMapping("/admin/form")
  public ModelAndView adminloginform() {

    ModelAndView mv = new ModelAndView();
    mv.addObject("pageTitle", "๐ ์ค๋์ ๊ณต๋ถ ๋ก๊ทธ์ธ");
    mv.addObject("contentUrl", "admin/AdminLoginForm.jsp");
    mv.setViewName("template1");
    return mv;
  } 

  @PostMapping("/admin/login")
  public ModelAndView adminlogin(String email, String password, String saveEmail, HttpSession session, HttpServletResponse response) throws Exception {

    Cookie cookie = null;
    if (saveEmail != null) {
      cookie = new Cookie("email", email);
      cookie.setMaxAge(60 * 60 * 24 * 7); // ์ผ์ฃผ์ผ (์ด * ๋ถ * ์๊ฐ * ์ผ)

    } else {
      cookie = new Cookie("email", "");
      cookie.setMaxAge(0); // ์ ํจ ๊ธฐ๊ฐ์ 0์ผ๋ก ์ค์ ํ๋ฉด ์น ๋ธ๋ผ์ฐ์ ๊ฐ ๋ฐ๋ ์ฆ์ ๋ฌดํจํ ์ฟ ํค๊ฐ ๋๋ค.
    }
    response.addCookie(cookie);

    Admin admin = adminDao.findByEmailAndPassword(email, password);

    ModelAndView mv = new ModelAndView();

    if (admin != null) {
      session.setAttribute("loginAdmin", admin);
      mv.setViewName("redirect:detail");
    }

    else {
      mv.addObject("refresh", "2;url=form");
      mv.addObject("pageTitle", "๋ก๊ทธ์ธ ์ค๋ฅ!");
      mv.addObject("contentUrl", "admin/AdminLoginFail.jsp");
      mv.setViewName("template1");
    }
    return mv;
  }

  @GetMapping("/admin/logout")
  public ModelAndView adminlogout(HttpSession session) throws Exception {

    session.invalidate();
    ModelAndView mv = new ModelAndView();
    mv.setViewName("redirect:../index");
    return mv;
  }
}
