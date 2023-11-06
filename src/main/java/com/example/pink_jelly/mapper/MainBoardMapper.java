package com.example.pink_jelly.mapper;

import com.example.pink_jelly.domain.MainBoardVO;
import com.example.pink_jelly.dto.PageRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MainBoardMapper {
    //게시물 등록
    void insert(MainBoardVO mainBoardVO);

    //총 게시물 수 = total
    int getCount(PageRequestDTO pageRequestDTO);
    //리스트 출력
    List<MainBoardVO> selectList(PageRequestDTO pageRequestDTO);
}
