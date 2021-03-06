package com.ogong.pms.web.study;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;

@Controller
public class StudyController {

  @Autowired
  SqlSessionFactory sqlSessionFactory;
  @Autowired
  StudyDao studyDao;

  /* 스터디 등록 폼 */
  @GetMapping("/study/form")
  public ModelAndView form() {
    ModelAndView mv = new ModelAndView();

    mv.addObject("pageTitle", "스터디 등록");
    mv.addObject("contentUrl", "study/StudyAddForm.jsp");
    mv.setViewName("template1");

    return mv;
  }

  /* 스터디 등록 */
  @PostMapping("/study/add")
  public ModelAndView add(Study study, HttpSession session) throws Exception {
    study.setOwner((Member) session.getAttribute("loginUser"));
    System.out.println(study);

    study.setMembers(new ArrayList<>());
    study.setWaitingMember(new ArrayList<>());
    study.setMyStudyCalender(new ArrayList<>());
    study.setMyStudyFreeBoard(new ArrayList<>());
    study.setMyStudyToDo(new ArrayList<>());
    study.setBookMarkMember(new ArrayList<>());

    studyDao.insert(study);
    studyDao.insertOwner(study.getStudyNo(),
        ((Member) session.getAttribute("loginUser")).getPerNo(), 2);
    studyDao.updateGuilder(study.getStudyNo(),
        ((Member) session.getAttribute("loginUser")).getPerNo());
    sqlSessionFactory.openSession().commit();

    ModelAndView mv = new ModelAndView();

    mv.setViewName("redirect:detail?studyno=" + study.getStudyNo());

    return mv;
  }

  /* 전체 스터디 목록 */
  @GetMapping("/study/list")
  public ModelAndView list() throws Exception {
    Collection<Study> studyList = studyDao.findAll();

    ModelAndView mv = new ModelAndView();

    mv.addObject("studyList", studyList);
    mv.addObject("pageTitle", "스터디 목록");
    mv.addObject("contentUrl", "study/StudyList.jsp");
    mv.setViewName("template1");

    return mv;
  }

  /* 진행 스터디 목록 */
  @GetMapping("/study/list/ing")
  public ModelAndView ingList() throws Exception {
    Collection<Study> studyIngList = studyDao.findAllIng();

    ModelAndView mv = new ModelAndView();

    mv.addObject("studyIngList", studyIngList);
    mv.addObject("pageTitle", "스터디 목록");
    mv.addObject("contentUrl", "study/StudyIngList.jsp");
    mv.setViewName("template1");

    return mv;
  }

  /* 종료 스터디 목록 */
  @GetMapping("/study/list/end")
  public ModelAndView endList() throws Exception {
    Collection<Study> studyEndList = studyDao.findAllEnd();

    ModelAndView mv = new ModelAndView();

    mv.addObject("studyEndList", studyEndList);
    mv.addObject("pageTitle", "스터디 목록");
    mv.addObject("contentUrl", "study/StudyEndList.jsp");
    mv.setViewName("template1");

    return mv;
  }

  /* 스터디 상세 */
  @GetMapping("/study/detail")
  public ModelAndView detail(int studyno, HttpSession session) throws Exception {
    Study study = studyDao.findByNo(studyno);

    if (study == null) {
      throw new Exception("해당 번호의 스터디가 없습니다.");
    }

    ModelAndView mv = new ModelAndView();

    if (((Member) session.getAttribute("loginUser")) != null) {
      int myBookmark =
          studyDao.myBookmark(studyno, ((Member) session.getAttribute("loginUser")).getPerNo());
      System.out.println(myBookmark);
      mv.addObject("myBookmark", myBookmark);

      Integer guilder = null;
      guilder = studyDao.findGuilderStatusByNo(studyno, ((Member) session.getAttribute("loginUser")).getPerNo());

      if (guilder == null) {
        mv.addObject("guilder", "false");

      } else if (guilder == 1) {
        mv.addObject("guilder", "waitingGuilder");

      } else if (guilder == 2) {
        mv.addObject("guilder", "guilder");
      }

    } else {
      int myBookmark = studyDao.myBookmark(studyno, 0);
      mv.addObject("myBookmark", myBookmark);
    }

    mv.addObject("study", study);
    mv.addObject("pageTitle", "스터디 상세");
    mv.addObject("contentUrl", "study/StudyDetail.jsp");
    mv.setViewName("template1");

    return mv;
  }

  /* 스터디 수정 폼 */
  @GetMapping("/study/updateform")
  public ModelAndView updateform(int studyno) throws Exception {
    Study study = studyDao.findByNo(studyno);

    ModelAndView mv = new ModelAndView();

    mv.addObject("study", study);
    mv.addObject("pageTitle", "스터디 수정");
    mv.addObject("contentUrl", "study/StudyUpdateForm.jsp");
    mv.setViewName("template1");

    return mv;
  }

  /* 스터디 수정 */
  @PostMapping("/study/update")
  public ModelAndView update(Study study) throws Exception {
    Study oldStudy = studyDao.findByNo(study.getStudyNo());

    if (oldStudy == null) {
      throw new Exception("해당 번호의 스터디가 없습니다.");
    }

    studyDao.updateStudyTitle(study);
    studyDao.updateNumberOfPeple(study);
    studyDao.updateFaceNo(study);
    studyDao.updateIntroduction(study);
    studyDao.updateStudyStatus(study);
    sqlSessionFactory.openSession().commit();

    ModelAndView mv = new ModelAndView();

    mv.addObject("study", study);
    mv.setViewName("redirect:detail?studyno=" + study.getStudyNo());

    return mv;
  }

  /* 스터디 삭제 */
  @GetMapping("/study/delete")
  public ModelAndView delete(int studyno) throws Exception {
    Study study = studyDao.findByNo(studyno);

    studyDao.updateStatusDelete(study);
    sqlSessionFactory.openSession().commit();

    ModelAndView mv = new ModelAndView();

    mv.addObject("study", study);
    mv.setViewName("redirect:list");

    return mv;
  }

  /* 스터디 참여 */
  @GetMapping("/study/join")
  public ModelAndView join(int studyno, HttpSession session) throws Exception {
    Study study = studyDao.findByNo(studyno);

    List<Member> waitingGuilder = studyDao.findByWaitingGuilderAll(study.getStudyNo());
    study.setWaitingMember(waitingGuilder);

    List<Member> guilders = studyDao.findByGuildersAll(study.getStudyNo());
    study.setMembers(guilders);

    study.getWaitingMember().add((Member) session.getAttribute("loginUser"));
    studyDao.insertGuilder(study.getStudyNo(),
        ((Member) session.getAttribute("loginUser")).getPerNo(), 1);
    sqlSessionFactory.openSession().commit();

    ModelAndView mv = new ModelAndView();

    mv.addObject("study", study);
    mv.setViewName("redirect:detail?studyno=" + study.getStudyNo());

    return mv;
  }

  /* 스터디 검색 */
  @GetMapping("/study/search")
  public ModelAndView search(String where, String keyword) throws Exception {
    if (where.equals("1")) {
      where = "ss.name";

    } else if (where.equals("2")) {
      where = "s.name";

    } else if (where.equals("3")) {
      where = "s.area";
    }

    List<Study> studyList = studyDao.findByKeyword(where, keyword);

    ModelAndView mv = new ModelAndView();

    mv.addObject("studyList", studyList);
    mv.addObject("pageTitle", "스터디 목록");
    mv.addObject("contentUrl", "study/StudyList.jsp");
    mv.setViewName("template1");

    return mv;
  }
}
