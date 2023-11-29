package com.example.pink_jelly.catsMe.controller;

import com.example.pink_jelly.catsMe.service.CatsMeService;
import com.example.pink_jelly.catsMe.dto.CatsReviewBoardDTO;
import com.example.pink_jelly.catsMe.dto.CatsMeBoardDTO;
import com.example.pink_jelly.dto.*;
import com.example.pink_jelly.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@Log4j2
@RequestMapping({"/catsMe"})
@RequiredArgsConstructor
public class CatsMeController {

    @Value("${com.example.tempUpload.path}")
    private String tempPath;
    @Value("${com.example.catsMeUpload.path}")
    private String catsMePath;
    private final CatsMeService catsMeService;
    @GetMapping("")
    public String CatsMe(Model model, PageRequestDTO pageRequestDTO, Long mno, String memberId ,@AuthenticationPrincipal MemberDTO memberDTO){
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
        if(catsMeBoardDTO.getCatsMeImg() != null) {
            for(int i = 0; i < catsMeBoardDTO.getCatsMeImg().size(); i++) {
                String cmbImg = catsMeBoardDTO.getCatsMeImg().get(i);
                String[] share = cmbImg.split("/");
                moveFile(share[0]);
                moveFile("s_" + share[0]);
            }
        }
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
    public String modify(CatsMeBoardDTO catsMeBoardDTO, Model model, String removeImg){
        log.info("Post modify");
        log.info(catsMeBoardDTO.getCmbNo());
        CatsMeBoardDTO checkBoard = catsMeService.getBoard(catsMeBoardDTO.getCmbNo(), "check");
        for(int i =0; i < checkBoard.getCatsMeImg().size(); i++) {
            for(int j = 0; j < catsMeBoardDTO.getCatsMeImg().size(); j++) {
                String[] img = catsMeBoardDTO.getCatsMeImg().get(j).split("/");
                String catsMeImg = img[1];
                if(!(checkBoard.getCatsMeImg().get(i).equals(catsMeImg))) {
                    moveFile(catsMeImg);
                    moveFile("s_" + catsMeImg);
                }
            }
        }
        catsMeService.modifyBoard(catsMeBoardDTO);

        if(!(removeImg.equals(""))) {
            List<String> removeImgs = List.of(removeImg.split(","));

            List<String> dateList = new ArrayList<>();
            List<String> fileList = new ArrayList<>();
            for (String img : removeImgs) {
                String[] parts = img.split("/");
                if (parts.length == 2) {
                    fileList.add(parts[1]);
                    dateList.add(parts[0]);
                }
            }
            dateList.forEach(log::info);
            fileList.forEach(log::info);

            for (int i = 0; i < removeImgs.size(); i++) {
                removeFiles(dateList.get(i), fileList.get(i));
            }
        }
        return "redirect:/catsMe/board/view?cmbNo=" + catsMeBoardDTO.getCmbNo();
    }
    @GetMapping("/board/remove")
    public String remove(Long cmbNo, @AuthenticationPrincipal MemberDTO memberDTO){
        Long mno = memberDTO.getMno();
        CatsMeBoardDTO catsMeBoardDTO = catsMeService.getBoard(cmbNo, "remove");
        List<String> catsMeImg = catsMeBoardDTO.getCatsMeImg();
        List<String> boardDateString= catsMeBoardDTO.getBoardDateString();

        if(catsMeImg != null & catsMeImg.size() > 0) {
            for (int i = 0; i < catsMeImg.size(); i ++) {
                removeFiles(boardDateString.get(i), catsMeImg.get(i));
            }
        }
        catsMeService.removeOne(cmbNo);
        return "redirect:/catsMe";
    }



    //review

    @GetMapping("/review/list")
    public void CatsMeReview(Model model, PageRequestDTO pageRequestDTO, Long mno, String memberId ,@AuthenticationPrincipal MemberDTO memberDTO){
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
        if(catsReviewBoardDTO.getCatsMeImg() != null) {
            for(int i = 0; i < catsReviewBoardDTO.getCatsMeImg().size(); i ++){
                String catsMeImg = catsReviewBoardDTO.getCatsMeImg().get(i);
                String[] share = catsMeImg.split("/");
                moveFile(share[0]);
                moveFile("s_" + share[0]);
            }
        }
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
            if (requestedUrl.equals("/catsMe/review/view")) {
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
    public String modifyReview(CatsReviewBoardDTO catsReviewBoardDTO, Model model, String removeImg){
        CatsReviewBoardDTO checkBoard = catsMeService.getReviewBoard(catsReviewBoardDTO.getCrbNo(), "check", catsReviewBoardDTO.getMno());
        for(int i =0; i < checkBoard.getCatsMeImg().size(); i++) {
            for(int j = 0; j < catsReviewBoardDTO.getCatsMeImg().size(); j++) {
                String[] img = catsReviewBoardDTO.getCatsMeImg().get(j).split("/");
                String catsMeImg = img[1];
                if(!(checkBoard.getCatsMeImg().get(i).equals(catsMeImg))) {
                    moveFile(catsMeImg);
                    moveFile("s_" + catsMeImg);
                }
            }
        }


        catsMeService.modifyReviewBoard(catsReviewBoardDTO);

        log.info(removeImg);
        if(!(removeImg.equals(""))) {
            List<String> removeImgs = List.of(removeImg.split(","));

            List<String> dateList = new ArrayList<>();
            List<String> fileList = new ArrayList<>();
            for (String img : removeImgs) {
                String[] parts = img.split("/");
                if (parts.length == 2) {
                    fileList.add(parts[1]);
                    dateList.add(parts[0]);
                }
            }
            dateList.forEach(log::info);
            fileList.forEach(log::info);
            for (int i = 0; i < removeImgs.size(); i++) {
                removeFiles(dateList.get(i), fileList.get(i));
            }
        }

        return "redirect:/catsMe/review/view?crbNo=" + catsReviewBoardDTO.getCrbNo();
    }
    @GetMapping("/review/remove")
    public String removeReview(Long crbNo, @AuthenticationPrincipal MemberDTO memberDTO){
        Long mno = memberDTO.getMno();
        CatsReviewBoardDTO catsReviewBoardDTO = catsMeService.getReviewBoard(crbNo, "remove", mno);
        List<String> catsMeImg = catsReviewBoardDTO.getCatsMeImg();
        List<String> boardDateString = catsReviewBoardDTO.getBoardDateString();

        if(catsMeImg != null & catsMeImg.size() > 0) {
            for(int i = 0; i < catsMeImg.size(); i++) {
                removeFiles(boardDateString.get(i), catsMeImg.get(i));
            }
        }
        catsMeService.removeReviewBoardOne(crbNo);
        return "redirect:/catsMe/review/list";
    }


    public void removeFiles(String boardDateString, String catsMeImg) {
        Resource resource = new FileSystemResource(catsMePath + File.separator + boardDateString + File.separator + catsMeImg);
        log.info(resource);
        try{
            String contentType = Files.probeContentType(resource.getFile().toPath());
            resource.getFile().delete();

            //섬네일이 존재한다면
            if(contentType.startsWith("image")) {
                File thumbFile = new File(catsMePath + File.separator + boardDateString + File.separator + "s_" + catsMeImg);
                thumbFile.delete();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


    private void moveFile(String fileName) {
        /* 등록시에 첨부 파일을 이동 */

        // 오늘 날짜로 폴더 생성
        LocalDate currentDate = LocalDate.now(); // 오늘 날짜 가져오기
        // 날짜 포맷 지정(원하는 형식으로)

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = currentDate.format(formatter); // 날짜를 문자열로 변환

        String newTempPath = tempPath + "/" + dateString;
        String newProfilePath = catsMePath + "/" + dateString;

        File file = new File(newProfilePath);
        if (!file.exists()) { // 폴더가 존재하지 않으면
            file.mkdirs(); // 폴더 생성
        }

        try {
            // 읽을 파일과 쓰기 파일을 구분해서 객체 생성.
            FileInputStream fileInputStream = new FileInputStream(newTempPath + File.separator + fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(newProfilePath + File.separator + fileName);

            // 파일 복사의 경우 buffer를 사용해야 속도가 빠름.
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            int i;
            while ((i = bufferedInputStream.read()) != -1) { // 파일 끝까지 읽기
                bufferedOutputStream.write(i); // 읽은 만큼 쓰기
            }
            bufferedInputStream.close();
            bufferedOutputStream.close();
            fileInputStream.close();
            fileOutputStream.close();

            Files.delete(Paths.get(newTempPath + File.separator + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
