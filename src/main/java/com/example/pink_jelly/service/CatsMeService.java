package com.example.pink_jelly.service;

import com.example.pink_jelly.dto.CatsMeBoardDTO;
import com.example.pink_jelly.dto.CatsReviewBoardDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;

public interface CatsMeService {
    void register(CatsMeBoardDTO catsMeBoardDTO);
    PageResponseDTO<CatsMeBoardDTO> getList(PageRequestDTO pageRequestDTO);
    CatsMeBoardDTO getBoard(Long cmbNo, String mode);
    void upHit(Long cmbNo);
    void modifyBoard(CatsMeBoardDTO catsMeBoardDTO);

    void removeOne(Long cmbNo);

    //CatsReviewBoard
    void registerReviewBoard(CatsReviewBoardDTO catsReviewBoardDTO);
    PageResponseDTO<CatsReviewBoardDTO> getReviewBoardList(PageRequestDTO pageRequestDTO);
    CatsReviewBoardDTO getReviewBoard(Long crbNo, String mode);
    void upReviewBoardHit(Long crbNo);
    void modifyReviewBoard(CatsReviewBoardDTO catsReviewBoardDTO);

    void removeReviewBoardOne(Long crbNo);
}
