package com.example.pink_jelly.mapper;

import com.example.pink_jelly.domain.MainBoardVO;
import com.example.pink_jelly.dto.MainBoardDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MainBoardMapper {

    void insert(MainBoardVO mainBoardVO); //게시물 등록


    int getCount(PageRequestDTO pageRequestDTO); //총 게시물 수 = total

    List<MainBoardVO> selectList(int skip, int size, String type, String keyword, String link, Long mno, String memberId); //리스트로 출력

    MainBoardVO getOne(Long mbNo); //게시물 불러오기

    void updateHit(Long mbNo); //조회수 증가

    void deleteOne(Long mbNo); //게시물 삭제

    void updateBoard(MainBoardVO mainBoardVO); //게시판 수정

    boolean isMainBoardLike(Long mno, Long mbNo); // 유저가 특정 게시물에 좋아요를 달았는지 여부

    boolean insertMainBoardLike(Long mno, Long mbNo); // 좋아요 추가

    boolean removeMainBoardLike(Long mno, Long mbNo); // 좋아요 제거

    void likeCntUpdate(Long mbNo, boolean flag); // 좋아요 수 업데이트

    void upCommentCnt (Long mbNo); //댓글수 증가

    void downCommentCnt (Long mbNo); //댓글 수 감소
}
