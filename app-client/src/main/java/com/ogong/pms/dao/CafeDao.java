package com.ogong.pms.dao;

import java.util.HashMap;
import java.util.List;
import com.ogong.pms.domain.Cafe;

public interface CafeDao {

  //-----------------------Cafe--------------------------------------

  List<Cafe> getCafeList() throws Exception;
  List<Cafe> getCafeListByMember() throws Exception;
  List<Cafe> findCafeListByLocation(String input) throws Exception;

  Cafe findByCafeNo(int cafeNo) throws Exception;
  Cafe findByCafeNoMember(int cafeNo) throws Exception;
  Cafe findByCeoMember(int ceoNo) throws Exception;

  // 조회수 증가
  void updateViewCount(int cafeNo) throws Exception;

  // 카페 휴무일
  String getCafeHoliday(HashMap<String,Object> params) throws Exception;
  void insertCafeHolidays(HashMap<String,Object> params) throws Exception;

  void insertCafe(Cafe cafe) throws Exception;
  void updateCafe(Cafe cafe) throws Exception;
  void deleteCafe(int cafeNo) throws Exception;

  // 관리자 카페 승인
  void updateCafeStatusToGENERAL(int cafeNo) throws Exception;

  // 카페 이미지
  void insertCafeImage(HashMap<String,Object> params) throws Exception;
  void deleteCafeImage(HashMap<String,Object> param) throws Exception;

}
