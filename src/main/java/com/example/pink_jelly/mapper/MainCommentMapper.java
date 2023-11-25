package com.example.pink_jelly.mapper;

import com.example.pink_jelly.domain.MainCommentVO;
import com.example.pink_jelly.dto.MainCommentDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MainCommentMapper {
    int insert(MainCommentVO mainCommentVO); //댓글 등록
    List<MainCommentVO> selectAll(@Param("mbNo") Long mbNo, PageRequestDTO pageRequestDTO); //전체 댓글
    int getCount(Long mbNo); //총 댓글 수 = total
    List<MainCommentVO> selectList(@Param("mbNo") Long mbNo, @Param("skip") int skip, @Param("size")int size); //리스트로 출력
    int deleteOne(Long comNo);

    void updateParentNo(); // 부모번호 업데이트

    List<MainCommentVO> selectListOrderByDESC(@Param("mbNo") Long mbNo, @Param("skip") int skip, @Param("size")int size);




}
