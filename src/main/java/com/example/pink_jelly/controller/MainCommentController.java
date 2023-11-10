package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.MainCommentDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;
import com.example.pink_jelly.service.MainCommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/mainComment")
@RequiredArgsConstructor //의존성 주입
public class MainCommentController {
    private final MainCommentService mainCommentService;

    @ApiOperation(value = "Comments POST", notes = "POST 방식으로 댓글 등록")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> register(@Valid @RequestBody MainCommentDTO mainCommentDTO, BindingResult bindingResult) throws BindException {
        log.info("왔습니다j--------------");
        if(bindingResult.hasErrors()) {
            throw new BindException((bindingResult));
        }
        mainCommentService.register(mainCommentDTO);
        Map<String, Long> resultMap = new HashMap<>();
        Long rno = 1L;
        resultMap.put("rno", rno);

        return resultMap;
    }
    @ApiOperation(value = "Replies of Board", notes = "GET 방식으로 특정 게시물의 댓글 목록")
    @GetMapping(value = "/list/{mbNo}")
    public PageResponseDTO<MainCommentDTO> getList(@PathVariable("mbNo") Long mbNo, PageRequestDTO pageRequestDTO) {
        // @PathVariable 경로에 있는 값 사용
        PageResponseDTO<MainCommentDTO> mainComment = mainCommentService.getListMainComment(mbNo, pageRequestDTO);

        return mainComment;
    }
}
