package com.ogong.pms;

import static com.ogong.menu.Menu.ADMIN_LOGIN;
import static com.ogong.menu.Menu.CEO_LOGIN;
import static com.ogong.menu.Menu.LOGOUT;
import static com.ogong.menu.Menu.PER_LOGIN;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.ogong.context.ApplicationContextListener;
import com.ogong.menu.Menu;
import com.ogong.menu.MenuFilter;
import com.ogong.menu.MenuGroup;
import com.ogong.pms.dao.AdminDao;
import com.ogong.pms.dao.AskBoardDao;
import com.ogong.pms.dao.CafeDao;
import com.ogong.pms.dao.CafeReservationDao;
import com.ogong.pms.dao.CafeReviewDao;
import com.ogong.pms.dao.CafeRoomDao;
import com.ogong.pms.dao.CeoMemberDao;
import com.ogong.pms.dao.CommentDao;
import com.ogong.pms.dao.FreeBoardDao;
import com.ogong.pms.dao.MemberDao;
import com.ogong.pms.dao.NoticeDao;
import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.dao.ToDoDao;
import com.ogong.pms.handler.AbstractLoginHandler;
import com.ogong.pms.handler.AuthAdminLoginHandler;
import com.ogong.pms.handler.AuthAdminLogoutHandler;
import com.ogong.pms.handler.AuthCeoMemberLoginHandler;
import com.ogong.pms.handler.AuthCeoMemberLogoutHandler;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.AuthPerMemberLogoutHandler;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.pms.handler.Askboard.AskBoardAddHandler;
import com.ogong.pms.handler.Askboard.AskBoardCeoMyDetailHandler;
import com.ogong.pms.handler.Askboard.AskBoardCeoMyListHandler;
import com.ogong.pms.handler.Askboard.AskBoardDeleteHandler;
import com.ogong.pms.handler.Askboard.AskBoardPerMyDetailHandler;
import com.ogong.pms.handler.Askboard.AskBoardPerMyListHandler;
import com.ogong.pms.handler.Askboard.AskBoardUpdateHandler;
import com.ogong.pms.handler.Askboard.ReplyAddHandler;
import com.ogong.pms.handler.Askboard.ReplyDetailHandler;
import com.ogong.pms.handler.admin.AdminAskBoardDetailHandler;
import com.ogong.pms.handler.admin.AdminAskBoardListHandler;
import com.ogong.pms.handler.admin.AdminCafeApprovalHandler;
import com.ogong.pms.handler.admin.AdminCafeControlHandler;
import com.ogong.pms.handler.admin.AdminCafeDeleteHandler;
import com.ogong.pms.handler.admin.AdminCafeDetailHandler;
import com.ogong.pms.handler.admin.AdminCafeReviewListControlHandler;
import com.ogong.pms.handler.admin.AdminCafeReviewListDeleteHandler;
import com.ogong.pms.handler.admin.AdminCeoMemberDeleteHandler;
import com.ogong.pms.handler.admin.AdminCeoMemberDetailHandler;
import com.ogong.pms.handler.admin.AdminCeoMemberListHandler;
import com.ogong.pms.handler.admin.AdminDetailHandler;
import com.ogong.pms.handler.admin.AdminMemberDeleteHandler;
import com.ogong.pms.handler.admin.AdminMemberDetailHandler;
import com.ogong.pms.handler.admin.AdminMemberListHandler;
import com.ogong.pms.handler.admin.AdminNoticeAddHandler;
import com.ogong.pms.handler.admin.AdminNoticeDeleteHandler;
import com.ogong.pms.handler.admin.AdminNoticeDetailHandler;
import com.ogong.pms.handler.admin.AdminNoticeListHandler;
import com.ogong.pms.handler.admin.AdminNoticeUpdateHandler;
import com.ogong.pms.handler.admin.AdminStudyDeleteHandler;
import com.ogong.pms.handler.admin.AdminUpdateHandler;
import com.ogong.pms.handler.cafe.CafeDetailHandler;
import com.ogong.pms.handler.cafe.CafeListHandler;
import com.ogong.pms.handler.cafe.CafeMyReservationDeleteHandler;
import com.ogong.pms.handler.cafe.CafeMyReservationDetailHandler;
import com.ogong.pms.handler.cafe.CafeMyReservationListHandler;
import com.ogong.pms.handler.cafe.CafeMyReviewAddHandler;
import com.ogong.pms.handler.cafe.CafeMyReviewDeleteHandler;
import com.ogong.pms.handler.cafe.CafeMyReviewListHandler;
import com.ogong.pms.handler.cafe.CafeReservationHandler;
import com.ogong.pms.handler.cafe.CafeSearchHandler;
import com.ogong.pms.handler.ceoCafe.CeoCafeAddHandler;
import com.ogong.pms.handler.ceoCafe.CeoCafeDeleteHandler;
import com.ogong.pms.handler.ceoCafe.CeoCafeDetailHandler;
import com.ogong.pms.handler.ceoCafe.CeoCafeRoomAddHandler;
import com.ogong.pms.handler.ceoCafe.CeoCafeRoomDeleteHandler;
import com.ogong.pms.handler.ceoCafe.CeoCafeRoomDetailHandler;
import com.ogong.pms.handler.ceoCafe.CeoCafeRoomListHandler;
import com.ogong.pms.handler.ceoCafe.CeoCafeRoomUpdateHandler;
import com.ogong.pms.handler.ceoCafe.CeoCafeUpdateHandler;
import com.ogong.pms.handler.ceoCafe.CeoReservationDetailHandler;
import com.ogong.pms.handler.ceoCafe.CeoReservationRejectHandler;
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
import com.ogong.pms.handler.myStudy.MyStudyDeleteHandler;
import com.ogong.pms.handler.myStudy.MyStudyDetailHandler;
import com.ogong.pms.handler.myStudy.MyStudyExitHandler;
import com.ogong.pms.handler.myStudy.MyStudyListHandler;
import com.ogong.pms.handler.myStudy.MyStudyUpdateHandler;
import com.ogong.pms.handler.myStudy.calender.CalenderAddHandler;
import com.ogong.pms.handler.myStudy.calender.CalenderDeleteHandler;
import com.ogong.pms.handler.myStudy.calender.CalenderDetailHandler;
import com.ogong.pms.handler.myStudy.calender.CalenderListHandler;
import com.ogong.pms.handler.myStudy.calender.CalenderMonthListHandler;
import com.ogong.pms.handler.myStudy.calender.CalenderUpdateHandler;
import com.ogong.pms.handler.myStudy.freeBoard.CommentAddHandler;
import com.ogong.pms.handler.myStudy.freeBoard.CommentDeleteHandler;
import com.ogong.pms.handler.myStudy.freeBoard.CommentUpdateHandler;
import com.ogong.pms.handler.myStudy.freeBoard.FreeBoardAddHandler;
import com.ogong.pms.handler.myStudy.freeBoard.FreeBoardDeleteHandler;
import com.ogong.pms.handler.myStudy.freeBoard.FreeBoardDetailHandler;
import com.ogong.pms.handler.myStudy.freeBoard.FreeBoardListHandler;
import com.ogong.pms.handler.myStudy.freeBoard.FreeBoardUpdateHandler;
import com.ogong.pms.handler.myStudy.freeBoard.PromptFreeBoard;
import com.ogong.pms.handler.myStudy.guilder.GuilderDeleteHandler;
import com.ogong.pms.handler.myStudy.guilder.GuilderEntrustHandler;
import com.ogong.pms.handler.myStudy.guilder.GuilderListHandler;
import com.ogong.pms.handler.myStudy.guilder.WatingGuilderListHandler;
import com.ogong.pms.handler.myStudy.todo.ToDoAdd;
import com.ogong.pms.handler.myStudy.todo.ToDoDelete;
import com.ogong.pms.handler.myStudy.todo.ToDoDetail;
import com.ogong.pms.handler.myStudy.todo.ToDoList;
import com.ogong.pms.handler.myStudy.todo.ToDoUpdate;
import com.ogong.pms.handler.study.StudyAddHandler;
import com.ogong.pms.handler.study.StudyDetailHandler;
import com.ogong.pms.handler.study.StudyEndListHandler;
import com.ogong.pms.handler.study.StudyIngListHandler;
import com.ogong.pms.handler.study.StudyJoinHandler;
import com.ogong.pms.handler.study.StudyListHandler;
import com.ogong.pms.handler.study.StudySearchHandler;
import com.ogong.pms.handler.study.bookMark.StudyBookMarkAddHandler;
import com.ogong.pms.handler.study.bookMark.StudyBookMarkDeleteHandler;
import com.ogong.pms.handler.study.bookMark.StudyBookMarkDetailHandler;
import com.ogong.pms.handler.study.bookMark.StudyBookMarkListHandler;
import com.ogong.pms.listener.AppInitListener;
import com.ogong.request.RequestAgent;
import com.ogong.util.Prompt;
import com.ogong.util.RandomPw;

