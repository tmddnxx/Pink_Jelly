package com.example.pink_jelly.service;

import com.example.pink_jelly.domain.CatsMeBoardVO;
import com.example.pink_jelly.domain.CatsReviewBoardVO;
import com.example.pink_jelly.domain.MainBoardVO;
import com.example.pink_jelly.dto.*;
import com.example.pink_jelly.mapper.CatsMeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.expression.spel.ast.Literal;
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
        String catsMeImg = null;
        if(catsMeBoardDTO.getCatsMeImg() != null) {
            List<String> files = catsMeBoardDTO.getCatsMeImg();
            StringBuilder catsMeImgBuilder = new StringBuilder();
            for (int i = 0; i < files.size(); i++) {
                String file = files.get(i);
                catsMeImgBuilder.append(file);

                // 마지막 파일이 아니면 쉼표로 구분
                if (i < files.size() - 1) {
                    catsMeImgBuilder.append(", ");
                }
            }
            // 최종적으로 mainImg 문자열 생성
            catsMeImg = catsMeImgBuilder.toString();
        }
        CatsMeBoardVO catsMeBoardVO = CatsMeBoardVO.builder()
                .memberId(catsMeBoardDTO.getMemberId())
                .nickName(catsMeBoardDTO.getNickName())
                .profileImg(catsMeBoardDTO.getProfileImg())
                .title(catsMeBoardDTO.getTitle())
                .content(catsMeBoardDTO.getContent())
                .catsMeImg(catsMeImg)
                .mno(catsMeBoardDTO.getMno())
                .status(catsMeBoardDTO.getStatus())
                .build();
        catsMeMapper.insert(catsMeBoardVO);
    }

    @Override
    public PageResponseDTO<CatsMeBoardDTO> getList(PageRequestDTO pageRequestDTO, Long mno, String memberId) {
        List<CatsMeBoardVO> catsMeBoardVOList = catsMeMapper.selectList(pageRequestDTO.getSkip(), pageRequestDTO.getSize(),
                pageRequestDTO.getType(), pageRequestDTO.getKeyword(), mno, memberId);
        List<CatsMeBoardDTO> catsMeBoardDTOList = new ArrayList<>();
        catsMeBoardVOList.forEach(catsMeBoardVO -> {
            CatsMeBoardDTO catsMeBoardDTO = modelMapper.map(catsMeBoardVO, CatsMeBoardDTO.class);
            List<String> imgs = List.of(catsMeBoardVO.getCatsMeImg().split(", "));

            List<String> dateList = new ArrayList<>();
            List<String> fileList = new ArrayList<>();

            for(String img : imgs) {
                String[] parts = img.split("/");
                if(parts.length == 2) {
                    fileList.add(parts[0]);
                    dateList.add(parts[1]);
                }
            }
            catsMeBoardDTO.setBoardDateString(dateList);
            catsMeBoardDTO.setCatsMeImg(fileList);

            String[] profileImg = catsMeBoardVO.getProfileImg().split("/");
            catsMeBoardDTO.setProfileImg(profileImg[0]);
            catsMeBoardDTO.setDateString(profileImg[1]);

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

        List<String> imgs = List.of(catsMeBoardVO.getCatsMeImg().split(", "));

        List<String> dateList = new ArrayList<>();
        List<String> fileList = new ArrayList<>();
        for(String img : imgs) {
            String[] parts = img.split("/");
            if(parts.length == 2) {
                fileList.add(parts[0]);
                dateList.add(parts[1]);
            }
        }
        catsMeBoardDTO.setCatsMeImg(fileList);
        catsMeBoardDTO.setBoardDateString(dateList);

        String[] profile = catsMeBoardDTO.getProfileImg().split("/");
        catsMeBoardDTO.setProfileImg(profile[0]);
        catsMeBoardDTO.setDateString(profile[1]);

        return catsMeBoardDTO;
    }

    @Override
    public void upHit(Long cmbNo) {
        catsMeMapper.updateHit(cmbNo);
    }

    @Override
    public void modifyBoard(CatsMeBoardDTO catsMeBoardDTO) {
        String catsMeImg = null;
        if (catsMeBoardDTO.getCatsMeImg() != null) {
            List<String> files = catsMeBoardDTO.getCatsMeImg();
            StringBuilder CatsMeImgBuilder = new StringBuilder();
            for (int i = 0; i < files.size(); i++) {
                String file = files.get(i);
                String[] splits = file.split("/");
                file = splits[1] +"/" + splits[0];
                CatsMeImgBuilder.append(file);

                // 마지막 파일이 아니면 쉼표로 구분
                if (i < files.size() - 1) {
                    CatsMeImgBuilder.append(", ");
                }
            }
            // 최종적으로 mainImg 문자열 생성
            catsMeImg = CatsMeImgBuilder.toString();
        }

        CatsMeBoardVO catsMeBoardVO = CatsMeBoardVO.builder()
                .title(catsMeBoardDTO.getTitle())
                .content(catsMeBoardDTO.getContent())
                .catsMeImg(catsMeImg)
                .profileImg(catsMeBoardDTO.getProfileImg())
                .status(catsMeBoardDTO.getStatus())
                .cmbNo(catsMeBoardDTO.getCmbNo())
                .build();

        catsMeMapper.updateBoard(catsMeBoardVO);
    }

    @Override
    public void removeOne(Long cmbNo){
        catsMeMapper.deleteOne(cmbNo);
    }


    //catsReviewBoard
    @Override
    public void registerReviewBoard(CatsReviewBoardDTO catsReviewBoardDTO) {
        String catsMeImg = null;
        if(catsReviewBoardDTO.getCatsMeImg() != null) {
            List<String> files = catsReviewBoardDTO.getCatsMeImg();
            StringBuilder catsMeImgBuilder = new StringBuilder();
            for (int i = 0; i < files.size(); i++) {
                String file = files.get(i);
                catsMeImgBuilder.append(file);

                // 마지막 파일이 아니면 쉼표로 구분
                if (i < files.size() - 1) {
                    catsMeImgBuilder.append(", ");
                }
            }
            // 최종적으로 mainImg 문자열 생성
            catsMeImg = catsMeImgBuilder.toString();
        }
        CatsReviewBoardVO catsReviewBoardVO = CatsReviewBoardVO.builder()
                .memberId(catsReviewBoardDTO.getMemberId())
                .nickName(catsReviewBoardDTO.getNickName())
                .profileImg(catsReviewBoardDTO.getProfileImg())
                .title(catsReviewBoardDTO.getTitle())
                .content(catsReviewBoardDTO.getContent())
                .catsMeImg(catsMeImg)
                .mno(catsReviewBoardDTO.getMno())
                .build();
        catsMeMapper.insertReviewBoard(catsReviewBoardVO);
    }

    @Override
    public PageResponseDTO<CatsReviewBoardDTO> getReviewBoardList(PageRequestDTO pageRequestDTO, Long mno, String memberId) {
        List<CatsReviewBoardVO> catsMeBoardVOList = catsMeMapper.selectReviewBoardList(pageRequestDTO.getSkip(), pageRequestDTO.getSize(),
                pageRequestDTO.getType(), pageRequestDTO.getKeyword(), mno, memberId);
        List<CatsReviewBoardDTO> catsReviewBoardDTOList = new ArrayList<>();
        catsMeBoardVOList.forEach(catsReviewBoardVO -> {
            CatsReviewBoardDTO catsReviewBoardDTO = modelMapper.map(catsReviewBoardVO, CatsReviewBoardDTO.class);
            List<String> imgs = List.of(catsReviewBoardVO.getCatsMeImg().split(", "));

            List<String> dateList = new ArrayList<>();
            List<String> fileList = new ArrayList<>();

            for (String img : imgs) {
                String[] parts = img.split("/");
                if (parts.length == 2) {
                    fileList.add(parts[0]);
                    dateList.add(parts[1]);
                }
            }
            catsReviewBoardDTO.setCatsMeImg(fileList);
            catsReviewBoardDTO.setBoardDateString(dateList);

            String[] profileImg = catsReviewBoardVO.getProfileImg().split("/");
            catsReviewBoardDTO.setProfileImg(profileImg[0]);
            catsReviewBoardDTO.setDateString(profileImg[1]);

            catsReviewBoardDTOList.add(catsReviewBoardDTO);
        });



        int total = catsMeMapper.getReviewBoardCount(pageRequestDTO);

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

        List<String> imgs = List.of(catsReviewBoardVO.getCatsMeImg().split(", "));
        List<String> dateList = new ArrayList<>();
        List<String> fileList = new ArrayList<>();
        for (String img : imgs) {
            String[] parts = img.split("/");
            if (parts.length == 2) {
                fileList.add(parts[0]);
                dateList.add(parts[1]);
            }
        }
        catsReviewBoardDTO.setCatsMeImg(fileList);
        catsReviewBoardDTO.setBoardDateString(dateList);

        String[] profile = catsReviewBoardDTO.getProfileImg().split("/");
        catsReviewBoardDTO.setProfileImg(profile[0]);
        catsReviewBoardDTO.setDateString(profile[1]);
        catsReviewBoardDTO.setFlag(flag);
        return catsReviewBoardDTO;
    }

    @Override
    public void upReviewBoardHit(Long crbNo) {
        catsMeMapper.updateReviewBoardHit(crbNo);
    }

    @Override
    public void modifyReviewBoard(CatsReviewBoardDTO catsReviewBoardDTO) {
        String catsMeImg = null;
        if (catsReviewBoardDTO.getCatsMeImg() != null) {
            List<String> files = catsReviewBoardDTO.getCatsMeImg();
            StringBuilder CatsMeImgBuilder = new StringBuilder();
            for (int i = 0; i < files.size(); i++) {
                String file = files.get(i);
                String[] splits = file.split("/");
                file = splits[1] +"/" + splits[0];
                CatsMeImgBuilder.append(file);

                // 마지막 파일이 아니면 쉼표로 구분
                if (i < files.size() - 1) {
                    CatsMeImgBuilder.append(", ");
                }
            }
            // 최종적으로 mainImg 문자열 생성
            catsMeImg = CatsMeImgBuilder.toString();
        }

        CatsReviewBoardVO catsReviewBoardVO = CatsReviewBoardVO.builder()
                .title(catsReviewBoardDTO.getTitle())
                .content(catsReviewBoardDTO.getContent())
                .catsMeImg(catsMeImg)
                .profileImg(catsReviewBoardDTO.getProfileImg())
                .crbNo(catsReviewBoardDTO.getCrbNo())
                .build();
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