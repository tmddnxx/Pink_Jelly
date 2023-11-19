package com.example.pink_jelly.service;

import com.example.pink_jelly.domain.CatsMeBoardVO;
import com.example.pink_jelly.domain.CatsReviewBoardVO;
import com.example.pink_jelly.domain.MainBoardVO;
import com.example.pink_jelly.dto.*;
import com.example.pink_jelly.mapper.CatsMeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CatsMeServiceImpl implements CatsMeService{
    private final ModelMapper modelMapper;
    private final CatsMeMapper catsMeMapper;

    @Override
    public void register(CatsMeBoardDTO catsMeBoardDTO) {
        CatsMeBoardVO catsMeBoardVO = modelMapper.map(catsMeBoardDTO, CatsMeBoardVO.class);
        catsMeMapper.insert(catsMeBoardVO);
    }

    @Override
    public PageResponseDTO<CatsMeBoardDTO> getList(PageRequestDTO pageRequestDTO, Long mno, String memberId) {
        List<CatsMeBoardVO> catsMeBoardVOList = catsMeMapper.selectList(pageRequestDTO.getSkip(), pageRequestDTO.getSize(),
                pageRequestDTO.getType(), pageRequestDTO.getKeyword(), mno, memberId);
        List<CatsMeBoardDTO> catsMeBoardDTOList = new ArrayList<>();
        catsMeBoardVOList.forEach(catsMeBoardVO -> {
            CatsMeBoardDTO catsMeBoardDTO = modelMapper.map(catsMeBoardVO, CatsMeBoardDTO.class);
            String[] profileImg = catsMeBoardVO.getProfileImg().split("/");
            catsMeBoardDTO.setProfileImg(profileImg[0]);
            catsMeBoardDTO.setProfileString(profileImg[1]);

            catsMeBoardDTOList.add(catsMeBoardDTO);

        });

        catsMeBoardDTOList.forEach(log::info);

        int total = catsMeMapper.getCount(pageRequestDTO);

        PageResponseDTO<CatsMeBoardDTO> pageResponseDTO = PageResponseDTO.<CatsMeBoardDTO>withAll()
                .dtoList(catsMeBoardDTOList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
        return pageResponseDTO;
    }

    @Override
    public CatsMeBoardDTO getBoard(Long cmbNo, String mode) {
        CatsMeBoardVO catsMeBoardVO = null;
        if(mode.equals("view")){
            catsMeBoardVO = catsMeMapper.getOne(cmbNo);
            catsMeMapper.updateHit(cmbNo);
        } else{
            catsMeBoardVO = catsMeMapper.getOne(cmbNo);
        }
        CatsMeBoardDTO catsMeBoardDTO = modelMapper.map(catsMeBoardVO, CatsMeBoardDTO.class);
        String[] profile = catsMeBoardDTO.getProfileImg().split("/");
        catsMeBoardDTO.setProfileImg(profile[0]);
        catsMeBoardDTO.setProfileString(profile[1]);

        return catsMeBoardDTO;
    }

    @Override
    public void upHit(Long cmbNo) {
        catsMeMapper.updateHit(cmbNo);
    }

    @Override
    public void modifyBoard(CatsMeBoardDTO catsMeBoardDTO) {
        CatsMeBoardVO catsMeBoardVO = modelMapper.map(catsMeBoardDTO, CatsMeBoardVO.class);
        catsMeMapper.updateBoard(catsMeBoardVO);
    }

    @Override
    public void removeOne(Long cmbNo){
        catsMeMapper.deleteOne(cmbNo);
    }


    //catsReviewBoard
    @Override
    public void registerReviewBoard(CatsReviewBoardDTO catsReviewBoardDTO) {
        CatsReviewBoardVO catsReviewBoardVO = modelMapper.map(catsReviewBoardDTO, CatsReviewBoardVO.class);
        catsMeMapper.insertReviewBoard(catsReviewBoardVO);
    }

    @Override
    public PageResponseDTO<CatsReviewBoardDTO> getReviewBoardList(PageRequestDTO pageRequestDTO, Long mno, String memberId) {
        List<CatsReviewBoardVO> catsMeBoardVOList = catsMeMapper.selectReviewBoardList(pageRequestDTO.getSkip(), pageRequestDTO.getSize(),
                pageRequestDTO.getType(), pageRequestDTO.getKeyword(), mno, memberId);
        List<CatsReviewBoardDTO> catsReviewBoardDTOList = new ArrayList<>();
        catsMeBoardVOList.forEach(catsReviewBoardVO -> {
            CatsReviewBoardDTO catsReviewBoardDTO = modelMapper.map(catsReviewBoardVO, CatsReviewBoardDTO.class);
            String[] profileImg = catsReviewBoardVO.getProfileImg().split("/");
            catsReviewBoardDTO.setProfileImg(profileImg[0]);
            catsReviewBoardDTO.setProfileString(profileImg[1]);

            catsReviewBoardDTOList.add(catsReviewBoardDTO);
        });



        int total = catsMeMapper.getCount(pageRequestDTO);

        PageResponseDTO<CatsReviewBoardDTO> pageResponseDTO = PageResponseDTO.<CatsReviewBoardDTO>withAll()
                .dtoList(catsReviewBoardDTOList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
        return pageResponseDTO;
    }

    @Override
    public CatsReviewBoardDTO getReviewBoard(Long crbNo, String mode, Long mno) {
        CatsReviewBoardVO catsReviewBoardVO = null;
        boolean flag = false;
        if(mode.equals("view")){
            catsReviewBoardVO = catsMeMapper.getReviewBoardOne(crbNo);
            catsMeMapper.updateReviewBoardHit(crbNo);
            flag = catsMeMapper.isReviewBoardLike(mno, crbNo);
        } else{
            catsReviewBoardVO = catsMeMapper.getReviewBoardOne(crbNo);
        }
        CatsReviewBoardDTO catsReviewBoardDTO = modelMapper.map(catsReviewBoardVO, CatsReviewBoardDTO.class);
        String[] profile = catsReviewBoardDTO.getProfileImg().split("/");
        catsReviewBoardDTO.setProfileImg(profile[0]);
        catsReviewBoardDTO.setProfileString(profile[1]);
        catsReviewBoardDTO.setFlag(flag);
        return catsReviewBoardDTO;
    }

    @Override
    public void upReviewBoardHit(Long crbNo) {
        catsMeMapper.updateReviewBoardHit(crbNo);
    }

    @Override
    public void modifyReviewBoard(CatsReviewBoardDTO catsReviewBoardDTO) {
        CatsReviewBoardVO catsReviewBoardVO = modelMapper.map(catsReviewBoardDTO, CatsReviewBoardVO.class);
        catsMeMapper.updateReviewBoard(catsReviewBoardVO);
    }

    @Override
    public void removeReviewBoardOne(Long crbNo) {
        catsMeMapper.deleteReviewBoardOne(crbNo);
    }

    // 리뷰 게시판 좋아요 달기
    @Override
    public boolean addReviewBoardLike(Long mno, Long crbNo) { // 좋아요 추가
        catsMeMapper.likeCntUpdate(crbNo, true);

        return catsMeMapper.insertReviewBoardLike(mno, crbNo);
    }

    @Override
    public boolean removeReviewBoardLike(Long mno, Long crbNo) { // 좋아요 제거
        catsMeMapper.likeCntUpdate(crbNo, false);

        return catsMeMapper.removeReviewBoardLike(mno, crbNo);
    }

    @Override
    public boolean isReviewBoardLike(Long mno, Long crbNo) { // 좋아요 여부

        return catsMeMapper.isReviewBoardLike(mno, crbNo);
    }
}