public class ClientApp {

  Connection con;
  RequestAgent requestAgent;

  HashMap<String, Command> commandMap = new HashMap<>();

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

  private void notifyOnApplicationStarted() {
    HashMap<String,Object> params = new HashMap<>();

    for (ApplicationContextListener listener : listeners) {
      listener.contextInitialized(params);
    }
  }

  private void notifyOnApplicationEnded() {
    HashMap<String,Object> params = new HashMap<>();

    for (ApplicationContextListener listener : listeners) {
      listener.contextDestroyed(params);
    }    
  }        

  public ClientApp() throws Exception {

    // ????????? ????????? ?????? ??????.
    requestAgent = null;

    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/ogong/pms/conf/mybatis-config.xml")).openSession();

    //    ??????????????? ????????????
    AdminDao adminDao = sqlSession.getMapper(AdminDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    CeoMemberDao ceoMemberDao = sqlSession.getMapper(CeoMemberDao.class);
    NoticeDao noticeDao = sqlSession.getMapper(NoticeDao.class);
    AskBoardDao askBoardDao = sqlSession.getMapper(AskBoardDao.class);
    CafeDao cafeDao = sqlSession.getMapper(CafeDao.class);
    CafeReservationDao cafeReservationDao = sqlSession.getMapper(CafeReservationDao.class);
    CafeReviewDao cafeReviewDao = sqlSession.getMapper(CafeReviewDao.class);
    CafeRoomDao cafeRoomDao = sqlSession.getMapper(CafeRoomDao.class);
    StudyDao studyDao = sqlSession.getMapper(StudyDao.class);
    FreeBoardDao freeBoardDao = sqlSession.getMapper(FreeBoardDao.class);
    ToDoDao toDoDao = sqlSession.getMapper(ToDoDao.class);
    CommentDao commentDao = sqlSession.getMapper(CommentDao.class); // ?????? ??? ???

    System.out.println("????????? ?????? ??????!"); // ?????? ?????????

    RandomPw randomPw = new RandomPw();
    commandMap.put("/member/login", new AuthPerMemberLoginHandler(memberDao));
    commandMap.put("/member/logout", new AuthPerMemberLogoutHandler());

    commandMap.put("/member/add", new MemberAddHandler(memberDao, sqlSession));
    commandMap.put("/member/detail", new MemberDetailHandler(memberDao));
    commandMap.put("/member/findIdPw", new MemberFindIdPwHandler(randomPw, memberDao, sqlSession));
    commandMap.put("/member/update", new MemberUpdateHandler(memberDao, sqlSession));
    commandMap.put("/member/delete", new MemberDeleteHandler(memberDao, studyDao, sqlSession));

    commandMap.put("/askBoard/add", new AskBoardAddHandler(askBoardDao, sqlSession));
    commandMap.put("/askBoard/update", new AskBoardUpdateHandler(askBoardDao, sqlSession));
    commandMap.put("/askBoard/delete", new AskBoardDeleteHandler(askBoardDao, sqlSession));
    commandMap.put("/askBoard/perMyList", new AskBoardPerMyListHandler(askBoardDao));
    commandMap.put("/askBoard/ceoMyList", new AskBoardCeoMyListHandler(askBoardDao));
    commandMap.put("/askBoard/perMydetail", new AskBoardPerMyDetailHandler(askBoardDao, sqlSession));
    commandMap.put("/askBoard/ceoMydetail", new AskBoardCeoMyDetailHandler(askBoardDao, sqlSession));

    commandMap.put("/askBoard/list", new AdminAskBoardListHandler(askBoardDao));
    commandMap.put("/askBoard/detail", new AdminAskBoardDetailHandler(askBoardDao, sqlSession));

    commandMap.put("/reply/add", new ReplyAddHandler(askBoardDao, sqlSession));
    commandMap.put("/reply/detail", new ReplyDetailHandler(askBoardDao));

    commandMap.put("/ceoMember/add", new CeoAddHandler(ceoMemberDao, sqlSession));
    commandMap.put("/ceoMember/detail", new CeoDetailHandler(ceoMemberDao));
    commandMap.put("/ceoMember/update", new CeoUpdateHandler(ceoMemberDao, sqlSession));
    commandMap.put("/ceoMember/delete", new CeoDeleteHandler(ceoMemberDao, sqlSession));
    commandMap.put("/ceoMember/login", new AuthCeoMemberLoginHandler(ceoMemberDao));
    commandMap.put("/ceoMember/logout", new AuthCeoMemberLogoutHandler());
    commandMap.put("/ceoMember/findIdPw", new CeoFindIdPwHandler(randomPw, ceoMemberDao, sqlSession));

    commandMap.put("/admin/login", new AuthAdminLoginHandler(adminDao));
    commandMap.put("/admin/logout", new AuthAdminLogoutHandler());

    commandMap.put("/admin/update", new AdminUpdateHandler(adminDao, sqlSession));
    commandMap.put("/admin/detail", new AdminDetailHandler(adminDao));

    commandMap.put("/adminCeoMember/list", new AdminCeoMemberListHandler(ceoMemberDao));
    commandMap.put("/adminCeoMember/detail", new AdminCeoMemberDetailHandler(ceoMemberDao));
    commandMap.put("/adminCeoMember/delete", new AdminCeoMemberDeleteHandler(ceoMemberDao));

    commandMap.put("/adminMember/list", new AdminMemberListHandler(memberDao));
    commandMap.put("/adminMember/detail", new AdminMemberDetailHandler(memberDao));
    commandMap.put("/adminMember/delete", new AdminMemberDeleteHandler(memberDao, studyDao, sqlSession));

    commandMap.put("/adminNotice/add", new AdminNoticeAddHandler(noticeDao, sqlSession));
    commandMap.put("/adminNotice/list", new AdminNoticeListHandler(noticeDao));
    commandMap.put("/adminNotice/detail", new AdminNoticeDetailHandler(noticeDao));
    commandMap.put("/adminNotice/update", new AdminNoticeUpdateHandler(noticeDao, sqlSession));
    commandMap.put("/adminNotice/delete", new AdminNoticeDeleteHandler(noticeDao, sqlSession));

    commandMap.put("/study/delete", new AdminStudyDeleteHandler(studyDao, sqlSession));

    commandMap.put("/study/add", new StudyAddHandler(studyDao, sqlSession));
    commandMap.put("/study/list", new StudyListHandler(studyDao));
    commandMap.put("/studying/list", new StudyIngListHandler(studyDao));
    commandMap.put("/studyend/list", new StudyEndListHandler(studyDao));
    commandMap.put("/study/detail", new StudyDetailHandler(studyDao));
    commandMap.put("/study/search", new StudySearchHandler(studyDao));
    commandMap.put("/study/join", new StudyJoinHandler(studyDao, sqlSession));

    commandMap.put("/study/bookMarkAdd", new StudyBookMarkAddHandler(studyDao, sqlSession));
    commandMap.put("/study/bookMarkList", new StudyBookMarkListHandler(studyDao));
    commandMap.put("/study/bookMarkDetail", new StudyBookMarkDetailHandler(studyDao));
    commandMap.put("/study/bookMarkDelete", new StudyBookMarkDeleteHandler(studyDao, sqlSession));

    commandMap.put("/myStudy/list", new MyStudyListHandler(studyDao));
    commandMap.put("/myStudy/detail", new MyStudyDetailHandler(studyDao));
    commandMap.put("/myStudy/update", new MyStudyUpdateHandler(studyDao, sqlSession));
    commandMap.put("/myStudy/delete", new MyStudyDeleteHandler(studyDao, sqlSession));
    commandMap.put("/myStudy/exit", new MyStudyExitHandler(studyDao, sqlSession));
    commandMap.put("/myStudy/guilder", new GuilderListHandler(studyDao));

    commandMap.put("/myStudy/watingGuilderList", new WatingGuilderListHandler(studyDao));
    commandMap.put("/myStudy/guilderEntrust", new GuilderEntrustHandler(studyDao, sqlSession));
    commandMap.put("/myStudy/guilderDelete", new GuilderDeleteHandler(studyDao, sqlSession));

    commandMap.put("/myStudy/calenderAdd", new CalenderAddHandler(studyDao));
    commandMap.put("/myStudy/calenderList", new CalenderListHandler(studyDao));
    commandMap.put("/myStudy/calenderMonthList", new CalenderMonthListHandler(studyDao));
    commandMap.put("/myStudy/calenderDetail", new CalenderDetailHandler(studyDao));
    commandMap.put("/myStudy/calenderUpdate", new CalenderUpdateHandler(studyDao));
    commandMap.put("/myStudy/calenderDelete", new CalenderDeleteHandler(studyDao));

    PromptFreeBoard promptFreeBoard = new PromptFreeBoard(commentDao, freeBoardDao, sqlSession);
    commandMap.put("/myStudy/freeBoardList", new FreeBoardListHandler(freeBoardDao));
    commandMap.put("/myStudy/freeBoardAdd", new FreeBoardAddHandler(freeBoardDao, sqlSession));
    commandMap.put("/myStudy/freeBoardDetail", new FreeBoardDetailHandler(freeBoardDao, promptFreeBoard, sqlSession));
    commandMap.put("/myStudy/freeBoardUpdate", new FreeBoardUpdateHandler( freeBoardDao, promptFreeBoard, sqlSession));
    commandMap.put("/myStudy/freeBoardDelete", new FreeBoardDeleteHandler(freeBoardDao, sqlSession));

    //Socket chatSocket = new Socket(); 
    //    commandMap.put("/myStudy/chat", new MyStudyChat(requestAgent));
    //commandMap.put("/myStudy/chatOpen", new MySocketServer(chatSocket, requestAgent));
    //commandMap.put("/myStudy/chatStart", new MySocketClient(requestAgent));

    commandMap.put("/myStudy/freeBoard/commentDelete", new CommentDeleteHandler(studyDao, commentDao));
    commandMap.put("/myStudy/freeBoard/commentAdd", new CommentAddHandler(commentDao, sqlSession));
    commandMap.put("/myStudy/freeBoard/commentUpdate", new CommentUpdateHandler(studyDao, commentDao));

    commandMap.put("/myStudy/todoAdd", new ToDoAdd(studyDao, toDoDao, sqlSession));
    commandMap.put("/myStudy/todoList", new ToDoList(studyDao, toDoDao));
    commandMap.put("/myStudy/todoDetail", new ToDoDetail(studyDao, toDoDao));
    commandMap.put("/myStudy/todoUpdate", new ToDoUpdate(studyDao, toDoDao, sqlSession));
    commandMap.put("/myStudy/todoDelete", new ToDoDelete(studyDao, toDoDao, sqlSession));

    commandMap.put("/cafe/list", new CafeListHandler(cafeDao));
    commandMap.put("/cafe/detail", new CafeDetailHandler(cafeDao, cafeReviewDao, cafeRoomDao, sqlSession));
    commandMap.put("/cafe/reservation", new CafeReservationHandler(cafeDao, cafeRoomDao, cafeReservationDao, sqlSession));
    commandMap.put("/cafe/search", new CafeSearchHandler(cafeDao));
    commandMap.put("/cafe/search", new CafeSearchHandler(cafeDao));

    commandMap.put("/cafeReservation/list", new CafeMyReservationListHandler(cafeReservationDao));
    commandMap.put("/cafeReservation/detail", new CafeMyReservationDetailHandler(cafeDao, cafeReservationDao, cafeRoomDao));
    commandMap.put("/cafeReservation/delete", new CafeMyReservationDeleteHandler(cafeReservationDao, sqlSession));

    commandMap.put("/cafe/myReviewList", new CafeMyReviewListHandler(cafeDao, cafeReviewDao));
    commandMap.put("/cafe/myReviewAdd", new CafeMyReviewAddHandler(cafeDao, cafeReviewDao, cafeReservationDao, sqlSession));
    commandMap.put("/cafe/myReviewDelete", new CafeMyReviewDeleteHandler(cafeReviewDao, sqlSession));

    commandMap.put("/ceoMember/myCafeDetail", new CeoCafeDetailHandler(cafeDao, cafeReviewDao));
    commandMap.put("/ceoMember/cafeAdd", new CeoCafeAddHandler(cafeDao, sqlSession));
    commandMap.put("/ceoMember/cafeUpdate", new CeoCafeUpdateHandler(cafeDao, sqlSession));
    commandMap.put("/ceoMember/cafeDelete", new CeoCafeDeleteHandler(cafeDao, sqlSession));

    commandMap.put("/ceoMember/cafeRoomList", new CeoCafeRoomListHandler(cafeRoomDao));
    commandMap.put("/ceoMember/cafeRoomDetail", new CeoCafeRoomDetailHandler(cafeDao, cafeRoomDao));
    commandMap.put("/ceoMember/cafeRoomAdd", new CeoCafeRoomAddHandler(cafeDao, cafeRoomDao, sqlSession));
    commandMap.put("/ceoMember/cafeRoomUpdate", new CeoCafeRoomUpdateHandler(cafeRoomDao, sqlSession));
    commandMap.put("/ceoMember/cafeRoomDelete", new CeoCafeRoomDeleteHandler(cafeRoomDao, sqlSession));

    commandMap.put("/ceoMember/ReservationDetail", new CeoReservationDetailHandler(cafeDao, cafeReservationDao, cafeRoomDao));
    commandMap.put("/ceoMember/ReservationReject", new CeoReservationRejectHandler(cafeDao, cafeReservationDao, sqlSession));

    commandMap.put("/cafe/control", new AdminCafeControlHandler(cafeDao));
    commandMap.put("/cafe/controlDetail", new AdminCafeDetailHandler(cafeDao, cafeReviewDao));
    commandMap.put("/cafe/controlApproval", new AdminCafeApprovalHandler(cafeDao, sqlSession));
    commandMap.put("/cafe/controlDelete", new AdminCafeDeleteHandler(cafeDao, sqlSession));

    commandMap.put("/cafe/reviewList", new AdminCafeReviewListControlHandler(cafeDao, cafeReviewDao)); 
    commandMap.put("/cafe/reviewListDelete", new AdminCafeReviewListDeleteHandler(cafeReviewDao, sqlSession)); 

  }  

  // ?????? ?????? ?????? ???
  //  class MyFilter implements MenuFilter {
  //    @Override
  //    public boolean accept(Menu menu) {
  //      return (menu.getAccessScope() & AbstractLoginHandler.getUserAccessLevel()) > 0; 
  //    }
  //  }

  MenuFilter menuFilter = menu -> (menu.getAccessScope() & AbstractLoginHandler.getUserAccessLevel()) > 0;

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
    MenuGroup adminMenuGroup = new MenuGroup("???? ?????????");
    adminMenuGroup.setMenuFilter(menuFilter);
    adminMenuGroup.add(new MenuItem("???? ?????????", LOGOUT, "/admin/login"));
    adminMenuGroup.add(new MenuItem("???? ????????????", ADMIN_LOGIN, "/admin/logout"));
    adminMenuGroup.add(new MenuItem("???? ?????? ?????????", ADMIN_LOGIN, "/admin/detail"));

    adminMenuGroup.add(createControlMemberMenu());  // ?????? ??????
    adminMenuGroup.add(createControlStudyMenu());   // ????????? ??????
    adminMenuGroup.add(createControlReviewMenu());  // ?????? ??????
    adminMenuGroup.add(createAdminCSMenu());        // ???????????? ??????

    return adminMenuGroup;
  }

