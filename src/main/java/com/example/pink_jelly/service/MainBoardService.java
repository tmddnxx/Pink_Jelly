package com.example.pink_jelly.service;

import com.example.pink_jelly.domain.MainBoardVO;
import com.example.pink_jelly.dto.MainBoardDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;

public interface MainBoardService {
    //게시판 등록
    void register(MainBoardDTO mainBoardDTO);

    PageResponseDTO<MainBoardDTO> getList(PageRequestDTO pageRequestDTO);

    MainBoardDTO getBoard(Long mbNo, String mode);

    void upHit(Long mbNo);

    void removeOne(Long mbNo);

    void modifyBoard(MainBoardDTO mainBoardDTO);

    boolean addBoardLike(Long mno, Long mbNo); // 좋아요 등록
    boolean removeBoardLike(Long mno, Long mbNo); // 좋아요 제거

    boolean isBoardLike(Long mno, Long mbNo);
}
