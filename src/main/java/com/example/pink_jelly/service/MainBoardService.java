package com.example.pink_jelly.service;

import com.example.pink_jelly.domain.MainBoardVO;
import com.example.pink_jelly.dto.MainBoardDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;

public interface MainBoardService {
    //게시판 등록
    void register(MainBoardDTO mainBoardDTO);

    PageResponseDTO<MainBoardDTO> getList(PageRequestDTO pageRequestDTO);

    MainBoardDTO getBoard(Long mbNo);


}
