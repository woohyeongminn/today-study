package com.ogong.pms.handler.ceoCafe;

import java.util.HashMap;
import java.util.List;
import com.ogong.pms.dao.CafeDao;
import com.ogong.pms.dao.CafeReviewDao;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CafeReview;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.handler.AuthCeoMemberLoginHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.pms.handler.cafe.CafeHandlerHelper;
import com.ogong.util.Prompt;

public class CeoCafeDetailHandler implements Command {

  CafeDao cafeDao;
  CafeReviewDao cafeReviewDao;

  public CeoCafeDetailHandler (CafeDao cafeDao, CafeReviewDao cafeReviewDao) {
    this.cafeDao = cafeDao;
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ 내 카페 관리");
    System.out.println();

    CeoMember ceoMember = AuthCeoMemberLoginHandler.getLoginCeoMember();
    Cafe cafe = cafeDao.findByCeoMember(ceoMember.getCeoNo());

    if (cafe == null) {
      System.out.println(" >> 등록된 카페가 없습니다.");
      System.out.println("\n----------------------");
      System.out.println("1. 등록");
      System.out.println("0. 이전");
      int selectNo = Prompt.inputInt("선택> ");
      switch (selectNo) {
        case 1 : request.getRequestDispatcher("/ceoMember/cafeAdd").forward(request); return;
        default : return;
      }
    }
    HashMap<String,Object> params = new HashMap<>();
    params.put("ceoNo", ceoMember.getCeoNo());
    cafe.setHoliday(cafeDao.getCafeHoliday(params));

    System.out.printf(" [%s]\n", cafe.getName());
    System.out.printf(" >> 대표자 : %s\n", cafe.getCeoMember().getCeoBossName());
    System.out.printf(" >> 사업자 등록번호 : %s\n", cafe.getCeoMember().getCeoLicenseNo());
    System.out.printf(" >> 대표이미지 : %s\n", cafe.getCafeImageNames());
    System.out.printf(" >> 소개글 : %s\n", cafe.getInfo());
    System.out.printf(" >> 주소 : %s\n", cafe.getLocation());
    System.out.printf(" >> 전화번호 : %s\n", cafe.getPhone());
    System.out.printf(" >> 오픈시간 : %s\n", cafe.getOpenTime());
    System.out.printf(" >> 마감시간 : %s\n", cafe.getCloseTime());
    System.out.printf(" >> 이번주 휴무일 : %s\n", cafe.getHoliday());
    System.out.printf(" >> 상태 : %s\n", CafeHandlerHelper.getCafeStatusLabel(cafe.getCafeStatus()));
    System.out.printf(" >> 리뷰평점 : ★ %.1f(%d)\n" , cafe.getAvgReview(), cafe.getCountReview());
    listReview(cafe); // 리뷰 목록
    System.out.println();

    request.setAttribute("cafeNo", cafe.getNo());

    System.out.println("----------------------");
    System.out.println("1. 수정");
    System.out.println("2. 삭제");
    System.out.println("3. 스터디룸 관리");
    System.out.println("4. 예약관리");
    System.out.println("0. 이전");
    int selectNo = Prompt.inputInt(" 선택> ");
    switch (selectNo) {
      case 1: request.getRequestDispatcher("/ceoMember/cafeUpdate").forward(request); return;
      case 2: request.getRequestDispatcher("/ceoMember/cafeDelete").forward(request); return;
      case 3: request.getRequestDispatcher("/ceoMember/cafeRoomList").forward(request); return;
      case 4: request.getRequestDispatcher("/ceoMember/ReservationDetail").forward(request); return;
      case 0: return;
      default : 
        System.out.println(" >> 번호를 다시 선택해 주세요.");
    }
  }

  private void listReview(Cafe cafe) throws Exception {
    int i = 1;
    System.out.println();
    System.out.println("============= 리뷰 =============");

    try {
      List<CafeReview> reviewList = cafeReviewDao.findReviewListByCafeNo(cafe.getNo());

      for (CafeReview review : reviewList) {
        if (review.getReviewStatus() == 2) {
          //System.out.printf(" \n (%s)\n", review.getReviewNo());
          System.out.printf(" (%d) | 삭제 된 리뷰입니다. |\n", i++);
          continue;
        }
        String nickname = review.getMember().getPerNickname();
        System.out.printf(" (%d) 닉네임 : %s | 별점 : %s | 내용 : %s | 등록일 : %s\n",
            i++, nickname, CafeHandlerHelper.getReviewGradeStatusLabel(review.getGrade())
            , review.getContent(), review.getRegisteredDate());
      }
    } catch (NullPointerException e) {
      System.out.println(" >> 등록된 리뷰가 없습니다.");
    }



  }
}
