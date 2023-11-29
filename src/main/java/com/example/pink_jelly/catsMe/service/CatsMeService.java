package com.example.pink_jelly.catsMe.service;

import com.example.pink_jelly.catsMe.dto.CatsReviewBoardDTO;
import com.example.pink_jelly.catsMe.dto.CatsMeBoardDTO;
import com.example.pink_jelly.dto.*;

public interface CatsMeService {
    void register(CatsMeBoardDTO catsMeBoardDTO);
    PageResponseDTO<CatsMeBoardDTO> getList(PageRequestDTO pageRequestDTO, Long mno, String memberId);
    CatsMeBoardDTO getBoard(Long cmbNo, String mode);
    void upHit(Long cmbNo);
    void modifyBoard(CatsMeBoardDTO catsMeBoardDTO);

    void removeOne(Long cmbNo);

    //CatsReviewBoard
    void registerReviewBoard(CatsReviewBoardDTO catsReviewBoardDTO);
    PageResponseDTO<CatsReviewBoardDTO> getReviewBoardList(PageRequestDTO pageRequestDTO, Long mno, String memberId);
    CatsReviewBoardDTO getReviewBoard(Long crbNo, String mode, Long mno);
    void upReviewBoardHit(Long crbNo);
    void modifyReviewBoard(CatsReviewBoardDTO catsReviewBoardDTO);

    void removeReviewBoardOne(Long crbNo);

    boolean addReviewBoardLike(Long mno, Long crbNo); // 좋아요 등록
    boolean removeReviewBoardLike(Long mno, Long crbNo); // 좋아요 제거

    boolean isReviewBoardLike(Long mno, Long crbNo); // 좋아요 확인
}
