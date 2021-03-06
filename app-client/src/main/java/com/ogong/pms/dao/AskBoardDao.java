package com.ogong.pms.dao;

import java.util.List;
import com.ogong.pms.domain.AskBoard;

public interface AskBoardDao {

  void insertPer(AskBoard askBoard) throws Exception;
  void insertCeo(AskBoard askBoard) throws Exception;
  void update(AskBoard askBoard) throws Exception;
  void delete(int no) throws Exception;
  List<AskBoard> findAll() throws Exception;
  // 개인회원 문의게시글 목록(로그인 유저)
  List<AskBoard> findPerMyAll(int perMemberNo) throws Exception;
  //사장회원 문의게시글 목록(로그인 유저)
  //List<AskBoard> findCeoMyAll(int ceoMemberNo) throws Exception;
  AskBoard findByNo(int no) throws Exception;

  // 답변 전용
  void insertreply(AskBoard askBoard) throws Exception;
  void updateViewCount(AskBoard askBoard) throws Exception;
  void deletereply(int no) throws Exception;

}
