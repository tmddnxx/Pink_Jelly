package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.MainBoardDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;
import com.example.pink_jelly.service.MainBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Controller
@RequestMapping({"/main", "/"})
@RequiredArgsConstructor
public class MainBoardController {
    private final MainBoardService mainBoardService;
    @GetMapping("")
    public String main(Model model, PageRequestDTO pageRequestDTO){
        log.info("main GET ...");
        PageResponseDTO<MainBoardDTO> mainBoardList = mainBoardService.getList(pageRequestDTO);
        model.addAttribute("mainBoardList", mainBoardList);
        mainBoardList.getDtoList().forEach(log::info);
        return "/main/list";
    }
    //글쓰기 페이지 이동
    @GetMapping("/write")
    public void write() {
        System.out.println("main write GET...");
    }

    @GetMapping({"/view", "/modify"})
    public void view(Long mbNo, Model model, HttpServletRequest request) {
        log.info("/main/view GET...");
        log.info(mbNo);
        MainBoardDTO mainBoardDTO = null;
        String requestedUrl = request.getRequestURI();
        if(requestedUrl.equals("/main/view")) {
            mainBoardDTO = mainBoardService.getBoard(mbNo, "view");
        }
        else{
            mainBoardDTO = mainBoardService.getBoard(mbNo, "modify");
        }
        model.addAttribute("mainBoard", mainBoardDTO);
    }
    //게시판 등록
    @PostMapping("/write")
    public String write(MainBoardDTO mainBoardDTO, HttpServletRequest request){
        log.info("/main/write... postMapping");
        mainBoardService.register(mainBoardDTO);
        return "redirect:/main";
    }
    //게시판 삭제
    @GetMapping("/remove")
    public String remove(Long mbNo){
        mainBoardService.removeOne(mbNo);
        return "redirect:/main";
    }

    @PostMapping("/modify")
    public String modify(MainBoardDTO mainBoardDTO, Model model){
        log.info("Post modify");
        log.info(mainBoardDTO);
        mainBoardService.modifyBoard(mainBoardDTO);
        return "redirect:/main/view?mbNo=" + mainBoardDTO.getMbNo();
    }
}
