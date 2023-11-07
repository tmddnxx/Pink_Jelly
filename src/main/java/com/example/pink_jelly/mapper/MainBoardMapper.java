package com.example.pink_jelly.mapper;

import com.example.pink_jelly.domain.MainBoardVO;
import com.example.pink_jelly.dto.MainBoardDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MainBoardMapper {

    void insert(MainBoardVO mainBoardVO); //게시물 등록

    List<MainBoardVO> selectAll(PageRequestDTO pageRequestDTO); //전체 게시물

    int getCount(PageRequestDTO pageRequestDTO); //총 게시물 수 = total
    //리스트 출력
    List<MainBoardVO> selectList(PageRequestDTO pageRequestDTO); //리스트로 출력

    MainBoardVO getOne(Long mbNo); //게시물 불러오기
}
