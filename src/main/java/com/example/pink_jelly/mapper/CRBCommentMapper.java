package com.example.pink_jelly.mapper;

import com.example.pink_jelly.domain.CatsCommentVO;
import com.example.pink_jelly.dto.PageRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper // mybatis 사용할 때는 선언해줘야함
public interface CRBCommentMapper {
    void insert (CatsCommentVO catsCommentVO);

    void delete(Long comNo);

    List<CatsCommentVO> selectList(Long crbNo, int skip, int size);

    int getCount(Long crbNo, PageRequestDTO pageRequestDTO);

}
