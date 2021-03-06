package com.ogong.pms.handler.myStudy.todo;

import org.apache.ibatis.session.SqlSession;
import com.ogong.pms.dao.StudyDao;
import com.ogong.pms.dao.ToDoDao;
import com.ogong.pms.domain.Study;
import com.ogong.pms.domain.ToDo;
import com.ogong.pms.handler.Command;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class ToDoUpdate implements Command {

  StudyDao studyDao;
  ToDoDao toDoDao;
  SqlSession sqlSession;

  public ToDoUpdate(StudyDao studyDao, ToDoDao toDoDao, SqlSession sqlSession) {
    this.studyDao = studyDao;
    this.toDoDao = toDoDao;
    this.sqlSession = sqlSession;
  }

  public void execute(CommandRequest request) throws Exception {
    System.out.println();
    System.out.println("▶ To-Do List 변경");
    System.out.println();

    int studyNo = (int) request.getAttribute("inputNo");
    int todoNo = (int) request.getAttribute("studyTodoNo");

    Study myStudy = studyDao.findByNo(studyNo);
    ToDo todo = toDoDao.findByNo(myStudy.getStudyNo(), todoNo);

    String todoContent = Prompt.inputString(String.format(" 내용(%s) : ", todo.getTodoContent()));
    String todoRemark = Prompt.inputString(String.format(" 비고(%s) : ", todo.getTodoRemark()));
    int todoStatus = promptStatus(todo.getTodoStatus());


    System.out.println();
    String input = Prompt.inputString(" 정말 변경하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 변경을 취소하였습니다.");
      return;
    }

    todo.setTodoContent(todoContent);
    todo.setTodoStatus(todoStatus);
    todo.setTodoRemark(todoRemark);

    toDoDao.update(todo);
    sqlSession.commit();

    System.out.println(" >> 할 일이 변경되었습니다.");
    request.getRequestDispatcher("/myStudy/todoList").forward(request);
  }

  private int promptStatus(int todoStatus) {
    if (todoStatus == -1) {
      System.out.println();
      System.out.println(" 진행 상황: ");
      System.out.println(" 1: 진행 중");
      System.out.println();

      try {
        return Prompt.inputInt("선택> ");
      } catch (Exception e) {
        System.out.println(" >> 잘못 입력하셨습니다.");
      }

    } else {
      System.out.printf(" 진행 상황(%s) : ", todoStatus);
    }
    return Prompt.inputInt("1. 진행 중 / 2. 완료 > ");
  }
}
