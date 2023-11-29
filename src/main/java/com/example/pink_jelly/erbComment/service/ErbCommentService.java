package com.example.pink_jelly.erbComment.service;

import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;
import com.example.pink_jelly.erbComment.dto.EternalRestCommentDTO;

public interface ErbCommentService {
    Long register(EternalRestCommentDTO eternalRestCommentDTO);
    void remove(Long comNo, Long erbNo);
    PageResponseDTO<EternalRestCommentDTO> getListERBComment(Long erbNo, PageRequestDTO pageRequestDTO);

}
