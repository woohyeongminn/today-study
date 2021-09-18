package com.ogong.pms;

import static com.ogong.menu.Menu.ADMIN_LOGIN;
import static com.ogong.menu.Menu.CEO_LOGIN;
import static com.ogong.menu.Menu.LOGOUT;
import static com.ogong.menu.Menu.PER_LOGIN;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ogong.menu.Menu;
import com.ogong.menu.MenuGroup;
import com.ogong.pms.domain.Admin;
import com.ogong.pms.domain.AdminNotice;
import com.ogong.pms.domain.AskBoard;
import com.ogong.pms.domain.Cafe;
import com.ogong.pms.domain.CafeReservation;
import com.ogong.pms.domain.CafeReview;
import com.ogong.pms.domain.CafeRoom;
import com.ogong.pms.domain.Calender;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.domain.Comment;
import com.ogong.pms.domain.FreeBoard;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Study;
import com.ogong.pms.domain.ToDo;
import com.ogong.pms.handler.AdminCafeControlHandler;
import com.ogong.pms.handler.AdminCafeReviewListControlHandler;
import com.ogong.pms.handler.AdminCeoMemberDeleteHandler;
import com.ogong.pms.handler.AdminCeoMemberDetailHandler;
import com.ogong.pms.handler.AdminCeoMemberListHandler;
import com.ogong.pms.handler.AdminCeoMemberUpdateHandler;
import com.ogong.pms.handler.AdminDetailHandler;
import com.ogong.pms.handler.AdminMemberDeleteHandler;
import com.ogong.pms.handler.AdminMemberDetailHandler;
import com.ogong.pms.handler.AdminMemberListHandler;
import com.ogong.pms.handler.AdminMemberUpdateHandler;
import com.ogong.pms.handler.AdminNoticeAddHandler;
import com.ogong.pms.handler.AdminNoticeDeleteHandler;
import com.ogong.pms.handler.AdminNoticeDetailHandler;
import com.ogong.pms.handler.AdminNoticeListHandler;
import com.ogong.pms.handler.AdminNoticeUpdateHandler;
import com.ogong.pms.handler.AdminStudyDeleteHandler;
import com.ogong.pms.handler.AdminUpdateHandler;
import com.ogong.pms.handler.AskBoardAddHandler;
import com.ogong.pms.handler.AskBoardDeleteHandler;
import com.ogong.pms.handler.AskBoardDetailHandler;
import com.ogong.pms.handler.AskBoardListHandler;
import com.ogong.pms.handler.AskBoardMyListHandler;
import com.ogong.pms.handler.AskBoardUpdateHandler;
import com.ogong.pms.handler.AuthAdminLoginHandler;
import com.ogong.pms.handler.AuthAdminLogoutHandler;
import com.ogong.pms.handler.AuthCeoMemberLoginHandler;
import com.ogong.pms.handler.AuthCeoMemberLogoutHandler;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.AuthPerMemberLogoutHandler;
import com.ogong.pms.handler.CafeDetailHandler;
import com.ogong.pms.handler.CafeListHandler;
import com.ogong.pms.handler.CafeMyReservationDetailHandler;
import com.ogong.pms.handler.CafeMyReservationListHandler;
import com.ogong.pms.handler.CafeMyReviewListHandler;
import com.ogong.pms.handler.CafeSearchHandler;
import com.ogong.pms.handler.CeoAddHandler;
import com.ogong.pms.handler.CeoCafeAddHandler;
import com.ogong.pms.handler.CeoCafeDeleteHandler;
import com.ogong.pms.handler.CeoCafeDetailHandler;
import com.ogong.pms.handler.CeoCafeListHandler;
import com.ogong.pms.handler.CeoCafeUpdateHandler;
import com.ogong.pms.handler.CeoDeleteHandler;
import com.ogong.pms.handler.CeoDetailHandler;
import com.ogong.pms.handler.CeoFindIdPwHandler;
import com.ogong.pms.handler.CeoReservationListHandler;
import com.ogong.pms.handler.CeoUpdateHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.pms.handler.MemberAddHandler;
import com.ogong.pms.handler.MemberDeleteHandler;
import com.ogong.pms.handler.MemberDetailHandler;
import com.ogong.pms.handler.MemberFindIdPwHandler;
import com.ogong.pms.handler.MemberUpdateHandler;
import com.ogong.pms.handler.MyStudyCalender;
import com.ogong.pms.handler.MyStudyDeleteHandler;
import com.ogong.pms.handler.MyStudyDetailHandler;
import com.ogong.pms.handler.MyStudyFreeBoard;
import com.ogong.pms.handler.MyStudyGuilder;
import com.ogong.pms.handler.MyStudyListHandler;
import com.ogong.pms.handler.MyStudyToDo;
import com.ogong.pms.handler.MyStudyUpdateHandler;
import com.ogong.pms.handler.PromptCafe;
import com.ogong.pms.handler.PromptCeoMember;
import com.ogong.pms.handler.PromptPerMember;
import com.ogong.pms.handler.PromptStudy;
import com.ogong.pms.handler.StudyAddHandler;
import com.ogong.pms.handler.StudyDetailHandler;
import com.ogong.pms.handler.StudyListHandler;
import com.ogong.pms.handler.StudySearchHandler;
import com.ogong.util.Prompt;

