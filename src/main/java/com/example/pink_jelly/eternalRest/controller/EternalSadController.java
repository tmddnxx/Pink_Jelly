package com.example.pink_jelly.eternalRest.controller;

import com.example.pink_jelly.eternalRest.service.EternalRestBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/sad")
public class EternalSadController {

    private final EternalRestBoardService eternalRestBoardService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> add(@RequestParam Long mno, @RequestParam Long erbNo){
        Map<String, Object> response = new HashMap<>();

        try {
            boolean result = eternalRestBoardService.addSad(mno, erbNo);

            response.put("result", result);
            log.info("sad added successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("result", false);
            log.error("Error occurred while adding like: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<Map<String, Object>> remove(@RequestParam Long mno, @RequestParam Long erbNo) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean result = eternalRestBoardService.removeSad(mno, erbNo);

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