  // ????????? ?????? ??????2 - ?????? ??????
  private Menu createControlMemberMenu() {
    MenuGroup adminUserMenu = new MenuGroup("???? ?????? ??????", ADMIN_LOGIN); 
    adminUserMenu.setMenuFilter(menuFilter);
    adminUserMenu.add(new MenuItem("?????? ?????? ??????", "/adminMember/list"));
    adminUserMenu.add(new MenuItem("?????? ?????? ??????", "/adminMember/detail"));
    adminUserMenu.add(new MenuItem("?????? ?????? ??????", "/adminCeoMember/list"));
    adminUserMenu.add(new MenuItem("?????? ?????? ??????", "/adminCeoMember/detail"));

    return adminUserMenu;
  }

  // ????????? ?????? ??????3 - ????????? ??????
  private Menu createControlStudyMenu() {
    MenuGroup adminStudyMenu = new MenuGroup("???? ????????? ??????", ADMIN_LOGIN); 
    adminStudyMenu.setMenuFilter(menuFilter);
    adminStudyMenu.add(new MenuItem("??????","/study/list"));
    adminStudyMenu.add(new MenuItem("??????","/study/delete"));
    return adminStudyMenu;
  }

  // ????????? ?????? ??????4 - ?????? ?????? ??????
  private Menu createControlReviewMenu() {
    MenuGroup adminCafeReviewMenu = new MenuGroup("???? ?????? ??????", ADMIN_LOGIN); 
    adminCafeReviewMenu.setMenuFilter(menuFilter);
    adminCafeReviewMenu.add(new MenuItem("?????? ????????? ??????","/cafe/control"));
    adminCafeReviewMenu.add(new MenuItem("?????? ?????? ??????","/cafe/reviewList")); 

    return adminCafeReviewMenu;
  }

