package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.CatsCommentDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;
import com.example.pink_jelly.service.CRBCommentService;
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
@RequestMapping("/crbComment")
@RequiredArgsConstructor
public class CRBCommentController {

    private final CRBCommentService crbCommentService;

    @ApiOperation(value = "Replies POST", notes = "POST 방식으로 댓글 등록")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE) // 특정 crbNo 게시물에 댓글 추가
    public Map<String, Long> register(@Valid @RequestBody CRBCommentDTO CRBCommentDTO, BindingResult bindingResult) throws BindException {
//        log.info(CRBCommentDTO);
        if(bindingResult.hasErrors()) {
            throw new BindException((bindingResult));
        }
        Map<String, Long> resultMap = new HashMap<>();
        Long comNo = crbCommentService.register(CRBCommentDTO);

        resultMap.put("comNo", comNo); // 메시지 추가

        return resultMap;
    }

    @ApiOperation(value = "Replies of Board", notes = "Get 방식으로 특정 게시물의 댓글 목록")
    @GetMapping(value = "/list/{crbNo}")
    public PageResponseDTO<CRBCommentDTO> getList(@PathVariable("crbNo") Long crbNo, PageRequestDTO pageRequestDTO){ // crbNo로 특정 게시물의 댓글 목록 츨략
        log.info("/list/crbNo---------------"+ crbNo + pageRequestDTO.getSkip()+ pageRequestDTO.getSize());
        PageResponseDTO<CRBCommentDTO> crbComment = crbCommentService.getListCRBComment(crbNo, pageRequestDTO);
//        log.info("crbComment----------" + crbComment);
        return crbComment;
    }

    @ApiOperation(value = "Delete Reply", notes = "DELETE방식으로 특정 댓글 삭제")
    @DeleteMapping(value = "/{comNo}/{crbNo}")
    public Map<String, Long> remove(@PathVariable("comNo") Long comNo, @PathVariable("crbNo") Long crbNo){
        crbCommentService.remove(comNo, crbNo);
        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("comNo", comNo);

        return  resultMap;
    }
}
