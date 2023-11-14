package com.example.pink_jelly.mapper;

import com.example.pink_jelly.domain.CatsMeBoardVO;
import com.example.pink_jelly.domain.CatsReviewBoardVO;
import com.example.pink_jelly.domain.MainBoardVO;
import com.example.pink_jelly.domain.MemberVO;
import com.example.pink_jelly.dto.MemberDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@Mapper // mybatis 사용할 때는 선언해줘야함
public interface CatsMeMapper {
    void insert(CatsMeBoardVO catsMeBoardVO); //게시물 등록
    List<CatsMeBoardVO> selectAll(PageRequestDTO pageRequestDTO); //전체 목록
    int getCount(PageRequestDTO pageRequestDTO); //총 게시물 수 = total
    List<CatsMeBoardVO> selectList(int skip, int size, Long mno, String memberId); //리스트 출력
    CatsMeBoardVO getOne(Long cmbNo); //게시판 불러오기
    void updateHit(Long cmbNo); //조회수 증가
    void deleteOne(Long cmbNo); // 삭제
    void updateBoard(CatsMeBoardVO catsMeBoardVO); //수정

    // 리뷰 게시판
    void insertReviewBoard(CatsReviewBoardVO catsReviewBoardVO); //게시물 등록
    List<CatsReviewBoardVO> selectReviewBoardAll(PageRequestDTO pageRequestDTO); //전체 목록
    int getReviewBoardCount(PageRequestDTO pageRequestDTO); //총 게시물 수 = total
    List<CatsReviewBoardVO> selectReviewBoardList(int skip, int size, Long mno, String memberId); //리스트 출력
    CatsReviewBoardVO getReviewBoardOne(Long crbNo); //게시판 불러오기
    void updateReviewBoardHit(Long crbNo); //조회수 증가
    void deleteReviewBoardOne(Long crbNo); // 삭제
    void updateReviewBoard(CatsReviewBoardVO catsReviewBoardVO); //수정

    boolean isReviewBoardLike(Long mno, Long crbNo); // 유저가 특정 게시물에 좋아요를 달았는지 여부

    boolean insertReviewBoardLike(Long mno, Long crbNo); // 좋아요 추가

    boolean removeReviewBoardLike(Long mno, Long crbNo); // 좋아요 제거
    void likeCntUpdate(Long crbNo, boolean flag); // 좋아요 수 업데이트
    void upCommentCnt(Long crbNo); //댓글 수 증가
    void downCommentCnt(Long mbNo); //댓글 수 감소
}
