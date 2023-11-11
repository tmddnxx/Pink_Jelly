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
        mainBoardVOList.forEach(mainBoardVO -> mainBoardDTOList.add(modelMapper.map(mainBoardVO, MainBoardDTO.class)));
        int total = mainBoardMapper.getCount(pageRequestDTO);

        PageResponseDTO<MainBoardDTO> pageResponseDTO = PageResponseDTO.<MainBoardDTO>withAll()
                .dtoList(mainBoardDTOList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
        return pageResponseDTO;
    }

    @Override
    public MainBoardDTO getBoard(Long mbNo, String mode) {
        MainBoardVO mainBoardVO;
        boolean flag = false;
//        Long mno = null; // 로그인 mno 수정 예정
        if(mode.equals("view")) {
            mainBoardVO = mainBoardMapper.getOne(mbNo);
            mainBoardMapper.updateHit(mbNo);
//            flag = mainBoardMapper.isMainBoardLike(mno, mbNo);
        }else {
            mainBoardVO = mainBoardMapper.getOne(mbNo);
        }
        MainBoardDTO mainBoardDTO = modelMapper.map(mainBoardVO, MainBoardDTO.class);
//        mainBoardDTO.setFlag(flag);
        return mainBoardDTO;
    }

    @Override
    public void upHit(Long mbNo){
        mainBoardMapper.updateHit(mbNo);
    }
    @Override
    public void removeOne(Long mbNo) {
        mainBoardMapper.deleteOne(mbNo);
    }

    @Override
    public void modifyBoard(MainBoardDTO mainBoardDTO) {
        MainBoardVO mainBoardVO = modelMapper.map(mainBoardDTO, MainBoardVO.class);
        mainBoardMapper.updateBoard(mainBoardVO);
    }

    @Override
    public boolean addBoardLike(Long mno, Long mbNo) { // 좋아요 달기
        mainBoardMapper.likeCntUpdate(mbNo, true);

        return mainBoardMapper.insertMainBoardLike(mno, mbNo);
    }

    @Override
    public boolean removeBoardLike(Long mno, Long mbNo) { // 좋아요 제거
        mainBoardMapper.likeCntUpdate(mbNo, false);

        return mainBoardMapper.removeMainBoardLike(mno, mbNo);
    }

    @Override
    public boolean isBoardLike(Long mno, Long mbNo) { // 좋아요 여부


        return mainBoardMapper.isMainBoardLike(mno, mbNo);
    }
}