  //????????? ?????? ??????5 - ???????????? ??????
  private Menu createAdminCSMenu() {
    MenuGroup csMenu = new MenuGroup("???? ???????????? ??????", ADMIN_LOGIN);
    csMenu.setMenuFilter(menuFilter);
    csMenu.add(createAdminNoticeMenu());
    csMenu.add(createAdminAskMenu());

    return csMenu;
  }   

  // 5-1
  private Menu createAdminNoticeMenu() {
    MenuGroup adminNoticeMenu = new MenuGroup("???? ????????????"); 
    adminNoticeMenu.setMenuFilter(menuFilter);
    adminNoticeMenu.add(new MenuItem("??????", "/adminNotice/add"));
    adminNoticeMenu.add(new MenuItem("??????", "/adminNotice/list"));
    adminNoticeMenu.add(new MenuItem("??????", "/adminNotice/detail"));

    return adminNoticeMenu;
  }

  // 5-2
  private Menu createAdminAskMenu() {
    MenuGroup adminaskMenu = new MenuGroup("???? ????????????");
    adminaskMenu.setMenuFilter(menuFilter);
    adminaskMenu.add(new MenuItem("??????", "/askBoard/list"));
    adminaskMenu.add(new MenuItem("??????", "/askBoard/detail"));

    return adminaskMenu;
  }

