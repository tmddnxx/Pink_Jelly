package com.example.pink_jelly.service;

import com.example.pink_jelly.dto.AdminSearchDTO;
import com.example.pink_jelly.dto.MemberDTO;
import com.example.pink_jelly.dto.*;

import java.util.List;

public interface AdminService {

    List<MemberDTO> adminMemberSearch(AdminSearchDTO adminSearchDTO);

    void removeMember (Long mno);

    PageResponseDTO<MainBoardDTO> mainList(PageRequestDTO pageRequestDTO); // 메인 리스트
    MainBoardDTO mainView(Long mbNo); // 메인 뷰
    void mainDelete(Long mbNo); // 메인 게시물 삭제
    PageResponseDTO<MainCommentDTO> mainCommentList(Long mbNo, PageRequestDTO pageRequestDTO); // 메인 댓글 리스트
    void mainCommentRemove(Long comNo, Long mbNo); // 메인 댓글 삭제


    PageResponseDTO<CatsMeBoardDTO> catsList(PageRequestDTO pageRequestDTO); // 캣츠미 리스트
    CatsMeBoardDTO catsView(Long cmbNo); // 캣츠미 뷰
    void catsDelete(Long cmbNo); // 캣츠미 게시물 삭제



    PageResponseDTO<CatsReviewBoardDTO> reviewList(PageRequestDTO pageRequestDTO); // 리뷰 리스트
    CatsReviewBoardDTO reviewView(Long crbNo); // 메인 뷰
    void reviewDelete(Long crbNo); // 리뷰 게시물 삭제
    PageResponseDTO<CRBCommentDTO> reviewCommentList(Long crbNo, PageRequestDTO pageRequestDTO); // 리뷰 댓글 리스트
    void reviewCommentRemove(Long comNo, Long crbNo); // 리뷰 댓글 삭제


}
