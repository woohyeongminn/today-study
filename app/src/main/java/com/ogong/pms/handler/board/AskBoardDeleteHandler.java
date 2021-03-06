package com.ogong.pms.handler.board;

import java.util.List;
import com.ogong.pms.domain.AskBoard;
import com.ogong.pms.domain.CeoMember;
import com.ogong.pms.domain.Member;
import com.ogong.pms.domain.Reply;
import com.ogong.pms.handler.AbstractAskBoardHandler;
import com.ogong.pms.handler.AuthCeoMemberLoginHandler;
import com.ogong.pms.handler.AuthPerMemberLoginHandler;
import com.ogong.pms.handler.CommandRequest;
import com.ogong.util.Prompt;

public class AskBoardDeleteHandler extends AbstractAskBoardHandler {

  public AskBoardDeleteHandler(List<AskBoard> askBoardList, List<Member> memberList,
      List<CeoMember> ceoMemberList, List<Reply> replyList) {
    super(askBoardList, replyList, memberList, ceoMemberList);
  }

  @Override
  public void execute(CommandRequest request) {
    System.out.println();
    System.out.println("▶ 문의사항 삭제");
    System.out.println();

    int askNo = (int) request.getAttribute("askNo");

    AskBoard askList = findByAskBoardNo(askNo);

    if (askList == null) {
      System.out.println(" >> 해당 번호의 문의글이 없습니다.");
      return;
    }

    if (AuthPerMemberLoginHandler.getLoginUser() != null) {

      if (AuthPerMemberLoginHandler.getLoginUser().getPerNo() !=
          askList.getAskMemberWriter().getPerNo()) {
        System.out.println(" >> 삭제 권한이 없습니다.");
        return;
      }

    }

    else if (AuthCeoMemberLoginHandler.getLoginCeoMember() != null) {

      if (AuthCeoMemberLoginHandler.getLoginCeoMember().getCeoNo() !=
          askList.getAskCeoWriter().getCeoNo()) {
        System.out.println(" >> 삭제 권한이 없습니다.");
        return;
      }

    }

    String input = Prompt.inputString(" 정말 삭제하시겠습니까? (네 / 아니오) ");
    if (!input.equalsIgnoreCase("네")) {
      System.out.println(" >> 문의글 삭제를 취소하였습니다.");
      return;
    }

    askBoardList.remove(askList);

    System.out.println(" >> 문의글을 삭제하였습니다.");
  }

}







