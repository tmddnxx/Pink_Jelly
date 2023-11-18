package com.example.pink_jelly.mapper;

import com.example.pink_jelly.domain.EternalRestCommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EternalRestCommentMapper {
    //댓글 등록
    int insert(EternalRestCommentVO eternalRestCommentVO);
    //총 댓글 수
    int getCount(Long comNo);
    //리스트 출려
    List<EternalRestCommentVO> selectList(@Param("erbNo") Long erbNo, @Param("skip") int skip , @Param("size") int size);
    //댓글 삭제
    int deleteOne(Long comNo); //댓글 삭제
}
