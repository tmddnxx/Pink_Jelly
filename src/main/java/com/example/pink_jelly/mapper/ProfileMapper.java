package com.example.pink_jelly.mapper;

import com.example.pink_jelly.domain.CatsMeBoardVO;
import com.example.pink_jelly.domain.CatsReviewBoardVO;
import com.example.pink_jelly.domain.MainBoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProfileMapper {

    List<MainBoardVO> mainBoardList(String memberId);

    List<CatsMeBoardVO> catsMeBoardList(String memberId);

    List<CatsReviewBoardVO> reviewBoardList(String memberId);
}