  // -----------------------------------------------------------------------------------------------
  // ?????? ?????? ??????
  Menu createMemberMenu() {
    MenuGroup userMenuGroup = new MenuGroup("???? ????????? ??????"); 
    userMenuGroup.setMenuFilter(menuFilter);
    userMenuGroup.add(new MenuItem("???? ????????????", LOGOUT, "/member/add"));
    userMenuGroup.add(new MenuItem("???? ????????????", PER_LOGIN, "/member/logout"));
    userMenuGroup.add(new MenuItem("???? ?????????", LOGOUT, "/member/login"));
    userMenuGroup.add(new MenuItem("??? ID/PW ??????", LOGOUT, "/member/findIdPw"));

    userMenuGroup.add(createMyPageMenu());      // ???????????????
    userMenuGroup.add(createStudyMenu());       // ????????? ??????

    userMenuGroup.add(createMystudyMenu());     // ??? ?????????

    userMenuGroup.add(createCafeMenu());        // ?????? ????????????
    userMenuGroup.add(createMemberCSMenu());    // ????????????

    return userMenuGroup;
  }

  // ?????? ?????? ??????2 - ??????????????? (????????? ?????????)
  private Menu createMyPageMenu() {
    MenuGroup myPageMenu = new MenuGroup("???? ?????? ?????????", PER_LOGIN); 
    myPageMenu.setMenuFilter(menuFilter);
    myPageMenu.add(new MenuItem("???? ????????????", "/member/detail"));
    myPageMenu.add(new MenuItem("???? ??? ?????????", "/study/bookMarkList"));
    myPageMenu.add(new MenuItem("???? ????????????", "/askBoard/perMyList"));
    myPageMenu.add(new MenuItem("???? ????????????", "/cafeReservation/list"));
    myPageMenu.add(new MenuItem("???? ????????????", "/cafe/myReviewList"));
    myPageMenu.add(new MenuItem("???? ????????????", "/member/delete"));
    return myPageMenu;
  }

