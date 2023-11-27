package com.example.pink_jelly.mapper;

import com.example.pink_jelly.domain.CRBCommentVO;
import com.example.pink_jelly.dto.PageRequestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CRBCommentMapper {
    int insert(CRBCommentVO CRBCommentVO); //댓글 등록
    List<CRBCommentVO> selectAll(@Param("crbNo") Long crbNo, @Param("type") String type, @Param("keyword") String keyword); //전체 댓글
    int getCount(Long comNo); //총 댓글 수 = total
    List<CRBCommentVO> selectList(@Param("crbNo") Long crbNo, @Param("skip") int skip, @Param("size") int size); //리스트 출력
    int deleteOne(Long comNo); // 댓글삭제

    void updateParentNo(); // 부모번호 업데이트
    void updateCnt(Long crbNo); // 댓글 수 업데이트
}
