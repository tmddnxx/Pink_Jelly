package com.example.pink_jelly.service;

import com.example.pink_jelly.dto.AdminSearchDTO;
import com.example.pink_jelly.dto.MemberDTO;
import com.example.pink_jelly.dto.*;

import java.util.List;

public interface AdminService {

    List<MemberDTO> adminMemberSearch(AdminSearchDTO adminSearchDTO);

    void removeMember (Long mno);

    PageResponseDTO<MainBoardDTO> mainList(PageRequestDTO pageRequestDTO); // 메인 리스트

    PageResponseDTO<CatsMeBoardDTO> catsList(PageRequestDTO pageRequestDTO); // 캣츠미 리스트

    PageResponseDTO<CatsReviewBoardDTO> reviewList(PageRequestDTO pageRequestDTO); // 리뷰 리스트


}
