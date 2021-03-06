package com.ogong.pms.web.admin;

import java.util.Collection;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ogong.pms.dao.MemberDao;
import com.ogong.pms.domain.Member;

@Controller
public class AdminPerMemberController {

  @Autowired SqlSessionFactory sqlSessionFactory;
  @Autowired MemberDao memberDao;

  @GetMapping("/admin/permemberlist")
  public ModelAndView list() throws Exception {

    Collection<Member> perMemberList = memberDao.findAll();

    ModelAndView mv = new ModelAndView();

    mv.addObject("perMemberList", perMemberList);
    mv.addObject("pageTitle", "π κ°μΈ νμ");
    mv.addObject("contentUrl", "admin/AdminPerMemberList.jsp");
    mv.setViewName("template1");
    return mv;
  }

  @GetMapping("/admin/permemberdetail")
  public ModelAndView detail(int no) throws Exception {

    Member perMember = memberDao.findByNo(no);

    if (perMember == null) {
      throw new Exception("ν΄λΉ λ²νΈμ νμμ΄ μμ΅λλ€.");
    }

    ModelAndView mv = new ModelAndView();
    mv.addObject("pageTitle", "π κ°μΈ νμ");
    mv.addObject("perMember", perMember);
    mv.addObject("contentUrl", "admin/AdminPerMemberDetail.jsp");
    mv.setViewName("template1");
    return mv;
  }

  @GetMapping("/admin/permemberdelete")
  protected ModelAndView delete(int no) throws Exception { 

    Member perMember = memberDao.findByNo(no);

    if (perMember == null) {
      throw new Exception("ν΄λΉ λ²νΈμ νμμ΄ μμ΅λλ€.");
    }

    perMember.setPerName("Deleted Member("+ perMember.getPerName() +")");
    perMember.setPerNickname("Deleted Member("+ perMember.getPerNickname() +")");
    perMember.setPerEmail("Deleted Email");
    perMember.setPerPassword("Deleted Password");
    perMember.setPerPhoto("perProfile");
    perMember.setPerTel("Deleted Tel");
    perMember.setPerStatus(Member.PER);
    perMember.setActive(Member.OUTUSER);

    memberDao.updateActive(perMember);
    sqlSessionFactory.openSession().commit();

    ModelAndView mv = new ModelAndView();
    mv.setViewName("redirect:permemberlist");
    return mv;
  }
}






