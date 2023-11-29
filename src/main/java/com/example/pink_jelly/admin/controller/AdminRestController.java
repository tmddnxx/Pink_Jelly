package com.example.pink_jelly.admin.controller;

import com.example.pink_jelly.admin.AdminSearchDTO;
import com.example.pink_jelly.catsMe.dto.CatsMeBoardDTO;
import com.example.pink_jelly.catsMe.dto.CatsReviewBoardDTO;
import com.example.pink_jelly.crbComment.dto.CRBCommentDTO;
import com.example.pink_jelly.dto.*;
import com.example.pink_jelly.admin.service.AdminService;
import com.example.pink_jelly.mainBoard.dto.MainBoardDTO;
import com.example.pink_jelly.mainComment.dto.MainCommentDTO;
import com.example.pink_jelly.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Log4j2
public class AdminRestController {
    @Value("${com.example.mainBoardUpload.path}")
    private String mainBoardPath;

    @Value("${com.example.catsMeUpload.path}")
    private String catsMePath;
    @Value("${com.example.profileUpload.path}")
    private String profilePath;
    private final AdminService adminService;

    //리스트 불러오기
    @GetMapping(value = "/memberList")
    public List<MemberDTO> getMemberList(AdminSearchDTO adminSearchDTO){
        log.info(adminSearchDTO);
        log.info("--------------");

        List<MemberDTO> memberDTOList = adminService.adminMemberSearch(adminSearchDTO);

        log.info(memberDTOList);
        log.info("--------------");
        return memberDTOList;
    }
    //낱개 삭제
    @DeleteMapping(value = "/removeMember/{mno}")
    public void deleteMember(@PathVariable("mno")Long mno){
        adminService.removeMember(mno);
    }

    @GetMapping("/mainList")
    public PageResponseDTO<MainBoardDTO> mainList(PageRequestDTO pageRequestDTO) { // 메인 게시물 호출
        log.info("main GET ...");

        PageResponseDTO<MainBoardDTO> response = adminService.mainList(pageRequestDTO);

        return response;
    }

    @GetMapping("/mainList/{mbNo}")
    public MainBoardDTO mainView(@PathVariable("mbNo") Long mbNo){ // 메인 게시물 뷰
        MainBoardDTO mainBoardDTO = adminService.mainView(mbNo);

        return mainBoardDTO;
    }

    @DeleteMapping("/mainList/{mbNo}")
    public void mainDelete(@PathVariable("mbNo") Long mbNo){ // 메인삭제
        MainBoardDTO mainBoardDTO = adminService.mainView(mbNo);

        List<String> mainImg = mainBoardDTO.getMainImg();
        List<String> boardDateString = mainBoardDTO.getBoardDateString();

        if(mainImg != null && mainImg.size()>0){
            for(int i=0; i< mainImg.size(); i++){
                removeFiles(mainBoardPath, boardDateString.get(i), mainImg.get(i));
            }
        }
        adminService.mainDelete(mbNo);
    }

    @GetMapping("/mainComment/{mbNo}") // 메인 댓글 목록
    public PageResponseDTO<MainCommentDTO> mainCommentList(@PathVariable("mbNo") Long mbNo, PageRequestDTO pageRequestDTO){
        PageResponseDTO<MainCommentDTO> mainComment = adminService.mainCommentList(mbNo, pageRequestDTO);

        return mainComment;
    }

    @DeleteMapping("/mainComment/{comNo}/{mbNo}") // 메인 댓글 삭제
    public Map<String, Long> mainCommentRemove(@PathVariable("comNo") Long comNo, @PathVariable("mbNo") Long mbNo){

        adminService.mainCommentRemove(comNo, mbNo);
        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("comNo", comNo);

        return resultMap;
    }

