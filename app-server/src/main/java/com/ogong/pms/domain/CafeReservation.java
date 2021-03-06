package com.ogong.pms.domain;

import java.sql.Date;
import java.time.LocalTime;

public class CafeReservation {

  private int reservationNo; // 예약 번호
  private Member member; // 예약 회원
  private Cafe cafe; // 예약 카페
  private Date reservationDate; // 예약 날짜
  private Date useDate; // 이용 날짜
  private LocalTime startTime; // 시작시간
  private int useTime; // 사용시간
  private int useMemberNumber; // 사용인원
  private int totalPrice; // 총금액
  private boolean wirteReview; // 리뷰작성 여부
  private String wirteReviewLable; // 리뷰 작성가능 / 작성불가 출력
  private int roomNo; // 룸 예약시 룸 번호
  private String roomName;  // 룸 이름
  private int reservationStatus; // 1 : 예약완료(현장결제) , 2 : 결제완료 , 
  //                                3 : 예약취소(개인) , 4 : 결제취소(개인) ,
  //                                5 : 예약거절(사장) , 6 : 결제거절(사장)
  //                                7 : 이용완료
  private String reservationStatusName; // 예약상태이름
  private String paymentUid; // 결제 고유 번호
  private String paymentType; // 결제방법

  @Override
  public String toString() {
    return "CafeReservation [reservationNo=" + reservationNo + ", member=" + member + ", cafe="
        + cafe + ", reservationDate=" + reservationDate + ", useDate=" + useDate + ", startTime="
        + startTime + ", useTime=" + useTime + ", useMemberNumber=" + useMemberNumber
        + ", totalPrice=" + totalPrice + ", wirteReview=" + wirteReview + ", wirteReviewLable="
        + wirteReviewLable + ", roomNo=" + roomNo + ", roomName=" + roomName
        + ", reservationStatus=" + reservationStatus + ", reservationStatusName="
        + reservationStatusName + ", paymentUid=" + paymentUid + ", paymentType=" + paymentType
        + "]";
  }

  public int getReservationNo() {
    return reservationNo;
  }

  public void setReservationNo(int reservationNo) {
    this.reservationNo = reservationNo;
  }

  public Member getMember() {
    return member;
  }

  public void setMember(Member member) {
    this.member = member;
  }

  public Cafe getCafe() {
    return cafe;
  }

  public void setCafe(Cafe cafe) {
    this.cafe = cafe;
  }

  public Date getReservationDate() {
    return reservationDate;
  }

  public void setReservationDate(Date reservationDate) {
    this.reservationDate = reservationDate;
  }

  public Date getUseDate() {
    return useDate;
  }

  public void setUseDate(Date useDate) {
    this.useDate = useDate;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  public int getUseTime() {
    return useTime;
  }

  public void setUseTime(int useTime) {
    this.useTime = useTime;
  }

  public int getUseMemberNumber() {
    return useMemberNumber;
  }

  public void setUseMemberNumber(int useMemberNumber) {
    this.useMemberNumber = useMemberNumber;
  }

  public int getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(int totalPrice) {
    this.totalPrice = totalPrice;
  }

  public boolean getWirteReview() {
    return wirteReview;
  }

  public void setWirteReview(boolean wirteReview) {
    this.wirteReview = wirteReview;
  }

  public int getRoomNo() {
    return roomNo;
  }

  public void setRoomNo(int roomNo) {
    this.roomNo = roomNo;
  }

  public String getRoomName() {
    return roomName;
  }

  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }

  public int getReservationStatus() {
    return reservationStatus;
  }

  public void setReservationStatus(int reservationStatus) {
    this.reservationStatus = reservationStatus;
  }

  public String getReservationStatusName() {
    return reservationStatusName;
  }

  public void setReservationStatusName(String reservationStatusName) {
    this.reservationStatusName = reservationStatusName;
  }

  public String getWirteReviewLable() {
    return wirteReviewLable;
  }

  public void setWirteReviewLable(String wirteReviewLable) {
    this.wirteReviewLable = wirteReviewLable;
  }

  public String getPaymentUid() {
    return paymentUid;
  }

  public void setPaymentUid(String paymentUid) {
    this.paymentUid = paymentUid;
  }

  public String getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
  }

}
