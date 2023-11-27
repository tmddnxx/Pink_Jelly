package com.example.pink_jelly.admin.service;

import com.example.pink_jelly.domain.MemberVO;
import com.example.pink_jelly.dto.AdminSearchDTO;
import com.example.pink_jelly.dto.MemberDTO;
import com.example.pink_jelly.domain.*;
import com.example.pink_jelly.dto.*;
import com.example.pink_jelly.mapper.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class AdminServiceImpl implements AdminService{
    private final ModelMapper modelMapper;
    private final BanMapper banMapper;
    private final FriendsMapper friendsMapper;
    private final MemberMapper memberMapper;
    private final MainBoardMapper mainBoardMapper;
    private final MainCommentMapper mainCommentMapper;
    private final CatsMeMapper catsMeMapper;
    private final CRBCommentMapper crbCommentMapper;

    @Override
    public List<MemberDTO> adminMemberSearch(AdminSearchDTO adminSearchDTO) {
        List<MemberVO> memberVOList = memberMapper.searchAll(adminSearchDTO);
        List<MemberDTO> memberDTOList = new ArrayList<>();
        memberVOList.forEach(memberVO -> {
            MemberDTO memberDTO = modelMapper.map(memberVO, MemberDTO.class);
            memberDTOList.add(memberDTO);
        });

        return memberDTOList;
    }

    @Override
    public void removeMember(Long mno) {
        memberMapper.deleteMember(mno);
        friendsMapper.deleteGmingAll(mno);
        banMapper.deleteBanAll(mno);
    }

    @Override
    public PageResponseDTO<MainBoardDTO> mainList(PageRequestDTO pageRequestDTO) { // 메인 리스트
        List<MainBoardVO> voList = mainBoardMapper.selectAll(pageRequestDTO);
        List<MainBoardDTO> dtoList = new ArrayList<>();

        voList.forEach(mainBoardVO -> {
            MainBoardDTO mainBoardDTO = modelMapper.map(mainBoardVO, MainBoardDTO.class);

            dtoList.add(mainBoardDTO);
        });

        int total = mainBoardMapper.getCount(pageRequestDTO);

        PageResponseDTO<MainBoardDTO> pageResponseDTO = PageResponseDTO.<MainBoardDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
        return pageResponseDTO;
    }

    @Override
    public MainBoardDTO mainView(Long mbNo) { // 메인 뷰
        MainBoardVO mainBoardVO = mainBoardMapper.getOne(mbNo);
        MainBoardDTO mainBoardDTO = modelMapper.map(mainBoardVO, MainBoardDTO.class);

        List<String> imgs = List.of(mainBoardVO.getMainImg().split(", "));

        List<String> dateList = new ArrayList<>();
        List<String> fileList = new ArrayList<>();

        for(String img : imgs){
            String[] parts = img.split("/");
            if(parts.length == 2){
                fileList.add(parts[0]);
                dateList.add(parts[1]);
            }
        }

        String[] profile = mainBoardVO.getProfileImg().split("/");

        mainBoardDTO.setProfileImg(profile[0]);
        mainBoardDTO.setDateString(profile[1]);
        mainBoardDTO.setMainImg(fileList);
        mainBoardDTO.setBoardDateString(dateList);
        return mainBoardDTO;
    }

    @Override
    public void mainDelete(Long mbNo) { // 메인 삭제
        mainBoardMapper.deleteOne(mbNo);
    }

    @Override
    public PageResponseDTO<MainCommentDTO> mainCommentList(Long mbNo, PageRequestDTO pageRequestDTO) { // 메인 댓글 리스트
        List<MainCommentVO> voList = mainCommentMapper.selectAll(mbNo, pageRequestDTO.getType(), pageRequestDTO.getKeyword());
        List<MainCommentDTO> dtoList = new ArrayList<>();

        voList.forEach(mainCommentVO -> {
            dtoList.add(modelMapper.map(mainCommentVO, MainCommentDTO.class));
        });

        dtoList.forEach(mainCommentDTO -> {
            String[] splits = mainCommentDTO.getProfileImg().split("/");
            String profileImg = splits[0];
            String dateString = splits[1];
            mainCommentDTO.setProfileImg(profileImg);
            mainCommentDTO.setDateString(dateString);
        });

        return PageResponseDTO.<MainCommentDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(mainCommentMapper.getCount(mbNo))
                .build();
    }

    @Override
    public void mainCommentRemove(Long comNo, Long mbNo) { // 메인 댓글 삭제
        mainCommentMapper.deleteOne(mbNo);
    }

    @Override
    public PageResponseDTO<CatsMeBoardDTO> catsList(PageRequestDTO pageRequestDTO) { // 캣츠미 리스트
        List<CatsMeBoardVO> voList = catsMeMapper.selectAll(pageRequestDTO);
        List<CatsMeBoardDTO> dtoList = new ArrayList<>();
        voList.forEach(catsMeBoardVO -> {
            CatsMeBoardDTO catsMeBoardDTO = modelMapper.map(catsMeBoardVO, CatsMeBoardDTO.class);

            dtoList.add(catsMeBoardDTO);
        });

        int total = catsMeMapper.getCount(pageRequestDTO);

        PageResponseDTO<CatsMeBoardDTO> pageResponseDTO = PageResponseDTO.<CatsMeBoardDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
        return pageResponseDTO;
    }

    @Override
    public CatsMeBoardDTO catsView(Long cmbNo) { // 캣츠미 뷰

        CatsMeBoardVO catsMeBoardVO = catsMeMapper.getOne(cmbNo);
        CatsMeBoardDTO catsMeBoardDTO = modelMapper.map(catsMeBoardVO, CatsMeBoardDTO.class);

        List<String> imgs = List.of(catsMeBoardVO.getCatsMeImg().split(", "));

        List<String> dateList = new ArrayList<>();
        List<String> fileList = new ArrayList<>();

        for(String img : imgs){
            String[] parts = img.split("/");
            if(parts.length == 2){
                fileList.add(parts[0]);
                dateList.add(parts[1]);
            }
        }

        String[] profile = catsMeBoardVO.getProfileImg().split("/");

        catsMeBoardDTO.setProfileImg(profile[0]);
        catsMeBoardDTO.setDateString(profile[1]);
        catsMeBoardDTO.setCatsMeImg(fileList);
        catsMeBoardDTO.setBoardDateString(dateList);
        return catsMeBoardDTO;
    }

    @Override
    public void catsDelete(Long cmbNo) { // 캣츠 미 삭제
        catsMeMapper.deleteOne(cmbNo);
    }


    @Override
    public PageResponseDTO<CatsReviewBoardDTO> reviewList(PageRequestDTO pageRequestDTO) { // 리뷰 리스트
        List<CatsReviewBoardVO> voList = catsMeMapper.selectReviewBoardAll(pageRequestDTO);
        List<CatsReviewBoardDTO> dtoList = new ArrayList<>();
        voList.forEach(catsReviewBoardVO -> {
            CatsReviewBoardDTO catsReviewBoardDTO = modelMapper.map(catsReviewBoardVO, CatsReviewBoardDTO.class);

            dtoList.add(catsReviewBoardDTO);
        });

        int total = catsMeMapper.getReviewBoardCount(pageRequestDTO);

        PageResponseDTO<CatsReviewBoardDTO> pageResponseDTO = PageResponseDTO.<CatsReviewBoardDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
        return pageResponseDTO;
    }

    @Override
    public CatsReviewBoardDTO reviewView(Long crbNo) { // 리뷰 뷰
        CatsReviewBoardVO catsReviewBoardVO = catsMeMapper.getReviewBoardOne(crbNo);
        CatsReviewBoardDTO catsReviewBoardDTO = modelMapper.map(catsReviewBoardVO, CatsReviewBoardDTO.class);

        List<String> imgs = List.of(catsReviewBoardVO.getCatsMeImg().split(", "));

        List<String> dateList = new ArrayList<>();
        List<String> fileList = new ArrayList<>();

        for(String img : imgs){
            String[] parts = img.split("/");
            if(parts.length == 2){
                fileList.add(parts[0]);
                dateList.add(parts[1]);
            }
        }

        String[] profile = catsReviewBoardVO.getProfileImg().split("/");

        catsReviewBoardDTO.setProfileImg(profile[0]);
        catsReviewBoardDTO.setDateString(profile[1]);
        catsReviewBoardDTO.setCatsMeImg(fileList);
        catsReviewBoardDTO.setBoardDateString(dateList);
        return catsReviewBoardDTO;
    }

    @Override
    public void reviewDelete(Long crbNo) { // 리뷰 삭제
        catsMeMapper.deleteReviewBoardOne(crbNo);
    }

    @Override
    public PageResponseDTO<CRBCommentDTO> reviewCommentList(Long crbNo, PageRequestDTO pageRequestDTO) { // 리뷰 댓글 목록
        List<CRBCommentVO> voList = crbCommentMapper.selectAll(crbNo, pageRequestDTO.getType(), pageRequestDTO.getKeyword());
        List<CRBCommentDTO> dtoList = new ArrayList<>();

        voList.forEach(crbCommentVO -> {
            dtoList.add(modelMapper.map(crbCommentVO, CRBCommentDTO.class));
        });

        dtoList.forEach(crbCommentDTO  -> {
            String[] splits = crbCommentDTO.getProfileImg().split("/");
            String profileImg = splits[0];
            String dateString = splits[1];
            crbCommentDTO.setProfileImg(profileImg);
            crbCommentDTO.setDateString(dateString);
        });

        return PageResponseDTO.<CRBCommentDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(mainCommentMapper.getCount(crbNo))
                .build();
    }

    @Override
    public void reviewCommentRemove(Long comNo, Long crbNo) { // 리뷰 댓글 삭제
        crbCommentMapper.deleteOne(comNo);
    }
}