public class App {
  List<Study> studyList = new LinkedList<>();
  List<Member> memberList = new LinkedList<>();
  List<AdminNotice> adminNoticeList = new ArrayList<>();
  List<AskBoard> askBoardList = new ArrayList<>();
  List<Cafe> cafeList = new ArrayList<>();
  List<CafeReview> cafeReviewList = new ArrayList<>();
  List<CafeReservation> cafeReservationList = new ArrayList<>();
  List<ToDo> toDoList = new ArrayList<>();
  List<FreeBoard> freeBoardList = new ArrayList<>();
  List<Calender> calenderList = new ArrayList<>();
  List<Admin> adminList = new ArrayList<>();
  List<CeoMember> ceoMemberList = new ArrayList<>();
  List<Comment> commentList = new ArrayList<>();
  List<CafeRoom> cafeRoomList = new ArrayList<>();

  // 해시맵 추가(0904)
  HashMap<String, Command> commandMap = new HashMap<>();

  PromptPerMember promptPerMember = new PromptPerMember(memberList); 
  PromptCeoMember promptCeoMember = new PromptCeoMember(ceoMemberList);
  PromptCafe promptcafe = new PromptCafe(cafeList, cafeReviewList);
  PromptStudy promptStudy = new PromptStudy(studyList);

  class MenuItem extends Menu {
    String menuId;

    public MenuItem(String title, String menuId) {
      super(title);
      this.menuId = menuId;
    }

    public MenuItem(String title, int accessScope, String menuId) {
      super(title, accessScope);
      this.menuId = menuId;
    }

