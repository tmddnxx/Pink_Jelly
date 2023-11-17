package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.MainBoardDTO;
import com.example.pink_jelly.dto.MemberDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;
import com.example.pink_jelly.service.MainBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.security.Principal;

@Log4j2
@Controller
@RequestMapping({"/main", "/"})
@RequiredArgsConstructor
public class MainBoardController {
    @Value("${com.example.tempUpload.path}")
    private String tempPath;
    @Value("${com.example.mainBoardUpload.path}")
    private String mainBoardPath;
    private final MainBoardService mainBoardService;

    @GetMapping("")
    public String main(Model model, PageRequestDTO pageRequestDTO, Long mno, String memberId, @AuthenticationPrincipal MemberDTO memberDTO) {
        log.info("main GET ...");
        log.info("타입은? : " + pageRequestDTO.getType());
        log.info("키워드는? : " + pageRequestDTO.getKeyword());
        pageRequestDTO.setType(pageRequestDTO.getType()); // ?
        if(memberDTO != null){
            String loginId = memberDTO.getMemberId();
            Long loginMno = memberDTO.getMno();
            PageResponseDTO<MainBoardDTO> mainBoardList = mainBoardService.getList(pageRequestDTO, loginMno, loginId);
            model.addAttribute("mainBoardList", mainBoardList);
            mainBoardList.getDtoList().forEach(log::info);
        } else {
            log.info("메인 멤버디티오 : " + memberDTO);
            PageResponseDTO<MainBoardDTO> mainBoardList = mainBoardService.getList(pageRequestDTO, mno, memberId);
            model.addAttribute("mainBoardList", mainBoardList);
            mainBoardList.getDtoList().forEach(log::info);
        }

        return "/main/list";
    }

    //글쓰기 페이지 이동
    @GetMapping("/write")
    public void write() {
        System.out.println("main write GET...");
    }

    @GetMapping({"/view", "/modify"})
    public void view(Long mbNo, Model model,Long mno ,HttpServletRequest request, @AuthenticationPrincipal MemberDTO memberDTO) {
        log.info("/main/view GET...");
        log.info(mbNo);
        MainBoardDTO mainBoardDTO = null;
        String requestedUrl = request.getRequestURI();
        // 로그인 한 놈 들고와

        if(memberDTO != null){
            mno = memberDTO.getMno();
            if (requestedUrl.equals("/main/view")) {
                mainBoardDTO = mainBoardService.getBoard(mbNo, "view", mno);
                mainBoardDTO.setFlag(mainBoardService.isBoardLike(mno, mbNo));
            } else {
                mainBoardDTO = mainBoardService.getBoard(mbNo, "modify", mno);
            }
            log.info(mainBoardDTO);
            model.addAttribute("mainBoard", mainBoardDTO);
        } else {
            if (requestedUrl.equals("/main/view")) {
                mainBoardDTO = mainBoardService.getBoard(mbNo, "view", mno);

            } else {
                mainBoardDTO = mainBoardService.getBoard(mbNo, "modify", mno);
            }
            log.info(mainBoardDTO);
            model.addAttribute("mainBoard", mainBoardDTO);
        }

    }

    //게시판 등록
    @PostMapping("/write")
    public String write(MainBoardDTO mainBoardDTO, HttpServletRequest request) {
        log.info("/main/write... postMapping");


        if (mainBoardDTO.getMainImg() != null) {
            for(int i = 0; i < mainBoardDTO.getMainImg().size(); i++) {
                String mainImg = mainBoardDTO.getMainImg().get(i);
                String[] share = mainImg.split("/");
                moveFile(share[0]);
                moveFile("s_" + share[0]);
            }
            String splits[] = mainBoardDTO.getProfileImg().split("/");

            moveFile(splits[0]);
            moveFile("s_" + splits[0]);
        }

        mainBoardService.register(mainBoardDTO);
        return "redirect:/main";
    }

    //게시판 삭제
    @GetMapping("/remove")
    public String remove(Long mbNo, @AuthenticationPrincipal MemberDTO memberDTO) {
        Long mno = memberDTO.getMno();
        MainBoardDTO mainBoardDTO = mainBoardService.getBoard(mbNo, "remove", mno);
        List<String> files = mainBoardDTO.getMainImg();

        if(files != null && files.size() > 0) {
            removeFiles(files);
            log.info("removeFiles----------------");

        }
        mainBoardService.removeOne(mbNo);
        return "redirect:/main";
    }
    public void removeFiles(List<String> files) {
        for(String fileName:files) {
            Resource resource = new FileSystemResource(mainBoardPath + File.separator + fileName);
            log.info(resource);
            try{
                String contentType = Files.probeContentType(resource.getFile().toPath());
                resource.getFile().delete();

                //섬네일이 존재한다면
                if(contentType.startsWith("image")) {
                    File thumbFile = new File(mainBoardPath + File.separator + "s_" + fileName);
                    thumbFile.delete();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    @PostMapping("/modify")
    public String modify(MainBoardDTO mainBoardDTO, Model model) {
        log.info("Post modify");
        log.info(mainBoardDTO);
        mainBoardService.modifyBoard(mainBoardDTO);
        return "redirect:/main/view?mbNo=" + mainBoardDTO.getMbNo();
    }



    private void moveFile(String fileName) {
        /* 등록시에 첨부 파일을 이동 */

        // 오늘 날짜로 폴더 생성
        LocalDate currentDate = LocalDate.now(); // 오늘 날짜 가져오기
        // 날짜 포맷 지정(원하는 형식으로)

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = currentDate.format(formatter); // 날짜를 문자열로 변환

        String newTempPath = tempPath + "/" + dateString;
        String newProfilePath = mainBoardPath + "/" + dateString;

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