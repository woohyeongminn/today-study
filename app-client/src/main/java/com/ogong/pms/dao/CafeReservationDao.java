package com.ogong.pms.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ogong.pms.domain.CafeReservation;

public interface CafeReservationDao {

  //-----------------------CafeReservation--------------------------------------

  List<CafeReservation> getCafeReservationList() throws Exception;
  List<CafeReservation> findReservationListByMember(int memberNo) throws Exception;
  List<CafeReservation> findReservationListByCeoMember(int ceoNo) throws Exception;
  CafeReservation findReservationByMember(@Param("memberNo")int memberNo, @Param("reserNo")int reserNo) throws Exception;
  void insertReservation(CafeReservation cafeReservation) throws Exception;
  void deleteReservation(@Param("reservationNo")int reservationNo, @Param("status")int status) throws Exception;

  // 리뷰 등록시 예약테이블에도 리뷰 등록했다고 변경
  void updateCafeReservationReviewStatus(int reservationNo) throws Exception;

}
