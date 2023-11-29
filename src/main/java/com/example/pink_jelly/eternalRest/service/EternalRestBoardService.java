package com.example.pink_jelly.eternalRest.service;

import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;
import com.example.pink_jelly.eternalRest.dto.EternalRestBoardDTO;

public interface EternalRestBoardService { // 장례식 게시판

    void register(EternalRestBoardDTO eternalRestBoardDTO); //게시판 등록

    PageResponseDTO<EternalRestBoardDTO> getList(PageRequestDTO pageRequestDTO); // 게시판 목록

    EternalRestBoardDTO getBoard(Long erbNo, Long mno); // 게시물 상세

    void remove(Long erbNo); // 게시물 삭제

    void catInfoDel(Long mno); // 고양이 정보 삭제


    boolean addSad(Long mno, Long erbNo); // 슬퍼요 등록
    boolean removeSad(Long mno, Long erbNo); // 슬퍼요 제거

    boolean isRestSad(Long mno, Long erbNo); // 슬퍼요 확인
}
