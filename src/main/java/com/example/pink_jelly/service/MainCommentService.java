package com.example.pink_jelly.service;


import com.example.pink_jelly.dto.MainCommentDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;

public interface MainCommentService {
    Long register(MainCommentDTO mainCommentDTO);

    void remove(Long comNo, Long mbNo);

    PageResponseDTO<MainCommentDTO> getListMainComment(Long mbNo, PageRequestDTO pageRequestDTO);

    void addCommentCnt (Long mbNo); //댓글수 추가

    void removeCommentCnt (Long mbNo); //댓글수 감소
}
