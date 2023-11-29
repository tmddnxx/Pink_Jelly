package com.example.pink_jelly.eternalRest.mapper;

import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.eternalRest.vo.EternalRestBoardVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EternalRestBoardMapper {

    void insert(EternalRestBoardVO eternalRestBoardVO); // 장레식 게시물 자동 등록

    int getCount(PageRequestDTO pageRequestDTO); // 총 게시물 수

    List<EternalRestBoardVO> selectList(PageRequestDTO pageRequestDTO); // 게시물 목록

    EternalRestBoardVO getOne(Long erbNo); // 게시물 상세

    void delete(Long erbNo); // 게시물 삭제

    boolean isRestSad(@Param("mno") Long mno, @Param("erbNo") Long erbNo); // 로그인유저가 특정 게시물에 슬퍼요를 달았는지 여부

    boolean insertRestSad(@Param("mno") Long mno, @Param("erbNo") Long erbNo); // 슬퍼요 추가 (mno = 로그인유저)

    boolean removeRestSad(@Param("mno") Long mno, @Param("erbNo")  Long erbNo); // 슬퍼요 제거 (mno = 로그인유저)

    void sadCntUpdate(@Param("erbNo") Long erbNo, @Param("flag") boolean flag); // 슬퍼요 수 업데이트

    void catInfoDel(Long mno); // 고양이 정보 삭제

    void upCommentCnt (Long erbNo); //댓글수 증가

    void downCommentCnt (Long erbNo); //댓글 수 감소



}