    @GetMapping("/catsMeList")
    public PageResponseDTO<CatsMeBoardDTO> catsMeList(PageRequestDTO pageRequestDTO) { // 캣츠미 게시물 호출
        log.info("catsMe GET ...");

        PageResponseDTO<CatsMeBoardDTO> response =  adminService.catsList(pageRequestDTO);

        return response;
    }
    @GetMapping("/catsMeList/{cmbNo}")
    public CatsMeBoardDTO catsView(@PathVariable("cmbNo") Long cmbNo){ // 캣츠미 게시물 뷰
        CatsMeBoardDTO catsMeBoardDTO = adminService.catsView(cmbNo);

        return catsMeBoardDTO;
    }

    @DeleteMapping("/catsMeList/{cmbNo}")
    public void catsDelete(@PathVariable("cmbNo") Long cmbNo){ // 캣츠미 삭제
        CatsMeBoardDTO catsMeBoardDTO = adminService.catsView(cmbNo);

        List<String> catsImg = catsMeBoardDTO.getCatsMeImg();
        List<String> boardDateString = catsMeBoardDTO.getBoardDateString();

        if(catsImg != null && catsImg.size()>0){
            for(int i=0; i< catsImg.size(); i++){
                removeFiles(catsMePath, boardDateString.get(i), catsImg.get(i));
            }
        }
        adminService.catsDelete(cmbNo);
    }

    @GetMapping("/reviewList")
    public PageResponseDTO<CatsReviewBoardDTO> reviewList(PageRequestDTO pageRequestDTO) { // 리뷰 게시물 호출
        log.info("review GET ...");

        PageResponseDTO<CatsReviewBoardDTO> response =  adminService.reviewList(pageRequestDTO);

        return response;
    }

    @GetMapping("/reviewList/{crbNo}")
    public CatsReviewBoardDTO reviewView(@PathVariable("crbNo") Long crbNo){ // 리뷰 게시물 뷰
        CatsReviewBoardDTO catsReviewBoardDTO = adminService.reviewView(crbNo);

        return catsReviewBoardDTO;
    }

    @DeleteMapping("/reviewList/{crbNo}")
    public void reviewDelete(@PathVariable("crbNo") Long crbNo){ // 리뷰 삭제
        CatsReviewBoardDTO catsReviewBoardDTO = adminService.reviewView(crbNo);

        List<String> catsImg = catsReviewBoardDTO.getCatsMeImg();
        List<String> boardDateString = catsReviewBoardDTO.getBoardDateString();

        if(catsImg != null && catsImg.size()>0){
            for(int i=0; i< catsImg.size(); i++){
                removeFiles(catsMePath, boardDateString.get(i), catsImg.get(i));
            }
        }
        adminService.reviewDelete(crbNo);
    }

    @GetMapping("/reviewComment/{crbNo}") // 리뷰 댓글 목록
    public PageResponseDTO<CRBCommentDTO> reviewCommentList(@PathVariable("crbNo") Long crbNo, PageRequestDTO pageRequestDTO){
        PageResponseDTO<CRBCommentDTO> reviewComment = adminService.reviewCommentList(crbNo, pageRequestDTO);
        log.info("리뷰 댓글 목록"+reviewComment);
        return reviewComment;
    }

    @DeleteMapping("/reviewComment/{crbNo}/{comNo}") // 리뷰 댓글 삭제
    public Map<String, Long> reviewCommentRemove(@PathVariable("comNo") Long comNo, @PathVariable("crbNo") Long crbNo){
        log.info("리뷰 댓글 삭제 : " + comNo, crbNo);
        adminService.reviewCommentRemove(comNo, crbNo);
        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("comNo", comNo);

        return resultMap;
    }

    private void removeFiles(String path, String boardDateString, String mainImg) { // 사진파일 삭제
        Resource resource = new FileSystemResource(path + File.separator + boardDateString + File.separator + mainImg);
        log.info(resource);
        try{
            String contentType = Files.probeContentType(resource.getFile().toPath());
            resource.getFile().delete();

            //섬네일이 존재한다면
            if(contentType.startsWith("image")) {
                File thumbFile = new File(mainBoardPath + File.separator + boardDateString + File.separator + "s_" + mainImg);
                thumbFile.delete();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
