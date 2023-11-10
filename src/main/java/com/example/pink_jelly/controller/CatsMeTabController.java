package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.CatsMeBoardDTO;
import com.example.pink_jelly.dto.CatsReviewBoardDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;
import com.example.pink_jelly.service.CatsMeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/catsMeTab")
@RequiredArgsConstructor
@Log4j2
public class CatsMeTabController {
    private final CatsMeService catsMeService;

    @GetMapping("/board")
    public ResponseEntity<PageResponseDTO<CatsMeBoardDTO>>catsMeTab(@RequestParam int page, @RequestParam int size){
        log.info("사이즈 " + size);
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setPage(page);
        pageRequestDTO.setSize(size);
        PageResponseDTO<CatsMeBoardDTO> response = catsMeService.getList(pageRequestDTO);

       return ResponseEntity.ok(response);
    }

    @GetMapping("/review")
    public ResponseEntity<PageResponseDTO<CatsReviewBoardDTO>>catsReviewTab(@RequestParam int page, @RequestParam int size){
        log.info("사이즈 " + size);
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setPage(page);
        pageRequestDTO.setSize(size);
        PageResponseDTO<CatsReviewBoardDTO> response = catsMeService.getReviewBoardList(pageRequestDTO);

        return ResponseEntity.ok(response);
    }
}
