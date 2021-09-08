package com.ogong.pms.handler;

import java.util.List;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CafeReservation;
import com.ogong.pms.domain.CafeReview;
import com.ogong.pms.domain.Member;
import com.ogong.util.Prompt;

public class CafeMyReviewListHandler extends AbstractCafeHandler {

  public CafeMyReviewListHandler (List<Cafe> cafeList, List<CafeReview> reviewList, List<CafeReservation> reserList) {
    super (cafeList, reviewList, reserList);
  }

  @Override
  public void execute() {
    System.out.println();
    System.out.println("▶ 내가 쓴 후기 보기");
    System.out.println();

    Member member = AuthPerMemberLoginHandler.getLoginUser();

    if (member == null) {
      System.out.println("로그인 한 회원만 볼 수 있습니다.");
      return;
    }

    int count = 0;
    for (CafeReview cafeReview : reviewList) {
      if (cafeReview.getMember().getPerNickname().equals(member.getPerNickname())) {
        System.out.printf(" (%d)\n [%s]\n 별점 : %d\n 내용 : %s\n 등록일 : %s\n",
            cafeReview.getReviewNo(), cafeReview.getCafe().getName(), cafeReview.getGrade(),
            cafeReview.getContent(), cafeReview.getRegisteredDate());
        System.out.println();
        count++;
      } 
    }

    if (count == 0) {
      System.out.println(" >> 리뷰 내역이 존재하지 않습니다.");
      return;
    }

    System.out.println("----------------------");
    System.out.println("1. 리뷰 수정");
    System.out.println("2. 리뷰 삭제"); // 삭제 기능 있어야 하나 고민
    System.out.println("0. 뒤로 가기");

    int selectNo = Prompt.inputInt("선택> ");
    switch (selectNo) {
      case 1: updateMyReview(); break;
      case 2: deleteMyReview(); break;
      default : return;
    }
  }

  public void updateMyReview() {
    // 작성하기
  }

  public void deleteMyReview() {
    System.out.println();
    int inputNo = Prompt.inputInt("삭제할 리뷰번호 : ");
    int count = 0;

    Member member = AuthPerMemberLoginHandler.getLoginUser();

    if (member == null) {
      System.out.println("로그인 한 회원만 삭제할 수 있습니다.");
      return;
    }

    for (CafeReview cafeReview : reviewList) {
      if (cafeReview.getMember().getPerNickname().equals(member.getPerNickname()) &&
          cafeReview.getReviewNo() == inputNo) {
        count++;
        String input = Prompt.inputString("정말 삭제하시겠습니까? (네 /아니오) ");
        if (!input.equalsIgnoreCase("네")) {
          System.out.println("삭제를 취소합니다.");
          return;
        }

        reviewList.remove(cafeReview);
        System.out.println("삭제를 완료하였습니다.");
      } 
    }

    if (count == 0) {
      System.out.println("리뷰번호를 잘못 선택하셨습니다.");
    }
  }
}
