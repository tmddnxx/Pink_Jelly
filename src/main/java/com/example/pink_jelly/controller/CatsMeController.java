package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.*;
import com.example.pink_jelly.service.CatsMeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@Log4j2
@RequestMapping({"/catsMe","/"})
@RequiredArgsConstructor
public class CatsMeController {

    private final CatsMeService catsMeService;
    @GetMapping("")
    public String CatsMe(Model model, PageRequestDTO pageRequestDTO,Long mno, String memberId ,@AuthenticationPrincipal MemberDTO memberDTO){
        log.info("/catsMe Get");
        if(memberDTO != null){
            String loginId = memberDTO.getMemberId();
            Long loginMno = memberDTO.getMno();
            PageResponseDTO<CatsMeBoardDTO> catsMeBoardList = catsMeService.getList(pageRequestDTO, loginMno, loginId);
            model.addAttribute("catsMeBoardList", catsMeBoardList);
        }else{
            PageResponseDTO<CatsMeBoardDTO> catsMeBoardList = catsMeService.getList(pageRequestDTO, mno, memberId);
            model.addAttribute("catsMeBoardList", catsMeBoardList);
        }

        return "/catsMe/board/list";
    }

    @GetMapping("/board/write")
    public void write() {
        System.out.println("catsMeBoard write GET...");

    }

    @PostMapping("/board/write")
    public String write(CatsMeBoardDTO catsMeBoardDTO){
        log.info("/catsMe/Board/write... postMapping");
        catsMeService.register(catsMeBoardDTO);
        return "redirect:/catsMe";
    }
    @GetMapping({"/board/view", "/board/modify"})
    public void view(Long cmbNo, Model model, HttpServletRequest request){
        log.info("/catsMe/view");
        CatsMeBoardDTO catsMeBoardDTO = null;
        String requestedUrl = request.getRequestURI();
        if(requestedUrl.equals("/catsMe/board/view")){
            catsMeBoardDTO = catsMeService.getBoard(cmbNo, "view");
        }else {
            catsMeBoardDTO = catsMeService.getBoard(cmbNo, "modify");
        }
        model.addAttribute("catsMeBoard", catsMeBoardDTO);

    }
    @PostMapping("/board/modify")
    public String modify(CatsMeBoardDTO catsMeBoardDTO, Model model){
        log.info("Post modify");
        log.info(catsMeBoardDTO.getCmbNo());
        catsMeService.modifyBoard(catsMeBoardDTO);
        return "redirect:/catsMe/board/view?cmbNo=" + catsMeBoardDTO.getCmbNo();
    }
    @GetMapping("/board/remove")
    public String remove(Long cmbNo){
        catsMeService.removeOne(cmbNo);
        return "redirect:/catsMe";
    }

    //review

    @GetMapping("/review/list")
    public void CatsMeReview(Model model, PageRequestDTO pageRequestDTO,Long mno, String memberId ,@AuthenticationPrincipal MemberDTO memberDTO){
        log.info("/catsMe/review/list Get");
        if(memberDTO != null){
            String loginId = memberDTO.getMemberId();
            Long loginMno = memberDTO.getMno();
            PageResponseDTO<CatsReviewBoardDTO> catsMeReviewBoardList = catsMeService.getReviewBoardList(pageRequestDTO, loginMno, loginId);
            model.addAttribute("catsMeReviewBoardList", catsMeReviewBoardList);
        }else{
            PageResponseDTO<CatsReviewBoardDTO> catsMeReviewBoardList = catsMeService.getReviewBoardList(pageRequestDTO, mno, memberId);
            model.addAttribute("catsMeReviewBoardList", catsMeReviewBoardList);
        }
    }

    @GetMapping("/review/write")
    public void writeReview() {
        System.out.println("catsMeReviewBoard write GET...");
    }

    @PostMapping("/review/write")
    public String writeReview(CatsReviewBoardDTO catsReviewBoardDTO){
        log.info("/catsMe/review/write... postMapping");
        catsMeService.registerReviewBoard(catsReviewBoardDTO);
        return "redirect:/catsMe/review/list";
    }
    @GetMapping({"/review/view", "/review/modify"})
    public void viewReview(Long crbNo, Model model, Long mno, HttpServletRequest request, @AuthenticationPrincipal MemberDTO memberDTO){
        log.info("/catsMe/review/view");
        CatsReviewBoardDTO catsReviewBoardDTO = null;
        String requestedUrl = request.getRequestURI();

        if(memberDTO != null){
            mno = memberDTO.getMno();
            if (requestedUrl.equals("/review/view")) {
                catsReviewBoardDTO = catsMeService.getReviewBoard(crbNo, "view", mno);
                catsReviewBoardDTO.setFlag(catsMeService.isReviewBoardLike(mno, crbNo));
            } else {
                catsReviewBoardDTO = catsMeService.getReviewBoard(crbNo, "modify", mno);
            }
            log.info(catsReviewBoardDTO);
            model.addAttribute("catsReviewBoard", catsReviewBoardDTO);
        } else {
            if (requestedUrl.equals("/review/view")) {
                catsReviewBoardDTO = catsMeService.getReviewBoard(crbNo, "view", mno);

            } else {
                catsReviewBoardDTO = catsMeService.getReviewBoard(crbNo, "modify", mno);
            }
            log.info(catsReviewBoardDTO);
            model.addAttribute("catsReviewBoard", catsReviewBoardDTO);
        }
    }
    @PostMapping("/review/modify")
    public String modifyReview(CatsReviewBoardDTO catsReviewBoardDTO, Model model){
        log.info("/review/modify post");
        log.info(catsReviewBoardDTO.getCrbNo());
        catsMeService.modifyReviewBoard(catsReviewBoardDTO);
        return "redirect:/catsMe/review/view?crbNo=" + catsReviewBoardDTO.getCrbNo();
    }
    @GetMapping("/review/remove")
    public String removeReview(Long crbNo){
        catsMeService.removeReviewBoardOne(crbNo);
        return "redirect:/catsMe/review/list";
    }
}
