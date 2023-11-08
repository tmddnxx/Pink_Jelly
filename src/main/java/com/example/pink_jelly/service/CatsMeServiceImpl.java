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
    public PageResponseDTO<CatsMeBoardDTO> getList(PageRequestDTO pageRequestDTO) {
        List<CatsMeBoardVO> catsMeBoardVOList = catsMeMapper.selectList(pageRequestDTO);
        List<CatsMeBoardDTO> catsMeBoardDTOList = new ArrayList<>();
        catsMeBoardVOList.forEach(catsMeBoardVO -> catsMeBoardDTOList.add(modelMapper.map(catsMeBoardVO, CatsMeBoardDTO.class)));
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
    public PageResponseDTO<CatsReviewBoardDTO> getReviewBoardList(PageRequestDTO pageRequestDTO) {
        List<CatsReviewBoardVO> catsMeBoardVOList = catsMeMapper.selectReviewBoardList(pageRequestDTO);
        List<CatsReviewBoardDTO> catsReviewBoardDTOList = new ArrayList<>();
        catsMeBoardVOList.forEach(catsReviewBoardVO -> catsReviewBoardDTOList.add(modelMapper.map(catsReviewBoardVO, CatsReviewBoardDTO.class)));
        int total = catsMeMapper.getCount(pageRequestDTO);

        PageResponseDTO<CatsReviewBoardDTO> pageResponseDTO = PageResponseDTO.<CatsReviewBoardDTO>withAll()
                .dtoList(catsReviewBoardDTOList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
        return pageResponseDTO;
    }

    @Override
    public CatsReviewBoardDTO getReviewBoard(Long crbNo, String mode) {
        CatsReviewBoardVO catsReviewBoardVO = null;
        if(mode.equals("view")){
            catsReviewBoardVO = catsMeMapper.getReviewBoardOne(crbNo);
            catsMeMapper.updateReviewBoardHit(crbNo);
        } else{
            catsReviewBoardVO = catsMeMapper.getReviewBoardOne(crbNo);
        }
        CatsReviewBoardDTO catsReviewBoardDTO = modelMapper.map(catsReviewBoardVO, CatsReviewBoardDTO.class);
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
}