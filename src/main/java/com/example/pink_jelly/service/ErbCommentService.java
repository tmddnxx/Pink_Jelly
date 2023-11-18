package com.example.pink_jelly.service;

import com.example.pink_jelly.dto.EternalRestCommentDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;

public interface ErbCommentService {
    Long register(EternalRestCommentDTO eternalRestCommentDTO);
    void remove(Long comNo, Long erbNo);
    PageResponseDTO<EternalRestCommentDTO> getListERBComment(Long erbNo, PageRequestDTO pageRequestDTO);

}
