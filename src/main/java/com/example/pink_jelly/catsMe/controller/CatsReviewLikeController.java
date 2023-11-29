package com.example.pink_jelly.catsMe.controller;

import com.example.pink_jelly.catsMe.service.CatsMeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/reviewLike")
@RequiredArgsConstructor
public class CatsReviewLikeController {
    private final CatsMeService catsMeService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> add(@RequestParam Long mno, @RequestParam Long crbNo){
        Map<String, Object> response = new HashMap<>();

        try {
            boolean result = catsMeService.addReviewBoardLike(mno, crbNo);

            response.put("result", result);
            log.info("Like added successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("result", false);
            log.error("Error occurred while adding like: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<Map<String, Object>> remove(@RequestParam Long mno, @RequestParam Long crbNo) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean result = catsMeService.removeReviewBoardLike(mno, crbNo);

            response.put("result", result);
            log.info("Like removed successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("result", false);
            log.error("Error occurred while removing like: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
