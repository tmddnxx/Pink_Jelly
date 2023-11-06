package com.example.pink_jelly.service;

import com.example.pink_jelly.domain.MainBoardVO;
import com.example.pink_jelly.dto.MainBoardDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;
import com.example.pink_jelly.mapper.MainBoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MainBoardServiceImpl implements MainBoardService{

    private final ModelMapper modelMapper;
    private final MainBoardMapper mainBoardMapper;

    //게시판 등록
    @Override
    public void register(MainBoardDTO mainBoardDTO) {
        MainBoardVO mainBoardVO = modelMapper.map(mainBoardDTO, MainBoardVO.class);
        mainBoardMapper.insert(mainBoardVO);
    }

    @Override
    public PageResponseDTO<MainBoardDTO> getList(PageRequestDTO pageRequestDTO) {
        List<MainBoardVO> mainBoardVOList = mainBoardMapper.selectList(pageRequestDTO);
        List<MainBoardDTO> mainBoardDTOList = new ArrayList<>();
        mainBoardVOList.forEach(mainBoardVO -> mainBoardDTOList.add(modelMapper.map(mainBoardVOList, MainBoardDTO.class)));
        int total = mainBoardMapper.getCount(pageRequestDTO);

        PageResponseDTO<MainBoardDTO> pageResponseDTO = PageResponseDTO.<MainBoardDTO>withAll()
                .dtoList(mainBoardDTOList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
        return pageResponseDTO;
    }
}
