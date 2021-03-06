package com.ogong.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import com.ogong.util.Prompt;


public class MenuGroup extends Menu {

  static Stack<Menu> breadCrumb = new Stack<>();

  ArrayList<Menu> childs = new ArrayList<>();

  boolean disablePrevMenu;
  String prevMenuTitle = "๐ ์ด์  ๋ฉ๋ด";

  // ๋ฉ๋ด ๋ชฉ๋ก์ ์ถ๋ ฅํ  ๋ ์ถ๋ ฅ ์ฌ๋ถ๋ฅผ ๊ฒฐ์ ํ  ๊ฐ์ฒด๋ฅผ ๋ณด๊ดํ๋ค.
  MenuFilter menuFilter;

  static public boolean successLogin = false;

  // ์ด์ ์ผ๋ก ์ด๋์ํค๋ ๋ฉ๋ด๋ฅผ ํํํ๊ธฐ ์ํด ๋ง๋  ํด๋์ค
  private static class PrevMenu extends Menu {
    public PrevMenu() {
      super("");
    }
    @Override
    public void execute() {
    }
  }
  static PrevMenu prevMenu = new PrevMenu();

  public MenuGroup(String title) {
    super(title);
  }
  public MenuGroup(String title, int accessScope) {
    super(title, accessScope);
  }

  public MenuGroup(String title, boolean disablePrevMenu) {
    super(title);
    this.disablePrevMenu = disablePrevMenu;
  }

  public MenuGroup(String title, boolean disablePrevMenu, int accessScope) {
    super(title);
    this.accessScope = accessScope;
  }

  public void setPrevMenuTitle(String prevMenuTitle) {
    this.prevMenuTitle = prevMenuTitle;
  }

  public void setMenuFilter(MenuFilter menuFilter) {
    this.menuFilter = menuFilter;
  }

  //MenuGroup์ด ํฌํจํ๋ ํ์ Menu๋ฅผ ๋ค๋ฃฐ ์ ์๋๋ก ๋ฉ์๋๋ฅผ ์ ์ํ๋ค.
  public void add(Menu child) {
    childs.add(child);
  }

  // ๋ฐฐ์ด์ ๋ค์ด ์๋ Menu ๊ฐ์ฒด๋ฅผ ์ฐพ์ ์ ๊ฑฐํ๋ค.
  public Menu remove(Menu child) {
    if (childs.remove(child)) 
      return child;
    return null;
  }

  @Override 
  public void execute() {

    // ํ์ฌ ์คํํ๋ ๋ฉ๋ด๋ฅผ ์คํ์ ๋ณด๊ดํ๋ค.
    breadCrumb.push(this);

    while (true) {

      if (successLogin) {
        successLogin = false;
        breadCrumb.pop();
        break;
      }

      printBreadCrumbMenuTitle();
      List<Menu> menuList = getMenuList();
      printMenuList(menuList);

      try {
        Menu menu = selectMenu(menuList);
        if (menu == null) {
          System.out.println("๋ฌดํจํ ๋ฉ๋ด ๋ฒํธ์๋๋ค.");
          continue;
        }
        if (menu instanceof PrevMenu) {
          breadCrumb.pop();
          return;
        }

        menu.execute();

      } catch (Exception e) {
        System.out.println("-----------------------------------------");
        System.out.printf ("์ค๋ฅ ๋ฐ์: %s\n", e.getClass().getName());
        e.printStackTrace();   // ์ ์๋ฌ ๋ณ๋์ง ์๋ ค์ค๋ค.
        System.out.println("-----------------------------------------");
      }
    }
  }


  private String getBreadCrumb() {
    String path = "";

    for (int i = 0; i < breadCrumb.size(); i++) {
      if (path.length() > 0) {
        path += " / ";
      }
      Menu menu = breadCrumb.get(i); 
      path += menu.title;
    }

    // ๋ฉ์ธ, ๊ด๋ฆฌ์ ๋ฉ๋ด๋ ์๋ณด์ด๊ฒ (๋ก๊ทธ์์ ์ ์ ๋ฉ์ธ, ๊ด๋ฆฌ์๋ฅผ ๋ชป๊ฐ๊ฒํด์ผํจ)
    //    if (AuthPerMemberLoginHandler.getLoginUser() != null) {
    //      path += " - " + AuthPerMemberLoginHandler.getLoginUser().getPerNickname();
    //    }

    return path;
  }

  // ์ถ๋ ฅ๋  ๋ฉ๋ด ๋ชฉ๋ก ์ค๋น
  // ์?
  // - ๋ฉ๋ด ์ถ๋ ฅ ์๋๋ฅผ ๋น ๋ฅด๊ฒ ํ๊ธฐ ์ํจ.
  // - ๋ฉ๋ด๋ฅผ ์ถ๋ ฅํ  ๋ ์ถ๋ ฅํ  ๋ฉ๋ด์ ์ถ๋ ฅํ์ง ๋ง์์ผ ํ  ๋ฉ๋ด๋ฅผ ๊ตฌ๋ถํ๋
  //   ์๊ฐ์ ์ค์ด๊ธฐ ์ํจ.
  private List<Menu> getMenuList() {
    ArrayList<Menu> menuList = new ArrayList<>();
    for (Menu menu : childs) {
      if (menuFilter != null && !menuFilter.accept(menu)) {
        // ๋ฉ๋ด ํํฐ๊ฐ ์์ ๋, ๊ทธ ๋ฉ๋ด ํํฐ์์ ์น์ธํ์ง ์๋๋ค๋ฉด
        // ์ถ๋ ฅํ  ๋ฉ๋ด์ ํฌํจ์ํค์ง ์๋๋ค.
        continue;
      }
      menuList.add(menu);
      //      if ((menu.accessScope & 
      //          AuthAdminLoginHandler.getUserAccessLevel()) > 0 ) {
      //        menuList.add(menu);
      //      } 
      //      else if ((menu.accessScope & 
      //          AuthPerMemberLoginHandler.getUserAccessLevel()) > 0 ) {
      //        menuList.add(menu);
      //      }
      //      else if ((menu.accessScope & 
      //          AuthCeoMemberLoginHandler.getUserAccessLevel()) > 0 ) {
      //        menuList.add(menu);
      //      }
    }


    return menuList;
  }


  private void printBreadCrumbMenuTitle() {
    System.out.printf("\n[%s]\n", getBreadCrumb());
  }

  private void printMenuList(List<Menu> menuList) {
    int i = 1;
    for (Menu menu : menuList) {
      System.out.printf("%d. %-20s\n", i++ , menu.title);
    }

    if (!disablePrevMenu) {
      System.out.printf("0. %s\n", this.prevMenuTitle);
    }
  }

  private Menu selectMenu(List<Menu> menuList)  {
    int menuNo = Prompt.inputInt("์ ํ> ");

    if (menuNo < 0 || menuNo > menuList.size()) {
      return null;
    }

    if (menuNo == 0 && !disablePrevMenu) {
      return prevMenu;   // ํธ์ถํ ์ชฝ์ '์ด์  ๋ฉ๋ด' ์ ํ์ ์๋ฆฌ๊ธฐ ์ํด
    } 

    return menuList.get(menuNo - 1);
  }

}



