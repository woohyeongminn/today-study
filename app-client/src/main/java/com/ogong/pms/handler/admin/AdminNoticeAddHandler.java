package com.ogong.pms.handler.admin;

import java.sql.Date;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.ogong.pms.dao.NoticeDao;
import com.ogong.pms.domain.AdminNotice;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class AdminNoticeAddHandler implements Command {

  NoticeDao noticeDao;
  SqlSession sqlSession;

  public AdminNoticeAddHandler(NoticeDao noticeDao, SqlSession sqlSession) {
    this.noticeDao = noticeDao;
    this.sqlSession = sqlSession;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 공지 등록");
    System.out.println();

    List<AdminNotice> adminNoticeList = noticeDao.findAll();
    AdminNotice adminNotice = new AdminNotice();

    adminNotice.setAdminNotiTitle(Prompt.inputString(" 제목 : "));
    adminNotice.setAdminNotiContent(Prompt.inputString(" 내용 : "));

    String inputfile = Prompt.inputString(" 첨부파일을 등록하시겠습니까? (네 / 아니오) ");
    if (inputfile.equals("네")) {
      adminNotice.setAdminNotiFile(Prompt.inputString(" 첨부파일 : "));
    } 

    adminNotice.setAdminNotiRegisteredDate(new Date(System.currentTimeMillis()));

    System.out.println();
    String input = Prompt.inputString(" 정말 등록하시겠습니까? (네 / 아니오) ");
    if (!input.equals("네")) {
      System.out.println(" >> 등록이 취소되었습니다.");
      request.getRequestDispatcher("/adminNotice/list").forward(request);
      return;
    }

    //    // 마지막 고유번호를 찾아서 신규 등록시 +1 되도록 기능 구현
    //    AdminNotice lastAdminNotice = null;
    //    if (!adminNoticeList.isEmpty()) {
    //      lastAdminNotice = adminNoticeList.get(adminNoticeList.size() - 1);
    //      adminNotice.setAdminNotiNo(lastAdminNotice.getAdminNotiNo() +1);
    //    } else {
    //      adminNotice.setAdminNotiNo(1);
    //    }

    noticeDao.insert(adminNotice);
    noticeDao.insertFilepath(adminNotice);
    sqlSession.commit();

    System.out.println(" >> 공지글 등록이 완료되었습니다.");
    request.getRequestDispatcher("/adminNotice/list").forward(request);
    return;
  }
}
