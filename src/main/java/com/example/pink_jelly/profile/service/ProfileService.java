package com.example.pink_jelly.profile.service;

import com.example.pink_jelly.catsMe.dto.CatsMeBoardDTO;
import com.example.pink_jelly.catsMe.dto.CatsReviewBoardDTO;
import com.example.pink_jelly.mainBoard.dto.MainBoardDTO;

import java.util.List;

public interface ProfileService {

    List<MainBoardDTO> mainBoardList(String memberId); // 메인 게시물 목록

    List<CatsMeBoardDTO> catsMeBoardList(String memberId); // 입양소 게시물 목록

    List<CatsReviewBoardDTO> reviewBoardList(String memberId); // 후기 게시판 목록
}
