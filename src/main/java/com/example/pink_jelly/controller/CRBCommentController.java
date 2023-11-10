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
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE) // 특정 crbNo 게시물에 댓글 추가
    public Map<String, String> register(@Valid @RequestBody CatsCommentDTO catsCommentDTO, BindingResult bindingResult) throws BindException {
        log.info(catsCommentDTO);

        if(bindingResult.hasErrors()) {
            throw new BindException((bindingResult));
        }
        Map<String, String> resultMap = new HashMap<>();
        crbCommentService.register(catsCommentDTO);

        resultMap.put("message", "댓글이 등록되었습니다."); // 메시지 추가

        return resultMap;
    }

    @ApiOperation(value = "Replies of Board", notes = "Get 방식으로 특정 게시물의 댓글 목록")
    @GetMapping(value = "/list/{crbNo}")
    public PageResponseDTO<CatsCommentDTO> getList(@PathVariable("crbNo") Long crbNo, PageRequestDTO pageRequestDTO){ // crbNo로 특정 게시물의 댓글 목록 츨략
        PageResponseDTO<CatsCommentDTO> responseDTO = crbCommentService.getListOfBoard(crbNo, pageRequestDTO);

        return responseDTO;
    }

    @ApiOperation(value = "Delete Reply", notes = "DELETE방식으로 특정 댓글 삭제")
    @DeleteMapping(value = "/{comNo}")
    public Map<String, Long> remove(@PathVariable("comNo") Long comNo){
        crbCommentService.remove(comNo);
        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("comNo", comNo);

        return  resultMap;
    }
}