  //?????? ?????? ??????3 - ????????? ??????
  private Menu createStudyMenu() {
    MenuGroup allStudyMenu = new MenuGroup("???? ????????? ??????"); 
    allStudyMenu.setMenuFilter(menuFilter);
    allStudyMenu.add(new MenuItem("??????", PER_LOGIN, "/study/add"));
    allStudyMenu.add(new MenuItem("??????","/study/list"));
    allStudyMenu.add(new MenuItem("??????","/study/search"));
    allStudyMenu.add(new MenuItem("??????","/study/detail"));

    return allStudyMenu; 
  }     

  //?????? ?????? ??????4 - ??? ?????????
  private Menu createMystudyMenu() {
    MenuGroup myStudyMenu = new MenuGroup("???? ??? ?????????", PER_LOGIN);
    myStudyMenu.setMenuFilter(menuFilter);
    myStudyMenu.add(new MenuItem("??????", "/myStudy/list"));
    myStudyMenu.add(new MenuItem("??????", "/myStudy/detail"));

    return myStudyMenu; 
  }

  //?????? ?????? ??????5 - ????????? ??????
  private Menu createCafeMenu() {
    MenuGroup cafeMenu = new MenuGroup("???? ?????? ??????"); 
    cafeMenu.setMenuFilter(menuFilter);
    cafeMenu.add(new MenuItem("??????", "/cafe/list"));
    cafeMenu.add(new MenuItem("??????", "/cafe/search"));
    cafeMenu.add(new MenuItem("??????", "/cafe/detail"));

    return cafeMenu;
  }

