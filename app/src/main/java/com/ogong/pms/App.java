package com.ogong.pms;

import static com.ogong.menu.Menu.ADMIN_LOGIN;
import static com.ogong.menu.Menu.CEO_LOGIN;
import static com.ogong.menu.Menu.LOGOUT;
import static com.ogong.menu.Menu.PER_LOGIN;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import com.ogong.context.ApplicationContextListener;
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
import com.ogong.pms.domain.Reply;
import com.ogong.pms.domain.Study;
import com.ogong.pms.domain.ToDo;
import com.ogong.pms.handler.AuthAdminLoginHandler;
import com.ogong.pms.handler.AuthAdminLogoutHandler;
import com.ogong.pms.handler.AuthCeoMemberLoginHandler;
import com.ogong.pms.handler.AuthCeoMemberLogoutHandler;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.AuthPerMemberLogoutHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.pms.handler.PromptCafe;
import com.ogong.pms.handler.PromptCeoMember;
import com.ogong.pms.handler.PromptPerMember;
import com.ogong.pms.handler.PromptStudy;
import com.ogong.pms.handler.admin.AdminCafeControlHandler;
import com.ogong.pms.handler.admin.AdminCafeReviewListControlHandler;
import com.ogong.pms.handler.admin.AdminCeoMemberDeleteHandler;
import com.ogong.pms.handler.admin.AdminCeoMemberDetailHandler;
import com.ogong.pms.handler.admin.AdminCeoMemberListHandler;
import com.ogong.pms.handler.admin.AdminCeoMemberUpdateHandler;
import com.ogong.pms.handler.admin.AdminDetailHandler;
import com.ogong.pms.handler.admin.AdminMemberDeleteHandler;
import com.ogong.pms.handler.admin.AdminMemberDetailHandler;
import com.ogong.pms.handler.admin.AdminMemberListHandler;
import com.ogong.pms.handler.admin.AdminMemberUpdateHandler;
import com.ogong.pms.handler.admin.AdminNoticeAddHandler;
import com.ogong.pms.handler.admin.AdminNoticeDeleteHandler;
import com.ogong.pms.handler.admin.AdminNoticeDetailHandler;
import com.ogong.pms.handler.admin.AdminNoticeListHandler;
import com.ogong.pms.handler.admin.AdminNoticeUpdateHandler;
import com.ogong.pms.handler.admin.AdminStudyDeleteHandler;
import com.ogong.pms.handler.admin.AdminUpdateHandler;
import com.ogong.pms.handler.board.AskBoardAddHandler;
import com.ogong.pms.handler.board.AskBoardDeleteHandler;
import com.ogong.pms.handler.board.AskBoardDetailHandler;
import com.ogong.pms.handler.board.AskBoardListHandler;
import com.ogong.pms.handler.board.AskBoardMyListHandler;
import com.ogong.pms.handler.board.AskBoardUpdateHandler;
import com.ogong.pms.handler.board.ReplyAddHandler;
import com.ogong.pms.handler.board.ReplyDetailHandler;
import com.ogong.pms.handler.cafe.CafeDetailHandler;
import com.ogong.pms.handler.cafe.CafeListHandler;
import com.ogong.pms.handler.cafe.CafeMyReservationDetailHandler;
import com.ogong.pms.handler.cafe.CafeMyReservationListHandler;
import com.ogong.pms.handler.cafe.CafeMyReviewListHandler;
import com.ogong.pms.handler.cafe.CafeSearchHandler;
import com.ogong.pms.handler.cafe.CeoCafeAddHandler;
import com.ogong.pms.handler.cafe.CeoCafeDeleteHandler;
import com.ogong.pms.handler.cafe.CeoCafeDetailHandler;
import com.ogong.pms.handler.cafe.CeoCafeListHandler;
import com.ogong.pms.handler.cafe.CeoCafeUpdateHandler;
import com.ogong.pms.handler.cafe.CeoReservationListHandler;
import com.ogong.pms.handler.member.CeoAddHandler;
import com.ogong.pms.handler.member.CeoDeleteHandler;
import com.ogong.pms.handler.member.CeoDetailHandler;
import com.ogong.pms.handler.member.CeoFindIdPwHandler;
import com.ogong.pms.handler.member.CeoUpdateHandler;
import com.ogong.pms.handler.member.MemberAddHandler;
import com.ogong.pms.handler.member.MemberDeleteHandler;
import com.ogong.pms.handler.member.MemberDetailHandler;
import com.ogong.pms.handler.member.MemberFindIdPwHandler;
import com.ogong.pms.handler.member.MemberUpdateHandler;
import com.ogong.pms.handler.mystudy.MyStudyCalender;
import com.ogong.pms.handler.mystudy.MyStudyDeleteHandler;
import com.ogong.pms.handler.mystudy.MyStudyDetailHandler;
import com.ogong.pms.handler.mystudy.MyStudyExit;
import com.ogong.pms.handler.mystudy.MyStudyFreeBoard;
import com.ogong.pms.handler.mystudy.MyStudyGuilder;
import com.ogong.pms.handler.mystudy.MyStudyGuilderDelete;
import com.ogong.pms.handler.mystudy.MyStudyGuilderEntrust;
import com.ogong.pms.handler.mystudy.MyStudyGuilderList;
import com.ogong.pms.handler.mystudy.MyStudyListHandler;
import com.ogong.pms.handler.mystudy.MyStudyToDo;
import com.ogong.pms.handler.mystudy.MyStudyUpdateHandler;
import com.ogong.pms.handler.study.StudyAddHandler;
import com.ogong.pms.handler.study.StudyDetailHandler;
import com.ogong.pms.handler.study.StudyListHandler;
import com.ogong.pms.handler.study.StudySearchHandler;
import com.ogong.pms.listener.AppInitListener;
import com.ogong.pms.listener.FileListener;
import com.ogong.util.Prompt;
import com.ogong.util.RandomPw;

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

  // ????????? ??????(0904)
  HashMap<String, Command> commandMap = new HashMap<>();

  PromptPerMember promptPerMember = new PromptPerMember(memberList); 
  PromptCeoMember promptCeoMember = new PromptCeoMember(ceoMemberList);
  PromptCafe promptcafe = new PromptCafe(cafeList, cafeReviewList);
  PromptStudy promptStudy = new PromptStudy(studyList);

  //=> ?????????(?????????) ??????
  List<ApplicationContextListener> listeners = new ArrayList<>();

  //=> ?????????(?????????)??? ???????????? ?????????
  public void addApplicationContextListener(ApplicationContextListener listener) {
    this.listeners.add(listener);
  }

  // => ?????????(?????????)??? ???????????? ?????????
  public void removeApplicationContextListener(ApplicationContextListener listener) {
    this.listeners.remove(listener);
  }


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
        System.out.printf("%s ????????? ???????????? ??? ?????? ??????!\n", menuId);
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    App app = new App(); 

    app.addApplicationContextListener(new AppInitListener());
    app.addApplicationContextListener(new FileListener());

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

    ReplyAddHandler replyAddHandler = new ReplyAddHandler();
    ReplyDetailHandler replyDetailHandler = new ReplyDetailHandler();
    List<Reply> replyList = new ArrayList<>();
    commandMap.put("/askBoard/add",  
        new AskBoardAddHandler(askBoardList, memberList, ceoMemberList, replyList));
    commandMap.put("/askBoard/list", 
        new AskBoardListHandler(askBoardList, memberList, ceoMemberList, replyList));
    commandMap.put("/askBoard/detail", 
        new AskBoardDetailHandler(
            askBoardList, memberList, ceoMemberList, replyList, replyAddHandler, replyDetailHandler));
    commandMap.put("/askBoard/update", 
        new AskBoardUpdateHandler(askBoardList, memberList, ceoMemberList, replyList));
    commandMap.put("/askBoard/delete", 
        new AskBoardDeleteHandler(askBoardList, memberList, ceoMemberList, replyList));
    commandMap.put("/askBoard/myList", 
        new AskBoardMyListHandler(askBoardList, memberList, ceoMemberList, replyList, replyDetailHandler));

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

    RandomPw randomPw = new RandomPw();
    commandMap.put("/member/findIdPw", new MemberFindIdPwHandler(promptPerMember, randomPw));
    commandMap.put("/member/login", new AuthPerMemberLoginHandler(promptPerMember, memberList));
    commandMap.put("/member/logout", new AuthPerMemberLogoutHandler());

    commandMap.put("/admin/detail", new AdminDetailHandler(adminList));
    commandMap.put("/admin/update", new AdminUpdateHandler(adminList));

    commandMap.put("/study/add", new StudyAddHandler(studyList, toDoList, promptPerMember));
    commandMap.put("/study/list", new StudyListHandler(studyList));
    commandMap.put("/study/detail", new StudyDetailHandler(studyList, promptStudy));
    commandMap.put("/study/search", new StudySearchHandler(studyList));
    commandMap.put("/study/delete", new AdminStudyDeleteHandler(studyList, promptStudy));


    // ??? ????????? ??????
    MyStudyCalender myStudyCalender = new MyStudyCalender(calenderList, studyList);
    MyStudyToDo myStudyToDo = new MyStudyToDo(toDoList, studyList);
    MyStudyFreeBoard myStudyFreeBoard = new MyStudyFreeBoard(freeBoardList, commentList, memberList, studyList);
    MyStudyGuilderList myStudyGuilderList = new MyStudyGuilderList();
    MyStudyGuilderDelete myStudyGuilderDelete = new MyStudyGuilderDelete();
    MyStudyGuilderEntrust myStudyGuilderEntrust = new MyStudyGuilderEntrust();
    MyStudyGuilder myStudyGuilder = new MyStudyGuilder(myStudyGuilderList, myStudyGuilderDelete, myStudyGuilderEntrust);
    MyStudyExit myStudyExit = new MyStudyExit();
    // ??? ????????? 
    commandMap.put("/myStudy/detail", new MyStudyDetailHandler(studyList, myStudyToDo,
        myStudyCalender, myStudyFreeBoard, myStudyGuilder, myStudyExit, commentList, promptStudy));
    commandMap.put("/myStudy/delete", new MyStudyDeleteHandler(studyList, promptStudy));
    commandMap.put("/myStudy/list", new MyStudyListHandler(studyList));
    commandMap.put("/myStudy/update", new MyStudyUpdateHandler(studyList, promptStudy));

  }

  private void notifyOnApplicationStarted() {
    HashMap<String,Object> params = new HashMap<>();
    params.put("memberList", memberList);
    params.put("ceoMemberList", ceoMemberList);
    params.put("adminList", adminList);
    params.put("adminNoticeList", adminNoticeList);
    params.put("askBoardList", askBoardList);
    params.put("cafeList", cafeList);
    params.put("cafeReservationList", cafeReservationList);
    params.put("cafeReviewList", cafeReviewList);
    params.put("cafeRoomList", cafeRoomList);
    params.put("studyList", studyList);
    params.put("toDoList", toDoList);
    params.put("calenderList", calenderList);
    params.put("freeBoardList", freeBoardList);

    for (ApplicationContextListener listener : listeners) {
      listener.contextInitialized(params);
    }
  }

  private void notifyOnApplicationEnded() {
    HashMap<String,Object> params = new HashMap<>();
    params.put("memberList", memberList);
    params.put("ceoMemberList", ceoMemberList);
    params.put("adminList", adminList);
    params.put("adminNoticeList", adminNoticeList);
    params.put("askBoardList", askBoardList);
    params.put("cafeList", cafeList);
    params.put("cafeReservationList", cafeReservationList);
    params.put("cafeReviewList", cafeReviewList);
    params.put("cafeRoomList", cafeRoomList);
    params.put("studyList", studyList);
    params.put("toDoList", toDoList);
    params.put("calenderList", calenderList);
    params.put("freeBoardList", freeBoardList);

    for (ApplicationContextListener listener : listeners) {
      listener.contextDestroyed(params);
    }
  }

  void welcomeservice() {
    welcome().execute();
    service();
  }

  void service() {
    notifyOnApplicationStarted();

    createMenu().execute();
    Prompt.close();

    notifyOnApplicationEnded();
  }

  static Menu welcome() {
    MenuGroup welcomeMenuGroup = new MenuGroup("????????? ?????????????????????");
    welcomeMenuGroup.setPrevMenuTitle("??????");
    return welcomeMenuGroup;
  }

  //--------------------------------------------------------
  Menu createMenu() {

    MenuGroup mainMenuGroup = new MenuGroup("??????");
    mainMenuGroup.setPrevMenuTitle("??????");

    mainMenuGroup.add(createAdminMenu());
    mainMenuGroup.add(createMemberMenu());
    mainMenuGroup.add(createCeoMenu());

    return mainMenuGroup;
  }




  // -----------------------------------------------------------------------------------------------
  // ????????? ??????
  Menu createAdminMenu() {
    MenuGroup adminMenuGroup = new MenuGroup("?????????");

    adminMenuGroup.add(new MenuItem("?????????", LOGOUT, "/admin/login"));
    adminMenuGroup.add(new MenuItem("????????????", ADMIN_LOGIN, "/admin/logout"));
    adminMenuGroup.add(new MenuItem("?????? ?????????", ADMIN_LOGIN, "/admin/detail"));

    adminMenuGroup.add(createControlMemberMenu());  // ?????? ??????
    adminMenuGroup.add(createControlStudyMenu());   // ????????? ??????
    adminMenuGroup.add(createControlReviewMenu());  // ?????? ??????
    adminMenuGroup.add(createAdminCSMenu());        // ???????????? ??????

    return adminMenuGroup;
  }

  // ????????? ?????? ??????2 - ?????? ??????
  private Menu createControlMemberMenu() {
    MenuGroup adminUserMenu = new MenuGroup("?????? ??????", ADMIN_LOGIN); 

    adminUserMenu.add(new MenuItem("?????? ?????? ??????", "/adminMember/list"));
    adminUserMenu.add(new MenuItem("?????? ?????? ??????", "/adminMember/detail"));
    adminUserMenu.add(new MenuItem("?????? ?????? ??????", "/adminCeoMember/list"));
    adminUserMenu.add(new MenuItem("?????? ?????? ??????", "/adminCeoMember/detail"));

    return adminUserMenu;
  }

  // ????????? ?????? ??????3 - ????????? ??????
  private Menu createControlStudyMenu() {
    MenuGroup adminStudyMenu = new MenuGroup("????????? ??????", ADMIN_LOGIN); 

    adminStudyMenu.add(new MenuItem("??????","/study/list"));
    adminStudyMenu.add(new MenuItem("??????","/study/delete"));
    return adminStudyMenu;
  }

  // ????????? ?????? ??????4 - ?????? ?????? ??????
  private Menu createControlReviewMenu() {
    MenuGroup adminCafeReviewMenu = new MenuGroup("?????? ??????", ADMIN_LOGIN); 

    adminCafeReviewMenu.add(new MenuItem("?????? ????????? ??????","/cafe/control"));
    adminCafeReviewMenu.add(new MenuItem("?????? ?????? ??????","/cafe/reviewList")); 

    return adminCafeReviewMenu;
  }

  //????????? ?????? ??????5 - ???????????? ??????
  private Menu createAdminCSMenu() {
    MenuGroup csMenu = new MenuGroup("???????????? ??????", ADMIN_LOGIN);

    csMenu.add(createAdminNoticeMenu());
    csMenu.add(createAdminAskMenu());

    return csMenu;
  }

  // 5-1
  private Menu createAdminNoticeMenu() {
    MenuGroup adminNoticeMenu = new MenuGroup("????????????"); 

    adminNoticeMenu.add(new MenuItem("??????", "/adminNotice/add"));
    adminNoticeMenu.add(new MenuItem("??????", "/adminNotice/list"));
    adminNoticeMenu.add(new MenuItem("??????", "/adminNotice/detail"));

    return adminNoticeMenu;
  }

  // 5-2
  private Menu createAdminAskMenu() {
    MenuGroup adminaskMenu = new MenuGroup("????????????");

    adminaskMenu.add(new MenuItem("??????", "/askBoard/list"));
    adminaskMenu.add(new MenuItem("??????", "/askBoard/detail"));

    return adminaskMenu;
  }

  // -----------------------------------------------------------------------------------------------
  // ?????? ?????? ??????
  Menu createMemberMenu() {
    MenuGroup userMenuGroup = new MenuGroup("????????? ??????"); 

    userMenuGroup.add(new MenuItem("????????????", LOGOUT, "/member/add"));
    userMenuGroup.add(new MenuItem("????????????", PER_LOGIN, "/member/logout"));
    userMenuGroup.add(new MenuItem("?????????", LOGOUT, "/member/login"));
    userMenuGroup.add(new MenuItem("ID/PW ??????", LOGOUT, "/member/findIdPw"));

    userMenuGroup.add(createMyPageMenu());      // ???????????????
    userMenuGroup.add(createStudyMenu());       // ????????? ??????

    userMenuGroup.add(createMystudyMenu());     // ??? ?????????

    userMenuGroup.add(createCafeMenu());        // ?????? ????????????
    userMenuGroup.add(createMemberCSMenu());          // ????????????

    return userMenuGroup;
  }

  // ?????? ?????? ??????2 - ??????????????? (????????? ?????????)
  private Menu createMyPageMenu() {
    MenuGroup myPageMenu = new MenuGroup("?????? ?????????", PER_LOGIN); 

    myPageMenu.add(new MenuItem("????????????", "/member/detail"));
    myPageMenu.add(new MenuItem("????????????", "/askBoard/myList"));
    myPageMenu.add(new MenuItem("????????????", "/cafeReservation/list"));
    myPageMenu.add(new MenuItem("????????????", "/cafe/myReviewList"));
    myPageMenu.add(new MenuItem("????????????", "/member/delete"));
    return myPageMenu;
  }

  //?????? ?????? ??????3 - ????????? ??????
  private Menu createStudyMenu() {
    MenuGroup allStudyMenu = new MenuGroup("????????? ??????"); 

    allStudyMenu.add(new MenuItem("??????", PER_LOGIN, "/study/add"));
    allStudyMenu.add(new MenuItem("??????","/study/list"));
    allStudyMenu.add(new MenuItem("??????","/study/search"));
    allStudyMenu.add(new MenuItem("??????","/study/detail"));

    return allStudyMenu; 
  }

  // ?????? ?????? ??? ????????? ?????? ???????????? ??? if????????? ???????????? !!!!!!!
  // (?????? ????????? ????????? ??? ??? ????????? if????????? ??????)
  //?????? ?????? ??????4 - ??? ?????????
  private Menu createMystudyMenu() {
    MenuGroup myStudyMenu = new MenuGroup("??? ?????????", PER_LOGIN);
    myStudyMenu.add(new MenuItem("??????", "/myStudy/list"));
    myStudyMenu.add(new MenuItem("??????", "/myStudy/detail"));

    return myStudyMenu; 
  }

  //?????? ?????? ??????5 - ????????? ??????
  private Menu createCafeMenu() {
    MenuGroup cafeMenu = new MenuGroup("?????? ??????"); 

    cafeMenu.add(new MenuItem("??????", "/cafe/list"));
    cafeMenu.add(new MenuItem("??????", "/cafe/search"));
    cafeMenu.add(new MenuItem("??????", "/cafe/detail"));

    return cafeMenu;
  }

  //?????? ?????? ??????6 - ????????????
  private Menu createMemberCSMenu() {
    MenuGroup memberCSMenu = new MenuGroup("????????????");

    memberCSMenu.add(createMemberNoticeMenu());
    memberCSMenu.add(createMemberAskBoardMenu());

    return memberCSMenu;
  }

  // 6-1
  private Menu createMemberNoticeMenu() {
    MenuGroup noticeMenu = new MenuGroup("????????????"); 

    noticeMenu.add(new MenuItem("??????", "/adminNotice/list"));
    noticeMenu.add(new MenuItem("??????", "/adminNotice/detail"));

    return noticeMenu;
  }

  // 6-2
  // ???????????? ???????????? (?????? ?????? ?????????) >> ?????? ??????
  private Menu createMemberAskBoardMenu() {
    MenuGroup askBoardMenu = new MenuGroup("????????????");

    askBoardMenu.add(new MenuItem("??????", PER_LOGIN, "/askBoard/add"));
    askBoardMenu.add(new MenuItem("??????", "/askBoard/list"));
    askBoardMenu.add(new MenuItem("??????", "/askBoard/detail"));

    return askBoardMenu;
  }

  //-----------------------------------------------------------------------------------------------

  // ??????
  Menu createCeoMenu() {
    MenuGroup ceoMemberMenuGroup = new MenuGroup("????????? ?????? - ?????????");

    ceoMemberMenuGroup.add(new MenuItem("????????????", LOGOUT, "/ceoMember/add"));
    ceoMemberMenuGroup.add(new MenuItem("?????????", LOGOUT, "/ceoMember/login"));
    ceoMemberMenuGroup.add(new MenuItem("ID/PW ??????", LOGOUT, "/ceoMember/findIdPw"));
    ceoMemberMenuGroup.add(new MenuItem("????????????", CEO_LOGIN, "/ceoMember/logout"));

    ceoMemberMenuGroup.add(createCeoPageMenu());      // ???????????????

    ceoMemberMenuGroup.add(createCeoCSMenu());          // ????????????

    return ceoMemberMenuGroup;
  }

  // ?????? ?????? >> ?????????????????? ???
  private Menu createCeoPageMenu() {
    MenuGroup ceoPageMenu = new MenuGroup("?????? ?????????", CEO_LOGIN); 

    ceoPageMenu.add(new MenuItem("?????? ?????????", "/ceoMember/detail"));
    //ceoPageMenu.add(new MenuItem("?????? ??????", "/cafe/add"));
    ceoPageMenu.add(new MenuItem("?????? ??????", "/ceoMember/myCafeList"));
    ceoPageMenu.add(new MenuItem("????????????", "/askBoard/myList"));
    ceoPageMenu.add(new MenuItem("????????????", "/ceoMember/ReservationList"));
    //    ceoPageMenu.add(new MenuItem("????????????", "/cafe/myReviewList"));
    //    ceoPageMenu.add(new MenuItem("????????????", "/member/delete"));

    return ceoPageMenu;
  }


  //?????? ?????? ??????6 - ????????????
  private Menu createCeoCSMenu() {
    MenuGroup memberCSMenu = new MenuGroup("????????????");

    memberCSMenu.add(createCeoNoticeMenu());
    memberCSMenu.add(createCeoAskBoardMenu());

    return memberCSMenu;
  }

  // 6-1
  private Menu createCeoNoticeMenu() {
    MenuGroup noticeMenu = new MenuGroup("????????????"); 

    noticeMenu.add(new MenuItem("??????", "/adminNotice/list"));
    noticeMenu.add(new MenuItem("??????", "/adminNotice/detail"));

    return noticeMenu;
  }

  // 6-2
  // ???????????? ???????????? (?????? ?????? ?????????) >> ?????? ??????
  private Menu createCeoAskBoardMenu() {
    MenuGroup askBoardMenu = new MenuGroup("????????????");

    askBoardMenu.add(new MenuItem("??????", CEO_LOGIN, "/askBoard/add"));
    askBoardMenu.add(new MenuItem("??????", "/askBoard/list"));
    askBoardMenu.add(new MenuItem("??????", "/askBoard/detail"));

    return askBoardMenu;
  }

}