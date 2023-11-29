package com.example.pink_jelly.mainBoard.service;

import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;
import com.example.pink_jelly.mainBoard.vo.MainBoardVO;
import com.example.pink_jelly.mainBoard.dto.MainBoardDTO;
import com.example.pink_jelly.mainBoard.mapper.MainBoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MainBoardServiceImpl implements MainBoardService {

    private final ModelMapper modelMapper;
    private final MainBoardMapper mainBoardMapper;

    //게시판 등록
    @Override
    public void register(MainBoardDTO mainBoardDTO) {
        String mainImg = null;
        if (mainBoardDTO.getMainImg() != null) {
            List<String> files = mainBoardDTO.getMainImg();
            StringBuilder mainImgBuilder = new StringBuilder();
            for (int i = 0; i < files.size(); i++) {
                String file = files.get(i);
                mainImgBuilder.append(file);

                // 마지막 파일이 아니면 쉼표로 구분
                if (i < files.size() - 1) {
                    mainImgBuilder.append(", ");
                }
            }
            // 최종적으로 mainImg 문자열 생성
            mainImg = mainImgBuilder.toString();
        }
        MainBoardVO mainBoardVO = MainBoardVO.builder()
                .memberId(mainBoardDTO.getMemberId())
                .nickName(mainBoardDTO.getNickName())
                .profileImg(mainBoardDTO.getProfileImg())
                .title(mainBoardDTO.getTitle())
                .content(mainBoardDTO.getContent())
                .mainImg(mainImg)
                .myCat(mainBoardDTO.getMyCat())
                .variety(mainBoardDTO.getVariety())
                .mno(mainBoardDTO.getMno())
                .build();
        mainBoardMapper.insert(mainBoardVO);
    }

    @Override
    public PageResponseDTO<MainBoardDTO> getList(PageRequestDTO pageRequestDTO, Long mno, String memberId) {
        log.info(pageRequestDTO);
        log.info(mno);
        log.info(memberId);
        List<MainBoardVO> mainBoardVOList = mainBoardMapper.selectList(pageRequestDTO.getSkip(), pageRequestDTO.getSize(), pageRequestDTO.getType(), pageRequestDTO.getKeyword(), mno, memberId);
        List<MainBoardDTO> mainBoardDTOList = new ArrayList<>();

        mainBoardVOList.forEach(mainBoardVO -> {
            MainBoardDTO mainBoardDTO = modelMapper.map(mainBoardVO, MainBoardDTO.class);
            List<String> imgs = List.of(mainBoardVO.getMainImg().split(", "));

            List<String> dateList = new ArrayList<>();
            List<String> fileList = new ArrayList<>();

            for (String img : imgs) {
                String[] parts = img.split("/");
                if (parts.length == 2) {
                    fileList.add(parts[0]);
                    dateList.add(parts[1]);
                }
            }
            //프로필 이미지 분리
            String[] profile = mainBoardVO.getProfileImg().split("/");


            mainBoardDTO.setProfileImg(profile[0]);
            mainBoardDTO.setDateString(profile[1]);
            mainBoardDTO.setBoardDateString(dateList);
            mainBoardDTO.setMainImg(fileList);
            mainBoardDTOList.add(mainBoardDTO);
        });

        int total = mainBoardMapper.getCount(pageRequestDTO);

        PageResponseDTO<MainBoardDTO> pageResponseDTO = PageResponseDTO.<MainBoardDTO>withAll()
                .dtoList(mainBoardDTOList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
        return pageResponseDTO;
    }

    @Override
    public MainBoardDTO getBoard(Long mbNo, String mode, Long mno) {
        MainBoardVO mainBoardVO = null;
        boolean flag = false;
        log.info("getBoard...");
        log.info(mbNo);

        if(mode.equals("view")) {
            mainBoardVO = mainBoardMapper.getOne(mbNo);
            mainBoardMapper.updateHit(mbNo);
            flag = mainBoardMapper.isMainBoardLike(mno, mbNo);
        }else {
            mainBoardVO = mainBoardMapper.getOne(mbNo);
        }

        MainBoardDTO mainBoardDTO = modelMapper.map(mainBoardVO, MainBoardDTO.class);
        log.info(mainBoardDTO);

        //mainImg 를 dateString과 fileName으로 나눔
        List<String> imgs = List.of(mainBoardVO.getMainImg().split(", "));

        List<String> dateList = new ArrayList<>();
        List<String> fileList = new ArrayList<>();
        for (String img : imgs) {
            String[] parts = img.split("/");
            if (parts.length == 2) {
                fileList.add(parts[0]);
                dateList.add(parts[1]);
            }
        }
        //프로필 이미지 분리
        String[] profile = mainBoardVO.getProfileImg().split("/");

        log.info(profile[0] + " 좀 좀 좀 " + profile[1]);

        mainBoardDTO.setProfileImg(profile[0]);
        mainBoardDTO.setDateString(profile[1]);
        mainBoardDTO.setBoardDateString(dateList);
        mainBoardDTO.setMainImg(fileList);
        mainBoardDTO.setFlag(flag);
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
        String mainImg = null;
        if (mainBoardDTO.getMainImg() != null) {
            List<String> files = mainBoardDTO.getMainImg();
            StringBuilder mainImgBuilder = new StringBuilder();
            for (int i = 0; i < files.size(); i++) {
                String file = files.get(i);
                String[] splits = file.split("/");
                file = splits[1] +"/" + splits[0];
                mainImgBuilder.append(file);

                // 마지막 파일이 아니면 쉼표로 구분
                if (i < files.size() - 1) {
                    mainImgBuilder.append(", ");
                }
            }
            // 최종적으로 mainImg 문자열 생성
            mainImg = mainImgBuilder.toString();
        }

        MainBoardVO mainBoardVO = MainBoardVO.builder()
                .title(mainBoardDTO.getTitle())
                .content(mainBoardDTO.getContent())
                .mainImg(mainImg)
                .myCat(mainBoardDTO.getMyCat())
                .variety(mainBoardDTO.getVariety())
                .mbNo(mainBoardDTO.getMbNo())
                .profileImg(mainBoardDTO.getProfileImg())
                .build();
        log.info("mainBoard MainImg : " + mainBoardVO.getMainImg());
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
