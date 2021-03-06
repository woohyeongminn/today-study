package com.ogong.pms.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Study {
  private int studyNo;          // 스터디 번호
  private String studyTitle;    // 스터디명
  private int subjectNo;        // 분야 번호
  private String subjectName;   // 분야
  private String area;          // 지역
  private int numberOfPeple;    // 인원수
  private int faceNo;           // 대면/비대면 번호
  private String faceName;      // 대면/비대면
  private String introduction;  // 소개글
  private Date registeredDate;  // 스터디 생성일
  private int point;            // 스터디 점수
  private int studyStatus;      // 스터디 상태       1.진행중 2.완료 3.삭제
  private int report;           // 스터디 신고여부

  private Member owner;         // 작성자(조장)
  private int status;           // 구성원 상태       1.승인대기중 2.승인 3.거절

  private int waitingCountMember;                            // 승인대기중인 구성원 수
  private List<Member> waitingMember = new ArrayList<>();    // 승인대기중

  private int countMember;                                  // 참여중인 구성원 수
  private List<Member> members = new ArrayList<>();         // 참여중

  private int countBookMember;                              // 북마크 수
  private List<Member> bookMarkMember = new ArrayList<>();  // 북마크한 회원

  private List<Calendar> myStudyCalender = new ArrayList<>();       // 내 스터디 캘린더
  private List<FreeBoard> myStudyFreeBoard = new ArrayList<>();     // 내 스터디 자유 게시판
  private List<ToDo> myStudyToDo = new ArrayList<>();               // 내 스터디 투두리스트

  @Override
  public String toString() {
    return "Study [studyNo=" + studyNo + ", studyTitle=" + studyTitle + ", subjectNo=" + subjectNo
        + ", subjectName=" + subjectName + ", area=" + area + ", numberOfPeple=" + numberOfPeple
        + ", faceNo=" + faceNo + ", faceName=" + faceName + ", introduction=" + introduction
        + ", registeredDate=" + registeredDate + ", point=" + point + ", studyStatus=" + studyStatus
        + ", report=" + report + ", owner=" + owner + ", status=" + status + ", waitingCountMember="
        + waitingCountMember + ", waitingMember=" + waitingMember + ", countMember=" + countMember
        + ", members=" + members + ", countBookMember=" + countBookMember + ", bookMarkMember="
        + bookMarkMember + ", myStudyCalender=" + myStudyCalender + ", myStudyFreeBoard="
        + myStudyFreeBoard + ", myStudyToDo=" + myStudyToDo + ", getWatingMemberNames()="
        + getWatingMemberNames() + ", getMemberNames()=" + getMemberNames() + ", getStudyNo()="
        + getStudyNo() + ", getStudyTitle()=" + getStudyTitle() + ", getSubjectNo()="
        + getSubjectNo() + ", getSubjectName()=" + getSubjectName() + ", getArea()=" + getArea()
        + ", getNumberOfPeple()=" + getNumberOfPeple() + ", getFaceNo()=" + getFaceNo()
        + ", getFaceName()=" + getFaceName() + ", getIntroduction()=" + getIntroduction()
        + ", getRegisteredDate()=" + getRegisteredDate() + ", getPoint()=" + getPoint()
        + ", getStudyStatus()=" + getStudyStatus() + ", getReport()=" + getReport()
        + ", getOwner()=" + getOwner() + ", getStatus()=" + getStatus()
        + ", getWaitingCountMember()=" + getWaitingCountMember() + ", getWaitingMember()="
        + getWaitingMember() + ", getCountMember()=" + getCountMember() + ", getMembers()="
        + getMembers() + ", getCountBookMember()=" + getCountBookMember() + ", getBookMarkMember()="
        + getBookMarkMember() + ", getMyStudyCalender()=" + getMyStudyCalender()
        + ", getMyStudyFreeBoard()=" + getMyStudyFreeBoard() + ", getMyStudyToDo()="
        + getMyStudyToDo() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
        + ", toString()=" + super.toString() + "]";
  }

  public String getWatingMemberNames() {
    if (this.members == null) {
      return "";
    }
    StringBuilder names = new StringBuilder();
    for (Member member : this.waitingMember) {
      if (names.length() > 0) {
        names.append(",");
      }
      names.append(member.getPerNickname());
    }
    return names.toString();
  }

  public String getMemberNames() {
    if (this.members == null) {
      return "";
    }
    StringBuilder names = new StringBuilder();
    for (Member member : this.members) {
      if (names.length() > 0) {
        names.append(", ");
      }
      names.append(member.getPerNickname());
    }
    return names.toString();
  }

  public int getStudyNo() {
    return studyNo;
  }

  public void setStudyNo(int studyNo) {
    this.studyNo = studyNo;
  }

  public String getStudyTitle() {
    return studyTitle;
  }

  public void setStudyTitle(String studyTitle) {
    this.studyTitle = studyTitle;
  }

  public int getSubjectNo() {
    return subjectNo;
  }

  public void setSubjectNo(int subjectNo) {
    this.subjectNo = subjectNo;
  }

  public String getSubjectName() {
    return subjectName;
  }

  public void setSubjectName(String subjectName) {
    this.subjectName = subjectName;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public int getNumberOfPeple() {
    return numberOfPeple;
  }

  public void setNumberOfPeple(int numberOfPeple) {
    this.numberOfPeple = numberOfPeple;
  }

  public int getFaceNo() {
    return faceNo;
  }

  public void setFaceNo(int faceNo) {
    this.faceNo = faceNo;
  }

  public String getFaceName() {
    return faceName;
  }

  public void setFaceName(String faceName) {
    this.faceName = faceName;
  }

  public String getIntroduction() {
    return introduction;
  }

  public void setIntroduction(String introduction) {
    this.introduction = introduction;
  }

  public Date getRegisteredDate() {
    return registeredDate;
  }

  public void setRegisteredDate(Date registeredDate) {
    this.registeredDate = registeredDate;
  }

  public int getPoint() {
    return point;
  }

  public void setPoint(int point) {
    this.point = point;
  }

  public int getStudyStatus() {
    return studyStatus;
  }

  public void setStudyStatus(int studyStatus) {
    this.studyStatus = studyStatus;
  }

  public int getReport() {
    return report;
  }

  public void setReport(int report) {
    this.report = report;
  }

  public Member getOwner() {
    return owner;
  }

  public void setOwner(Member owner) {
    this.owner = owner;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getWaitingCountMember() {
    return waitingCountMember;
  }

  public void setWaitingCountMember(int waitingCountMember) {
    this.waitingCountMember = waitingCountMember;
  }

  public List<Member> getWaitingMember() {
    return waitingMember;
  }

  public void setWaitingMember(List<Member> waitingMember) {
    this.waitingMember = waitingMember;
  }

  public int getCountMember() {
    return countMember;
  }

  public void setCountMember(int countMember) {
    this.countMember = countMember;
  }

  public List<Member> getMembers() {
    return members;
  }

  public void setMembers(List<Member> members) {
    this.members = members;
  }

  public int getCountBookMember() {
    return countBookMember;
  }

  public void setCountBookMember(int countBookMember) {
    this.countBookMember = countBookMember;
  }

  public List<Member> getBookMarkMember() {
    return bookMarkMember;
  }

  public void setBookMarkMember(List<Member> bookMarkMember) {
    this.bookMarkMember = bookMarkMember;
  }

  public List<Calendar> getMyStudyCalender() {
    return myStudyCalender;
  }

  public void setMyStudyCalender(List<Calendar> myStudyCalender) {
    this.myStudyCalender = myStudyCalender;
  }

  public List<FreeBoard> getMyStudyFreeBoard() {
    return myStudyFreeBoard;
  }

  public void setMyStudyFreeBoard(List<FreeBoard> myStudyFreeBoard) {
    this.myStudyFreeBoard = myStudyFreeBoard;
  }

  public List<ToDo> getMyStudyToDo() {
    return myStudyToDo;
  }

  public void setMyStudyToDo(List<ToDo> myStudyToDo) {
    this.myStudyToDo = myStudyToDo;
  }
}