  //?????? ?????? ??????6 - ????????????
  private Menu createMemberCSMenu() {
    MenuGroup memberCSMenu = new MenuGroup("???? ????????????");
    memberCSMenu.setMenuFilter(menuFilter);
    memberCSMenu.add(createMemberNoticeMenu());
    memberCSMenu.add(createMemberAskBoardMenu());

    return memberCSMenu;
  }

  // 6-1
  private Menu createMemberNoticeMenu() {
    MenuGroup noticeMenu = new MenuGroup("???? ????????????"); 
    noticeMenu.setMenuFilter(menuFilter);
    noticeMenu.add(new MenuItem("??????", "/adminNotice/list"));
    noticeMenu.add(new MenuItem("??????", "/adminNotice/detail"));

    return noticeMenu;
  }

  // 6-2
  // ???????????? ???????????? (?????? ?????? ?????????) >> ?????? ??????
  private Menu createMemberAskBoardMenu() {
    MenuGroup askBoardMenu = new MenuGroup("???? ????????????");
    askBoardMenu.setMenuFilter(menuFilter);
    askBoardMenu.add(new MenuItem("??????", "/askBoard/add"));
    askBoardMenu.add(new MenuItem("??????", "/askBoard/perMyList"));
    askBoardMenu.add(new MenuItem("??????", "/askBoard/perMydetail"));

    return askBoardMenu;
  }

