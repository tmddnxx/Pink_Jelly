package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.CRBCommentDTO;
import com.example.pink_jelly.dto.EternalRestCommentDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;
import com.example.pink_jelly.service.ErbCommentService;
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

@RestController
@Log4j2
@RequestMapping("/erbComment")
@RequiredArgsConstructor
public class ErbCommentController {
    private final ErbCommentService erbCommentService;
    //댓글 등록
    @ApiOperation(value = "ERBComments POST", notes = "POST 방식으로 댓글 등록")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> register(@Valid @RequestBody EternalRestCommentDTO eternalRestCommentDTO, BindingResult bindingResult) throws BindException {
//        log.info("왔습니다j--------------");
        if(bindingResult.hasErrors()) {
            throw new BindException((bindingResult));
        }
        Map<String, Long> resultMap = new HashMap<>();
        Long rno = erbCommentService.register(eternalRestCommentDTO);;

        resultMap.put("rno", rno);

        return resultMap;
    }
    @ApiOperation(value = "Replies of Board", notes = "GET 방식으로 특정 게시물의 댓글 목록")
    @GetMapping(value = "/list/{erbNo}")
    public PageResponseDTO<EternalRestCommentDTO> getList(@PathVariable("erbNo") Long erbNo, PageRequestDTO pageRequestDTO) {
        // @PathVariable 경로에 있는 값 사용
//        log.info(pageRequestDTO.getSkip());
        log.info("/list/erbNo---------------"+ erbNo + pageRequestDTO.getSkip()+ pageRequestDTO.getSize());

        PageResponseDTO<EternalRestCommentDTO> erbComment = erbCommentService.getListERBComment(erbNo, pageRequestDTO);

        return erbComment;
    }
    @ApiOperation(value = "Delete Reply", notes = "DELETE 방식으로 특정 댓글 삭제")
    @DeleteMapping(value = "/{comNo}/{erbNo}")
    public Map<String, Long> remove(@PathVariable("comNo") Long comNo, @PathVariable("erbNo") Long erbNo) {
        erbCommentService.remove(comNo, erbNo);
        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("comNo", comNo);
        return resultMap;
    }
}
