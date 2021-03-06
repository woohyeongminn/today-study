package com.ogong.pms.web.admin;

import java.util.Collection;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ogong.pms.dao.NoticeDao;
import com.ogong.pms.domain.AdminNotice;
import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;

@Controller
public class AdminNoticeController {

  @Autowired SqlSessionFactory sqlSessionFactory;
  @Autowired NoticeDao noticeDao;
  @Autowired ServletContext sc;

  @GetMapping("/adminNotice/form")
  public ModelAndView noticeForm() {

    ModelAndView mv = new ModelAndView();
    mv.addObject("pageTitle", "π κ³΅μ§κ²μκΈ λ±λ‘");
    mv.addObject("contentUrl", "admin/NoticeAddForm.jsp");
    mv.setViewName("template1");
    return mv;
  } 

  @PostMapping("/adminNotice/add")
  public ModelAndView noticeAdd(AdminNotice adminNotice, Part filepath) throws Exception {

    if (filepath.getSize() > 0) {
      String filename = UUID.randomUUID().toString();
      filepath.write(sc.getRealPath("/upload/notice") + "/" + filename);
      adminNotice.setAdminNotiFile(filename); // μ€μ  μ μ₯ν νμΌλͺμ λ°μ΄ν° λ² μ΄μ€μ μ μ₯νλλ‘

      Thumbnails.of(sc.getRealPath("/upload/notice") + "/" + filename)
      .size(20, 20)
      .outputFormat("jpg")
      .crop(Positions.CENTER)
      .toFiles(new Rename() {
        @Override
        public String apply(String name, ThumbnailParameter param) {
          return name + "_20x20";
        }
      });
      Thumbnails.of(sc.getRealPath("/upload/notice") + "/" + filename)
      .size(100, 100)
      .outputFormat("jpg")
      .crop(Positions.CENTER)
      .toFiles(new Rename() {
        @Override
        public String apply(String name, ThumbnailParameter param) {
          return name + "_100x100";
        }
      });
    }

    noticeDao.insert(adminNotice);
    noticeDao.insertFilepath(adminNotice);
    sqlSessionFactory.openSession().commit();

    ModelAndView mv = new ModelAndView();
    mv.setViewName("redirect:list");
    return mv;
  }

  @GetMapping("/adminNotice/list")
  public ModelAndView noticeList() throws Exception {

    Collection<AdminNotice> adminNoticeList = noticeDao.findAll();

    ModelAndView mv = new ModelAndView();
    mv.addObject("adminNoticeList", adminNoticeList);
    mv.addObject("pageTitle", "π κ³΅μ§κ²μκΈ λͺ©λ‘");
    mv.addObject("contentUrl", "admin/NoticeList.jsp");
    mv.setViewName("template1");
    return mv;
  }

  @GetMapping("/adminNotice/detail")
  public ModelAndView noticeDetail(int no) throws Exception {

    AdminNotice adminNotice = noticeDao.findByNoticeNo(no);

    if (adminNotice == null) {
      throw new Exception(" >> ν΄λΉ λ²νΈμ κ³΅μ§κΈμ΄ μμ΅λλ€.");
    }

    ModelAndView mv = new ModelAndView();
    mv.addObject("adminNotice", adminNotice);
    mv.addObject("pageTitle", "π κ³΅μ§κ²μκΈ μμΈ");
    mv.addObject("contentUrl", "admin/NoticeDetail.jsp");
    mv.setViewName("template1");
    return mv;
  }

  @GetMapping("/adminNotice/Updateform")
  public ModelAndView noticeUpdateForm(int no) throws Exception {

    AdminNotice notice = noticeDao.findByNoticeNo(no);

    ModelAndView mv = new ModelAndView();
    mv.addObject("notice", notice);
    mv.addObject("pageTitle", "π κ³΅μ§κ²μκΈ λ³κ²½");
    mv.addObject("contentUrl", "admin/NoticeUpdateform.jsp");
    mv.setViewName("template1");
    return mv;
  }