  //-----------------------------------------------------------------------------------------------

  // ??????
  Menu createCeoMenu() {
    MenuGroup ceoMemberMenuGroup = new MenuGroup("???? ????????? ?????? - ?????????");
    ceoMemberMenuGroup.setMenuFilter(menuFilter);
    ceoMemberMenuGroup.add(new MenuItem("???? ????????????", LOGOUT, "/ceoMember/add"));
    ceoMemberMenuGroup.add(new MenuItem("???? ?????????", LOGOUT, "/ceoMember/login"));
    ceoMemberMenuGroup.add(new MenuItem("??? ID/PW ??????", LOGOUT, "/ceoMember/findIdPw"));
    ceoMemberMenuGroup.add(new MenuItem("???? ????????????", CEO_LOGIN, "/ceoMember/logout"));

    ceoMemberMenuGroup.add(createCeoPageMenu());      // ???????????????

    ceoMemberMenuGroup.add(createCeoCSMenu());          // ????????????

    return ceoMemberMenuGroup;
  }

  // ??????
  private Menu createCeoPageMenu() {
    MenuGroup ceoPageMenu = new MenuGroup("???? ?????? ?????????", CEO_LOGIN); 
    ceoPageMenu.setMenuFilter(menuFilter);
    ceoPageMenu.add(new MenuItem("???? ?????? ?????????", "/ceoMember/detail"));
    ceoPageMenu.add(new MenuItem("???? ????????????", "/ceoMember/myCafeDetail"));
    ceoPageMenu.add(new MenuItem("???? ????????????", "/askBoard/ceoMyList"));
    ceoPageMenu.add(new MenuItem("???? ????????????", "/ceoMember/delete"));

    return ceoPageMenu;
  }


  //?????? ?????? ??????6 - ????????????
  private Menu createCeoCSMenu() {
    MenuGroup memberCSMenu = new MenuGroup("???? ????????????");
    memberCSMenu.setMenuFilter(menuFilter);
    memberCSMenu.add(createCeoNoticeMenu());
    memberCSMenu.add(createCeoAskBoardMenu());

    return memberCSMenu;
  }

  // 6-1
  private Menu createCeoNoticeMenu() {
    MenuGroup noticeMenu = new MenuGroup("???? ????????????"); 
    noticeMenu.setMenuFilter(menuFilter);
    noticeMenu.add(new MenuItem("??????", "/adminNotice/list"));
    noticeMenu.add(new MenuItem("??????", "/adminNotice/detail"));

    return noticeMenu;
  }

  // 6-2
  // ???????????? ???????????? (?????? ?????? ?????????) >> ?????? ??????
  private Menu createCeoAskBoardMenu() {
    MenuGroup askBoardMenu = new MenuGroup("???? ????????????");
    askBoardMenu.setMenuFilter(menuFilter);
    askBoardMenu.add(new MenuItem("??????", CEO_LOGIN, "/askBoard/add"));
    askBoardMenu.add(new MenuItem("??????", "/askBoard/ceoMyList"));
    askBoardMenu.add(new MenuItem("??????", "/askBoard/ceoMydetail"));

    return askBoardMenu;
  }

  void welcomeservice() throws Exception {
    welcome().execute();
    service();
  }

  void service() throws Exception{
    notifyOnApplicationStarted();

    createMenu().execute(); 
    requestAgent.request("quit", null);
    Prompt.close();

    notifyOnApplicationEnded();
    con.close();
  }

  public static void main(String[] args) throws Exception {
    System.out.println("[ ???? ????????? ?????? ??????????????? ]");
    ClientApp app = new ClientApp(); 

    app.addApplicationContextListener(new AppInitListener());
    //app.addApplicationContextListener(new FileListener());

    app.welcomeservice();
    Prompt.close();
  }

}