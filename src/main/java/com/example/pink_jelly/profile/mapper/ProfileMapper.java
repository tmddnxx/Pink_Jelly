package com.example.pink_jelly.profile.mapper;

import com.example.pink_jelly.catsMe.vo.CatsMeBoardVO;
import com.example.pink_jelly.catsMe.vo.CatsReviewBoardVO;
import com.example.pink_jelly.mainBoard.vo.MainBoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProfileMapper {

    List<MainBoardVO> mainBoardList(String memberId);

    List<CatsMeBoardVO> catsMeBoardList(String memberId);

    List<CatsReviewBoardVO> reviewBoardList(String memberId);
}
