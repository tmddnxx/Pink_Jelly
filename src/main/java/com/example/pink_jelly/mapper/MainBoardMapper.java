package com.example.pink_jelly.mapper;

import com.example.pink_jelly.domain.MainBoardVO;
import com.example.pink_jelly.domain.MemberVO;
import com.example.pink_jelly.dto.MainBoardDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MainBoardMapper {

    void insert(MainBoardVO mainBoardVO); //게시물 등록


    int getCount(PageRequestDTO pageRequestDTO); //총 게시물 수 = total

    List<MainBoardVO> selectList(@Param("skip") int skip, @Param("size") int size, @Param("type") String type, @Param("keyword") String keyword, @Param("mno") Long mno, @Param("memberId") String memberId); //리스트로 출력

    MainBoardVO getOne(Long mbNo); //게시물 불러오기

    void updateHit(Long mbNo); //조회수 증가

    void deleteOne(Long mbNo); //게시물 삭제

    void updateBoard(MainBoardVO mainBoardVO); //게시판 수정

    boolean isMainBoardLike(@Param("mno") Long mno, @Param("mbNo") Long mbNo); // 유저가 특정 게시물에 좋아요를 달았는지 여부

    boolean insertMainBoardLike(@Param("mno") Long mno, @Param("mbNo") Long mbNo); // 좋아요 추가

    boolean removeMainBoardLike(@Param("mno") Long mno, @Param("mbNo")  Long mbNo); // 좋아요 제거

    void likeCntUpdate(@Param("mbNo") Long mbNo,@Param("flag") boolean flag); // 좋아요 수 업데이트

    void upCommentCnt (Long mbNo); //댓글수 증가

    void downCommentCnt (Long mbNo); //댓글 수 감소

    List<MemberVO> getAll(); //전체 불러오기

    List<MainBoardVO> selectAll(PageRequestDTO pageRequestDTO); // 메인 리스트
}
