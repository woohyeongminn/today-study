package com.ogong.pms.handler.admin;

import org.apache.ibatis.session.SqlSession;
import com.ogong.pms.dao.NoticeDao;
import com.ogong.pms.domain.AdminNotice;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class AdminNoticeUpdateHandler implements Command {

  NoticeDao noticeDao;
  SqlSession sqlSession;

  public AdminNoticeUpdateHandler(NoticeDao noticeDao, SqlSession sqlSession) {
    this.noticeDao = noticeDao;
    this.sqlSession = sqlSession;
  }
  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 공지 변경");
    System.out.println();
    int noticeNo = (int) request.getAttribute("noticeNo");

    AdminNotice notice = noticeDao.findByNoticeNo(noticeNo);

    System.out.println("1. 제목");
    System.out.println("2. 내용");
    System.out.println("3. 파일추가");
    System.out.println("4. 파일삭제");
    System.out.println();
    int selectNo = Prompt.inputInt(" 수정하고 싶은 정보를 선택해 주세요. > ");
    System.out.println();

    String adminNoticeTitle = notice.getAdminNotiTitle();
    String adminNoticeContent = notice.getAdminNotiContent();
    String adminNoticeFile = notice.getAdminNotiFile();

    switch (selectNo) {
      case 1: adminNoticeTitle = Prompt.inputString(" 제목(" + notice.getAdminNotiTitle()  + ") : ");
      break;
      case 2: adminNoticeContent = Prompt.inputString(" 내용(" + notice.getAdminNotiContent()  + ") : ");
      break;
      case 3:
        if (notice.getAdminNotiFile() != null) {
          System.out.println(" >> 이미 등록된 첨부파일이 있습니다.");
          return;
        }
        String inputfile = Prompt.inputString(" 첨부파일을 등록하시겠습니까? (네 / 아니오) ");
        if (inputfile.equals("네")) {
          notice.setAdminNotiFile(Prompt.inputString(" 첨부파일 : "));
        } 
        break;
      case 4:
        String inputdelete = Prompt.inputString(" 첨부파일을 삭제하시겠습니까? (네 / 아니오) ");
        if (!inputdelete.equals("네")) {
          System.out.println(" >> 이전 화면으로 돌아갑니다.");
        } 
        break;
      default : 
        System.out.println(" >> 올바른 번호를 입력해 주세요.");
        return;
    }

    System.out.println();
    String inputnotice = Prompt.inputString(" 정말 변경하시겠습니까? (네 / 아니오) ");
    if (!inputnotice.equalsIgnoreCase("네")) {
      System.out.println(" >> 변경이 취소되었습니다.");
      request.getRequestDispatcher("/adminNotice/list").forward(request);
      return;
    }

    if (selectNo == 1) {
      notice.setAdminNotiTitle(adminNoticeTitle);
      noticeDao.updateTitle(notice);
      sqlSession.commit();
    } else if (selectNo == 2) {
      notice.setAdminNotiContent(adminNoticeContent);
      noticeDao.updateContent(notice);
      sqlSession.commit();
    } else if (selectNo == 3) {
      noticeDao.insertFilepath(notice);
      sqlSession.commit();
    } else if (selectNo == 4) {
      notice.setAdminNotiFile(adminNoticeFile);
      noticeDao.deletenoticefile(noticeNo);
      sqlSession.commit();
    }

    System.out.println(" >> 공지가 변경되었습니다.");
    request.getRequestDispatcher("/adminNotice/list").forward(request);
    return;
  }

}