    @Override
    public void execute() {
      Command command = commandMap.get(menuId);
      try {
        command.execute(new CommandRequest(commandMap));
      } catch (Exception e) {
        System.out.printf("%s 명령을 실행하는 중 오류 발생!\n", menuId);
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    App app = new App(); 
    app.welcomeservice();
  }

  public App() {
    commandMap.put("/member/add", new MemberAddHandler(memberList));

    commandMap.put("/member/detail", new MemberDetailHandler(memberList, commandMap));
    commandMap.put("/member/update", new MemberUpdateHandler(memberList, promptPerMember));
    commandMap.put("/member/delete", 
        new MemberDeleteHandler(memberList, promptPerMember, studyList));

    commandMap.put("/ceoMember/login", new AuthCeoMemberLoginHandler(ceoMemberList, promptCeoMember));
    commandMap.put("/ceoMember/logout", new AuthCeoMemberLogoutHandler());
    commandMap.put("/ceoMember/findIdPw", new CeoFindIdPwHandler(ceoMemberList, promptCeoMember));
    commandMap.put("/ceoMember/add", new CeoAddHandler(ceoMemberList));
    commandMap.put("/ceoMember/detail", new CeoDetailHandler(ceoMemberList));
    commandMap.put("/ceoMember/update", new CeoUpdateHandler(ceoMemberList, promptCeoMember));
    commandMap.put("/ceoMember/delete", 
        new CeoDeleteHandler(ceoMemberList, promptCeoMember, cafeList));
    commandMap.put("/ceoMember/myCafeList", 
        new CeoCafeListHandler(ceoMemberList, cafeList, cafeReviewList, promptPerMember));
    commandMap.put("/ceoMember/cafeAdd", new CeoCafeAddHandler(cafeList, ceoMemberList));
    commandMap.put("/ceoMember/cafeUpdate", new CeoCafeUpdateHandler(ceoMemberList));
    commandMap.put("/ceoMember/cafeDelete", new CeoCafeDeleteHandler(cafeList, promptcafe));
    commandMap.put("/ceoMember/myCafeDetail", 
        new CeoCafeDetailHandler(ceoMemberList, cafeList, cafeReviewList, cafeRoomList));
    commandMap.put("/ceoMember/ReservationList", 
        new CeoReservationListHandler(ceoMemberList, cafeReservationList, cafeList, cafeRoomList));

    commandMap.put("/adminMember/detail", new AdminMemberDetailHandler(memberList, promptPerMember));
    commandMap.put("/adminMember/update", new AdminMemberUpdateHandler(memberList, promptPerMember));
    commandMap.put("/adminMember/delete", new AdminMemberDeleteHandler(memberList, promptPerMember, studyList));
    commandMap.put("/adminMember/list", new AdminMemberListHandler(memberList, commandMap));

    commandMap.put("/adminCeoMember/detail", new AdminCeoMemberDetailHandler(ceoMemberList, promptCeoMember));
    commandMap.put("/adminCeoMember/update", new AdminCeoMemberUpdateHandler(ceoMemberList, promptCeoMember));
    commandMap.put("/adminCeoMember/delete",
        new AdminCeoMemberDeleteHandler(ceoMemberList, promptCeoMember, cafeList));
    commandMap.put("/adminCeoMember/list", new AdminCeoMemberListHandler(ceoMemberList, commandMap));

    commandMap.put("/askBoard/add",  new AskBoardAddHandler(askBoardList, memberList, ceoMemberList, commentList));
    commandMap.put("/askBoard/list", new AskBoardListHandler(askBoardList, memberList, ceoMemberList, commentList));
    commandMap.put("/askBoard/detail", new AskBoardDetailHandler(askBoardList, memberList, ceoMemberList, commentList));
    commandMap.put("/askBoard/update", new AskBoardUpdateHandler(askBoardList, memberList, ceoMemberList, commentList));
    commandMap.put("/askBoard/delete", new AskBoardDeleteHandler(askBoardList, memberList, ceoMemberList, commentList));
    commandMap.put("/askBoard/myList", new AskBoardMyListHandler(askBoardList, memberList, ceoMemberList, commentList));

    commandMap.put("/cafe/list", new CafeListHandler(cafeList));
    commandMap.put("/cafe/detail", new CafeDetailHandler(cafeList, cafeReviewList, 
        cafeReservationList, promptPerMember, cafeRoomList, promptcafe));
    commandMap.put("/cafe/search", new CafeSearchHandler(cafeList));
    commandMap.put("/cafeReservation/list", new CafeMyReservationListHandler(cafeList, 
        cafeReservationList, promptPerMember));
    commandMap.put("/cafeReservation/detail", new CafeMyReservationDetailHandler(cafeList, 
        cafeReviewList, cafeReservationList, cafeRoomList));
    commandMap.put("/cafe/myReviewList", new CafeMyReviewListHandler(cafeList, cafeReviewList, promptcafe));

    commandMap.put("/cafe/control", new AdminCafeControlHandler(cafeList, cafeReviewList, 
        promptPerMember, promptcafe));
    commandMap.put("/cafe/reviewList", new AdminCafeReviewListControlHandler(cafeList, 
        cafeReviewList, promptcafe)); 

    commandMap.put("/adminNotice/add", new AdminNoticeAddHandler(adminNoticeList));
    commandMap.put("/adminNotice/list", new AdminNoticeListHandler(adminNoticeList));
    commandMap.put("/adminNotice/update", new AdminNoticeUpdateHandler(adminNoticeList));
    commandMap.put("/adminNotice/detail", new AdminNoticeDetailHandler(adminNoticeList));
    commandMap.put("/adminNotice/delete", new AdminNoticeDeleteHandler(adminNoticeList));

    commandMap.put("/admin/login", new AuthAdminLoginHandler(adminList));
    commandMap.put("/admin/logout", new AuthAdminLogoutHandler());

    commandMap.put("/member/login", new AuthPerMemberLoginHandler(promptPerMember, memberList));
    commandMap.put("/member/findIdPw", new MemberFindIdPwHandler(promptPerMember));
    commandMap.put("/member/logout", new AuthPerMemberLogoutHandler());

    commandMap.put("/admin/detail", new AdminDetailHandler(adminList));
    commandMap.put("/admin/update", new AdminUpdateHandler(adminList));

    commandMap.put("/study/add", new StudyAddHandler(studyList, toDoList, promptPerMember));
    commandMap.put("/study/list", new StudyListHandler(studyList));
    commandMap.put("/study/detail", new StudyDetailHandler(studyList, promptStudy));
    commandMap.put("/study/search", new StudySearchHandler(studyList));
    commandMap.put("/study/delete", new AdminStudyDeleteHandler(studyList, promptStudy));


    // 내 스터디 하위
    MyStudyCalender myStudyCalender = new MyStudyCalender(calenderList, studyList);
    MyStudyToDo myStudyToDo = new MyStudyToDo(toDoList, studyList);
    MyStudyFreeBoard myStudyFreeBoard = new MyStudyFreeBoard(freeBoardList, commentList, memberList, studyList);
    MyStudyGuilder myStudyGuilder = new MyStudyGuilder();

    // 내 스터디 
    commandMap.put("/myStudy/detail", new MyStudyDetailHandler(studyList, myStudyToDo,
        myStudyCalender, myStudyFreeBoard, commentList, myStudyGuilder, promptStudy));

    commandMap.put("/myStudy/delete", new MyStudyDeleteHandler(studyList, promptStudy));
    commandMap.put("/myStudy/list", new MyStudyListHandler(studyList, commandMap));
    commandMap.put("/myStudy/update", new MyStudyUpdateHandler(studyList, promptStudy));

  }

  void welcomeservice() {
    welcome().execute();
    service();
  }

  void service() {

    loadObjects("member.json", memberList, Member.class);
    loadObjects("ceoMember.json", ceoMemberList, CeoMember.class);
    loadObjects("admin.json", adminList, Admin.class);
    loadObjects("adminNotice.json" , adminNoticeList, AdminNotice.class);
    loadObjects("askBoard.json", askBoardList, AskBoard.class);
    loadObjects("cafe.json", cafeList, Cafe.class);
    loadObjects("cafeReservation.json", cafeReservationList, CafeReservation.class);
    loadObjects("cafeReview.json", cafeReviewList, CafeReview.class);
    loadObjects("cafeRoom.json", cafeRoomList, CafeRoom.class);
    loadObjects("study.json", studyList, Study.class);
    loadObjects("toDo.json", toDoList, ToDo.class);
    loadObjects("calender.json", calenderList, Calender.class);
    loadObjects("freeBoard.json", freeBoardList, FreeBoard.class);

    createMenu().execute();
    Prompt.close();

    //    saveObjects("member.json", memberList);  // MemberAddHandler
    //    saveObjects("askBoard.json", askBoardList);  // AskBoardAddHandler
    //    saveObjects("study.json", studyList);   // StudyAddHandler
    //    saveObjects("freeBoard.json", freeBoardList); // MyStudyFreeBoard
    //    saveObjects("ceoMember.json", ceoMemberList);   // CeoAddHandler
    //    saveObjects("admin.json", adminList); // AuthAdminLoginHandler
    //    saveObjects("adminNotice.json" , adminNoticeList);  // AdminNoticeAddHandler
    //    saveObjects("cafe.json", cafeList); // CeoCafeAddHandler
    //    saveObjects("cafeReservation.json", cafeReservationList);  // CafeMyReservationListHandler
    //    saveObjects("cafeReview.json", cafeReviewList);
    //    saveObjects("cafeRoom.json", cafeRoomList); // CafeDetailHandler 테스트값 : 2021-10-10
    //    saveObjects("toDo.json", toDoList);  // MyStudyToDo
    //    saveObjects("calender.json", calenderList); // MyStudyCalender

  }

  static Menu welcome() {
    ////////////////////////////////////////////////////////////////
    System.out.println("\u2728" + "오늘의 공부" + "\u2728");
    System.out.println("\u0020\u2229__\u2229\u0020\u00a0\u00a0\u00a0\u0020\u2229__\u2229\u0020");
    System.out.println("\u0028\u0020\u0027\u03c0\u0027\u0029\u00a0\u00a0\u00a0\u0028\u0027\u03c0\u0027\u00a0\u0029");
    System.out.println("\u0028\u0020\u2283\u2755\u2282\u0029\u00a0\u00a0\u00a0\u0028\u2283\u2755\u2282\u00a0\u0029");
    ////////////////////////////////////////////////////////////////
    MenuGroup welcomeMenuGroup = new MenuGroup("발표를 시작하겠습니다");
    welcomeMenuGroup.setPrevMenuTitle("시작");
    return welcomeMenuGroup;
  }


  //--------------------------------------------------------
  Menu createMenu() {

    MenuGroup mainMenuGroup = new MenuGroup("메인");
    mainMenuGroup.setPrevMenuTitle("종료");

    mainMenuGroup.add(createAdminMenu());
    mainMenuGroup.add(createMemberMenu());
    mainMenuGroup.add(createCeoMenu());

    return mainMenuGroup;
  }


  // JSON 형식으로 저장된 데이터를 읽어서 객체로 만든다.
  @SuppressWarnings("unused")
  private <E> void loadObjects(
      String filepath, // 데이터를 읽어 올 파일 경로 
      List<E> list, // 로딩한 데이터를 객체로 만든 후 저장할 목록 
      Class<E> domainType // 생성할 객체의 타입정보
      ) {

    // CSV 형식으로 저장된 게시글 데이터를 파일에서 읽어 객체에 담는다. 
    try (BufferedReader in = new BufferedReader(
        new FileReader(filepath, Charset.forName("UTF-8")))) {

      StringBuilder strBuilder = new StringBuilder();
      String str;
      while ((str = in.readLine()) != null) { // 파일 전체를 읽는다.
        strBuilder.append(str);
      }

      // *StringBuilder로 읽어온 JSON 문자열을 객체로 바꾼다.
      Type type = TypeToken.getParameterized(Collection.class, domainType).getType();
      Collection<E> collection = new Gson().fromJson(strBuilder.toString(), type);

      // JSON 데이터로 읽어온 목록을 파라미터로 받은 List 에 저장한다.
      list.addAll(collection);

      System.out.printf("%s 데이터 로딩 완료!\n", filepath);

    } catch (Exception e) {
      System.out.printf("%s 데이터 로딩 오류!\n", filepath);
    }
  }

  // 객체를 JSON 형식으로 저장한다.
  @SuppressWarnings("unused")
  private void saveObjects(String filepath, List<?> list) {
    try (PrintWriter out = new PrintWriter(
        new BufferedWriter(
            new FileWriter(filepath, Charset.forName("UTF-8"))))) {

      out.print(new Gson().toJson(list));

      System.out.printf("%s 데이터 출력 완료!\n", filepath);

    } catch (Exception e) {
      System.out.printf("%s 데이터 출력 오류!\n", filepath);
      e.printStackTrace();
    }
  }

  // -----------------------------------------------------------------------------------------------
  // 관리자 메인
  Menu createAdminMenu() {
    MenuGroup adminMenuGroup = new MenuGroup("관리자");

    adminMenuGroup.add(new MenuItem("로그인", LOGOUT, "/admin/login"));
    adminMenuGroup.add(new MenuItem("로그아웃", ADMIN_LOGIN, "/admin/logout"));
    adminMenuGroup.add(new MenuItem("마이 페이지", ADMIN_LOGIN, "/admin/detail"));

    adminMenuGroup.add(createControlMemberMenu());  // 회원 관리
    adminMenuGroup.add(createControlStudyMenu());   // 스터디 관리
    adminMenuGroup.add(createControlReviewMenu());  // 장소 관리
    adminMenuGroup.add(createAdminCSMenu());        // 고객센터 관리

    return adminMenuGroup;
  }

  // 관리자 하위 메뉴2 - 회원 관리
  private Menu createControlMemberMenu() {
    MenuGroup adminUserMenu = new MenuGroup("회원 관리", ADMIN_LOGIN); 

    adminUserMenu.add(new MenuItem("개인 회원 조회", "/adminMember/list"));
    adminUserMenu.add(new MenuItem("개인 회원 상세", "/adminMember/detail"));
    adminUserMenu.add(new MenuItem("사장 회원 조회", "/adminCeoMember/list"));
    adminUserMenu.add(new MenuItem("사장 회원 상세", "/adminCeoMember/detail"));

    return adminUserMenu;
  }

  // 관리자 하위 메뉴3 - 스터디 관리
  private Menu createControlStudyMenu() {
    MenuGroup adminStudyMenu = new MenuGroup("스터디 관리", ADMIN_LOGIN); 

    adminStudyMenu.add(new MenuItem("목록","/study/list"));
    adminStudyMenu.add(new MenuItem("삭제","/study/delete"));
    return adminStudyMenu;
  }

  // 관리자 하위 메뉴4 - 장소 후기 관리
  private Menu createControlReviewMenu() {
    MenuGroup adminCafeReviewMenu = new MenuGroup("장소 관리", ADMIN_LOGIN); 

    adminCafeReviewMenu.add(new MenuItem("장소 게시글 관리","/cafe/control"));
    adminCafeReviewMenu.add(new MenuItem("장소 리뷰 관리","/cafe/reviewList")); 

    return adminCafeReviewMenu;
  }

  //관리자 하위 메뉴5 - 고객센터 관리
  private Menu createAdminCSMenu() {
    MenuGroup csMenu = new MenuGroup("고객센터 관리", ADMIN_LOGIN);

    csMenu.add(createAdminNoticeMenu());
    csMenu.add(createAdminAskMenu());

    return csMenu;
  }

  // 5-1
  private Menu createAdminNoticeMenu() {
    MenuGroup adminNoticeMenu = new MenuGroup("공지사항"); 

    adminNoticeMenu.add(new MenuItem("등록", "/adminNotice/add"));
    adminNoticeMenu.add(new MenuItem("목록", "/adminNotice/list"));
    adminNoticeMenu.add(new MenuItem("상세", "/adminNotice/detail"));
    adminNoticeMenu.add(new MenuItem("수정", "/adminNotice/update"));
    adminNoticeMenu.add(new MenuItem("삭제", "/adminNotice/delete"));

    return adminNoticeMenu;
  }

  // 5-2
  private Menu createAdminAskMenu() {
    MenuGroup adminaskMenu = new MenuGroup("문의사항");

    adminaskMenu.add(new MenuItem("목록", "/askBoard/list"));
    adminaskMenu.add(new MenuItem("상세", "/askBoard/detail"));
    adminaskMenu.add(new MenuItem("삭제", "/askBoard/delete"));

    return adminaskMenu;
  }

  // -----------------------------------------------------------------------------------------------
  // 개인 회원 메인
  Menu createMemberMenu() {
    MenuGroup userMenuGroup = new MenuGroup("오늘의 공부"); 

    userMenuGroup.add(new MenuItem("회원가입", LOGOUT, "/member/add"));
    userMenuGroup.add(new MenuItem("로그아웃", PER_LOGIN, "/member/logout"));
    userMenuGroup.add(new MenuItem("로그인", LOGOUT, "/member/login"));
    userMenuGroup.add(new MenuItem("ID/PW 찾기", LOGOUT, "/member/findIdPw"));

    userMenuGroup.add(createMyPageMenu());      // 마이페이지
    userMenuGroup.add(createStudyMenu());       // 스터디 찾기

    userMenuGroup.add(createMystudyMenu());     // 내 스터디

    userMenuGroup.add(createCafeMenu());        // 장소 예약하기
    userMenuGroup.add(createMemberCSMenu());          // 고객센터

    return userMenuGroup;
  }

  // 개인 하위 메뉴2 - 마이페이지 (로그인 했을때)
  private Menu createMyPageMenu() {
    MenuGroup myPageMenu = new MenuGroup("마이 페이지", PER_LOGIN); 

    myPageMenu.add(new MenuItem("개인정보", "/member/detail"));
    myPageMenu.add(new MenuItem("문의내역", "/askBoard/myList"));
    myPageMenu.add(new MenuItem("예약내역", "/cafeReservation/list"));
    myPageMenu.add(new MenuItem("후기내역", "/cafe/myReviewList"));
    myPageMenu.add(new MenuItem("탈퇴하기", "/member/delete"));
    return myPageMenu;
  }

  //개인 하위 메뉴3 - 스터디 찾기
  private Menu createStudyMenu() {
    MenuGroup allStudyMenu = new MenuGroup("스터디 찾기"); 

    allStudyMenu.add(new MenuItem("등록", PER_LOGIN, "/study/add"));
    allStudyMenu.add(new MenuItem("목록","/study/list"));
    allStudyMenu.add(new MenuItem("검색","/study/search"));
    allStudyMenu.add(new MenuItem("상세","/study/detail"));

    return allStudyMenu; 
  }

  // 이거 일단 다 보이게 하고 들어갔을 때 if문으로 필터하기 !!!!!!!
  // (조장 아니면 들어는 갈 수 있는데 if문으로 팅김)
  //개인 하위 메뉴4 - 내 스터디
  private Menu createMystudyMenu() {
    MenuGroup myStudyMenu = new MenuGroup("내 스터디", PER_LOGIN);
    myStudyMenu.add(new MenuItem("목록", "/myStudy/list"));
    myStudyMenu.add(new MenuItem("상세", "/myStudy/detail"));
    myStudyMenu.add(new MenuItem("수정", "/myStudy/update"));
    myStudyMenu.add(new MenuItem("삭제", "/myStudy/delete"));

    return myStudyMenu; 
  }

  //개인 하위 메뉴5 - 스터디 장소
  private Menu createCafeMenu() {
    MenuGroup cafeMenu = new MenuGroup("장소 예약"); 

    //cafeMenu.add(new MenuItem("등록", "/cafe/add")); // 기업권한
    cafeMenu.add(new MenuItem("목록", "/cafe/list"));
    cafeMenu.add(new MenuItem("검색", "/cafe/search"));
    cafeMenu.add(new MenuItem("상세", "/cafe/detail"));
    //cafeMenu.add(new MenuItem("수정", "/cafe/update")); // 기업권한
    //cafeMenu.add(new MenuItem("삭제", "/cafe/delete")); // 기업권한

    return cafeMenu;
  }

  //개인 하위 메뉴6 - 고객센터
  private Menu createMemberCSMenu() {
    MenuGroup memberCSMenu = new MenuGroup("고객센터");

    memberCSMenu.add(createMemberNoticeMenu());
    memberCSMenu.add(createMemberAskBoardMenu());

    return memberCSMenu;
  }

  // 6-1
  private Menu createMemberNoticeMenu() {
    MenuGroup noticeMenu = new MenuGroup("공지사항"); 

    noticeMenu.add(new MenuItem("목록", "/adminNotice/list"));
    noticeMenu.add(new MenuItem("상세", "/adminNotice/detail"));

    return noticeMenu;
  }

  // 6-2
  // 문의사항 상세보기 (댓글 목록 조회만) >> 회원 권한
  private Menu createMemberAskBoardMenu() {
    MenuGroup askBoardMenu = new MenuGroup("문의사항");

    askBoardMenu.add(new MenuItem("등록", PER_LOGIN, "/askBoard/add"));
    askBoardMenu.add(new MenuItem("목록", "/askBoard/list"));
    askBoardMenu.add(new MenuItem("상세", "/askBoard/detail"));
    askBoardMenu.add(new MenuItem("변경", PER_LOGIN, "/askBoard/update"));
    askBoardMenu.add(new MenuItem("삭제", PER_LOGIN, "/askBoard/delete"));

    return askBoardMenu;
  }

  //-----------------------------------------------------------------------------------------------

  // 기업
  Menu createCeoMenu() {
    MenuGroup ceoMemberMenuGroup = new MenuGroup("오늘의 공부 - 사장님");

    ceoMemberMenuGroup.add(new MenuItem("회원가입", LOGOUT, "/ceoMember/add"));
    ceoMemberMenuGroup.add(new MenuItem("로그인", LOGOUT, "/ceoMember/login"));
    ceoMemberMenuGroup.add(new MenuItem("ID/PW 찾기", LOGOUT, "/ceoMember/findIdPw"));
    ceoMemberMenuGroup.add(new MenuItem("로그아웃", CEO_LOGIN, "/ceoMember/logout"));

    ceoMemberMenuGroup.add(createCeoPageMenu());      // 마이페이지

    ceoMemberMenuGroup.add(createCeoCSMenu());          // 고객센터

    return ceoMemberMenuGroup;
  }

  // 기업 정보 >> 로그인하라고 뜸
  private Menu createCeoPageMenu() {
    MenuGroup ceoPageMenu = new MenuGroup("마이 페이지", CEO_LOGIN); 

    ceoPageMenu.add(new MenuItem("기업 프로필", "/ceoMember/detail"));
    //ceoPageMenu.add(new MenuItem("카페 등록", "/cafe/add"));
    ceoPageMenu.add(new MenuItem("카페 목록", "/ceoMember/myCafeList"));
    ceoPageMenu.add(new MenuItem("문의내역", "/askBoard/myList"));
    ceoPageMenu.add(new MenuItem("예약내역", "/ceoMember/ReservationList"));
    //    ceoPageMenu.add(new MenuItem("후기내역", "/cafe/myReviewList"));
    //    ceoPageMenu.add(new MenuItem("탈퇴하기", "/member/delete"));

    return ceoPageMenu;
  }


  //기업 하위 메뉴6 - 고객센터
  private Menu createCeoCSMenu() {
    MenuGroup memberCSMenu = new MenuGroup("고객센터");

    memberCSMenu.add(createCeoNoticeMenu());
    memberCSMenu.add(createCeoAskBoardMenu());

    return memberCSMenu;
  }

  // 6-1
  private Menu createCeoNoticeMenu() {
    MenuGroup noticeMenu = new MenuGroup("공지사항"); 

    noticeMenu.add(new MenuItem("목록", "/adminNotice/list"));
    noticeMenu.add(new MenuItem("상세", "/adminNotice/detail"));

    return noticeMenu;
  }

  // 6-2
  // 문의사항 상세보기 (댓글 목록 조회만) >> 기업 권한
  private Menu createCeoAskBoardMenu() {
    MenuGroup askBoardMenu = new MenuGroup("문의사항");

    askBoardMenu.add(new MenuItem("등록", CEO_LOGIN, "/askBoard/add"));
    askBoardMenu.add(new MenuItem("목록", "/askBoard/list"));
    askBoardMenu.add(new MenuItem("상세", "/askBoard/detail"));
    askBoardMenu.add(new MenuItem("변경", CEO_LOGIN, "/askBoard/update"));
    askBoardMenu.add(new MenuItem("삭제", CEO_LOGIN, "/askBoard/delete"));

    return askBoardMenu;
  }

}