  @PostMapping("/adminNotice/update")
  public ModelAndView noticeUpdate(AdminNotice adminNotice, Part filepath) throws Exception {

    AdminNotice notice = noticeDao.findByNoticeNo(adminNotice.getAdminNotiNo());

    if (notice == null) {
      throw new Exception(" >> ν΄λΉ λ²νΈμ κ³΅μ§κΈμ μ°Ύμ μ μμ΅λλ€.");
    } 

    adminNotice.setAdminNotiFile(notice.getAdminNotiFile());
    adminNotice.setAdminNotiRegisteredDate(notice.getAdminNotiRegisteredDate());

    if (filepath.getSize() > 0) {
      String filename = UUID.randomUUID().toString();
      filepath.write(sc.getRealPath("/upload/notice") + "/" + filename);
      notice.setAdminNotiFile(filename); // μ€μ  μ μ₯ν νμΌλͺμ λ°μ΄ν° λ² μ΄μ€μ μ μ₯νλλ‘

      Thumbnails.of(sc.getRealPath("/upload/notice") + "/" + filename)
      .size(20, 20)
      .outputFormat("jpg")
      .crop(Positions.CENTER)
      .toFiles(new Rename() {
        @Override
        public String apply(String name, ThumbnailParameter param) {
          return name + "_20x20";
        }
      });

      Thumbnails.of(sc.getRealPath("/upload/notice") + "/" + filename)
      .size(100, 100)
      .outputFormat("jpg")
      .crop(Positions.CENTER)
      .toFiles(new Rename() {
        @Override
        public String apply(String name, ThumbnailParameter param) {
          return name + "_100x100";
        }
      });

      adminNotice.setAdminNotiFile(filename);
    }

    noticeDao.updateTitle(adminNotice);
    noticeDao.updateContent(adminNotice);
    noticeDao.deletenoticefile(notice.getAdminNotiNo());
    noticeDao.insertFilepath(adminNotice);
    sqlSessionFactory.openSession().commit();

    ModelAndView mv = new ModelAndView();
    mv.setViewName("redirect:list");
    return mv;
  }

  @GetMapping("/adminNotice/delete")
  public ModelAndView noticeDelete(int no) throws Exception {

    AdminNotice notice = noticeDao.findByNoticeNo(no);

    if (notice == null) {
      throw new Exception (" >> ν΄λΉ λ²νΈμ κ³΅μ§κΈμ΄ μμ΅λλ€.");
    }

    noticeDao.deletenoticefile(no);
    noticeDao.delete(no);
    sqlSessionFactory.openSession().commit();

    ModelAndView mv = new ModelAndView();
    mv.setViewName("redirect:list");
    return mv;
  }

  // μ μ μ©
  @GetMapping("/adminNotice/userlist")
  public ModelAndView noticeUserList() throws Exception {

    Collection<AdminNotice> adminNoticeList = noticeDao.findAll();

    ModelAndView mv = new ModelAndView();
    mv.addObject("adminNoticeList", adminNoticeList);
    mv.addObject("pageTitle", "π κ³΅μ§κ²μκΈ λͺ©λ‘");
    mv.addObject("contentUrl", "admin/UserNoticeList.jsp");
    mv.setViewName("template1");
    return mv;
  }

  @GetMapping("/adminNotice/userdetail")
  public ModelAndView noticeUserDetail(int no) throws Exception {

    AdminNotice adminNotice = noticeDao.findByNoticeNo(no);

    if (adminNotice == null) {
      throw new Exception(" >> ν΄λΉ λ²νΈμ κ³΅μ§κΈμ΄ μμ΅λλ€.");
    }

    ModelAndView mv = new ModelAndView();
    mv.addObject("adminNotice", adminNotice);
    mv.addObject("pageTitle", "π κ³΅μ§κ²μκΈ μμΈ");
    mv.addObject("contentUrl", "admin/UserNoticeDetail.jsp");
    mv.setViewName("template1");
    return mv;
  }
}
