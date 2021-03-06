package com.ogong.pms.handler.cafe;

import java.util.List;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CafeReservation;
import com.ogong.pms.domain.Member;
import com.ogong.pms.handler.AbstractCafeHandler;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.pms.handler.PromptPerMember;
import com.ogong.util.Prompt;

public class CafeMyReservationListHandler extends AbstractCafeHandler {

  List<CafeReservation> reserList;
  PromptPerMember promptPerMember;

  public CafeMyReservationListHandler (List<Cafe> cafeList,
      List<CafeReservation> reserList, PromptPerMember promptPerMember) {

    super (cafeList);
    this.reserList = reserList;
    this.promptPerMember = promptPerMember;

    //    CafeReservation reservation = new CafeReservation();
    //    reservation.setReservationNo(1);
    //    reservation.setMember(promptPerMember.memberList.get(0));
    //    reservation.setCafe(cafeList.get(0));
    //    reservation.setReservationDate(Date.valueOf("2021-8-1"));
    //    reservation.setStartTime(LocalTime.of(10, 00));
    //    reservation.setUseTime(1);
    //    reservation.setUseMemberNumber(1);
    //    reservation.setTotalPrice(2000);
    //    reservation.setWirteReview(false);
    //    reservation.setReservationStatus(0);
    //    reserList.add(reservation);
    //
    //    reservation = new CafeReservation();
    //    reservation.setReservationNo(2);
    //    reservation.setMember(promptPerMember.memberList.get(0));
    //    reservation.setCafe(cafeList.get(0));
    //    reservation.setReservationDate(Date.valueOf("2021-10-10"));
    //    reservation.setStartTime(LocalTime.of(10, 00));
    //    reservation.setUseTime(3);
    //    reservation.setUseMemberNumber(0);
    //    reservation.setTotalPrice(45000);
    //    reservation.setWirteReview(false);
    //    reservation.setRoomNo(4);
    //    reservation.setReservationStatus(0);
    //    reserList.add(reservation);
    //
    //    reservation = new CafeReservation();
    //    reservation.setReservationNo(3);
    //    reservation.setMember(promptPerMember.memberList.get(0));
    //    reservation.setCafe(cafeList.get(0));
    //    reservation.setReservationDate(Date.valueOf("2021-10-10"));
    //    reservation.setStartTime(LocalTime.of(15, 00));
    //    reservation.setUseTime(2);
    //    reservation.setUseMemberNumber(0);
    //    reservation.setTotalPrice(30000);
    //    reservation.setWirteReview(false);
    //    reservation.setRoomNo(4);
    //    reservation.setReservationStatus(0);
    //    reserList.add(reservation);
  }

  @Override
  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("??? ??? ?????? ?????? ??????");
    System.out.println();

    Member member = AuthPerMemberLoginHandler.getLoginUser(); 

    if (member == null) {
      System.out.println(" >> ????????? ??? ????????? ??? ??? ????????????.");
      return;
    }
    int reservationCount = 0;

    for (CafeReservation myReservationList : reserList) {
      if (myReservationList.getMember().getPerNo() == member.getPerNo()) {
        System.out.printf(" (%d)\n ???????????? : %s\n ???????????? : %s\n ???????????? : %d???\n ?????? : %s\n"
            , myReservationList.getReservationNo(), myReservationList.getReservationDate(), 
            myReservationList.getCafe().getName(), myReservationList.getTotalPrice(),
            getReservationStatus(myReservationList.getReservationStatus()));
        System.out.println();
        reservationCount++;
      }
    }

    if (reservationCount == 0) {
      System.out.println(" >> ?????? ????????? ???????????? ????????????.");
      return;
    }

    System.out.println("----------------------");
    System.out.println("1. ??????");
    System.out.println("0. ??????");
    while (true) {
      int selectNo = Prompt.inputInt("??????> ");
      switch (selectNo) {
        case 1: request.getRequestDispatcher("/cafeReservation/detail").forward(request); return;
        case 0: return;
        default : 
          System.out.println(" >> ????????? ?????? ????????? ?????????.");
      }
    }
  }
}
