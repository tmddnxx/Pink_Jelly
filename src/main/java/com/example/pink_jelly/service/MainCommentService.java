package com.example.pink_jelly.service;


import com.example.pink_jelly.dto.MainCommentDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;

public interface MainCommentService {
    void register(MainCommentDTO mainCommentDTO);

    void remove(Long comNo);

    PageResponseDTO<MainCommentDTO> getListMainComment(Long mbNo, PageRequestDTO pageRequestDTO);
}
