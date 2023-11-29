package com.example.pink_jelly.mainComment.service;


import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;
import com.example.pink_jelly.mainComment.dto.MainCommentDTO;

public interface MainCommentService {
    Long register(MainCommentDTO mainCommentDTO);

    void remove(Long comNo, Long mbNo);

    PageResponseDTO<MainCommentDTO> getListMainComment(Long mbNo, PageRequestDTO pageRequestDTO);

    PageResponseDTO<MainCommentDTO> getMainCommentList(Long mbNo, PageRequestDTO pageRequestDTO);
}
