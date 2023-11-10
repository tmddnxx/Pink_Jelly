package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.*;
import com.example.pink_jelly.service.CatsMeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public String CatsMe(Model model, PageRequestDTO pageRequestDTO){
        log.info("/catsMe Get");
        PageResponseDTO<CatsMeBoardDTO> catsMeBoardList = catsMeService.getList(pageRequestDTO);
        model.addAttribute("catsMeBoardList", catsMeBoardList);
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
    public void viewReview(Long crbNo, Model model, HttpServletRequest request){
        log.info("/catsMe/review/view");
        CatsReviewBoardDTO catsReviewBoardDTO = null;
        String requestedUrl = request.getRequestURI();

        // 로그인 mno 가져오기
        Long mno = null;

        if(requestedUrl.equals("/catsMe/review/view")){
            catsReviewBoardDTO = catsMeService.getReviewBoard(crbNo, "view");
            catsReviewBoardDTO.setFlag(catsMeService.isReviewBoardLike(15L, crbNo));
        }else {
            catsReviewBoardDTO = catsMeService.getReviewBoard(crbNo, "modify");
        }
        model.addAttribute("catsReviewBoard", catsReviewBoardDTO);

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
