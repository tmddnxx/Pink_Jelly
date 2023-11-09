package com.example.pink_jelly.service;

import com.example.pink_jelly.dto.CatsCommentDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;

public interface CRBCommentService {

    void register(CatsCommentDTO catsCommentDTO);

    void remove(Long comNo);

    PageResponseDTO<CatsCommentDTO> getListOfBoard(Long crbNo, PageRequestDTO pageRequestDTO);
}
