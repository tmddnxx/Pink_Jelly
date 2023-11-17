package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.upload.UploadFileDTO;
import com.example.pink_jelly.dto.upload.UploadResultDTO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@Log4j2
@RequestMapping("/upload")
public class UpDownController {
    @Value("${com.example.tempUpload.path}")
    private String tempPath;

    @Value("${com.example.mainBoardUpload.path}")
    private String mainBoardPath; //메인게시판 저장 경로

    @Value("${com.example.profileUpload.path}")
    private String profilePath; // 프로필 저장 경로

    //임시 저장소 temp
    @ApiOperation(value = "Temp Upload Post", notes = "POST 방식으로 임시 파일 등록")
    @PostMapping(value = "/tempUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private List<UploadResultDTO> uploadFile(UploadFileDTO uploadFileDTO) {
        // 이미지 업로드
        log.info(uploadFileDTO);
        if (uploadFileDTO.getFiles() != null) {
            List<UploadResultDTO> list = new ArrayList<>();
            for (MultipartFile multipartFile : uploadFileDTO.getFiles()) {
                String originalName = multipartFile.getOriginalFilename();// 실제 이미지 파일 이름
                log.info(originalName);
                String fileName = UUID.randomUUID().toString() + "_" + originalName; // uuid + 실제 파일명

                // 오늘 날짜로 폴더 생성
                LocalDate currentDate = LocalDate.now(); // 오늘 날짜 가져오기
                // 날짜 포맷 지정(원하는 형식으로)

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dateString = currentDate.format(formatter); // 날짜를 문자열로 변환
                log.info(dateString);

                String path = tempPath + "/" + dateString;
                File file = new File(path);
                if (!file.exists()) { // 폴더가 존재하지 않으면
                    file.mkdirs(); // 폴더 생성
                }

                Path savePath = Paths.get(path, fileName); // 이미지 경로 저장
                boolean isImage = false;
                try {
                    multipartFile.transferTo(savePath); // 실제 파일 저장

                    // 이미지 파일이면 썸네일 생성
                    if (Files.probeContentType(savePath).startsWith("image")) {
                        log.info(Files.probeContentType(savePath));
                        isImage = true;
                        File thumbFile = new File(path, "s_" + fileName); // 썸네일 파일 경로
                        Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 100, 100);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 이미지 파일명 리턴
                list.add(UploadResultDTO.builder()
                        .fileName(fileName)
                        .isImage(isImage)
                        .dateString(dateString)
                        .build());
            }
            return list;
        }
        return null;
    }

    @ApiOperation(value = "Temp View GET", notes = "GET방식으로 임시 첨부파일 조회")
    @GetMapping("/tempView/{dateString}/{fileName}")
    public ResponseEntity<Resource> getTempViewFile(@PathVariable String dateString, @PathVariable String fileName) {
        // 임시 저장 이미지 조회
        return getViewFile(dateString ,fileName, tempPath);
    }



    //메인보드 //upload
    @ApiOperation(value = "view 파일", notes = "GET방식으로 첨부파일 조회")
    @GetMapping("/mainBoardView/{dateString}/{fileName}")
    public ResponseEntity<Resource> GetMainBoardViewFile(@PathVariable String dateString,  @PathVariable String fileName){
        // 메인보드 이미지 파일 조회
        return getViewFile(dateString, fileName, mainBoardPath);
    }

    @ApiOperation(value = "remove 파일", notes = "DELETE 방식으로 파일 삭제")
    @DeleteMapping("/mainBoardRemove/{fileName}")
    public Map<String, Boolean> removeMainBoardFile(@PathVariable String fileName) {
        // 메인보드 이미지 파일 삭제
        return removeFile(fileName, mainBoardPath);
    }



    //프로필 upload
    @ApiOperation(value = "Profile View GET", notes = "GET방식으로 프로필 첨부파일 조회")
    @GetMapping("/profileView/{dateString}/{fileName}")
    public ResponseEntity<Resource> getProfileViewFile(@PathVariable String dateString, @PathVariable String fileName) {
        // 프로필 사진 조회
        return getViewFile(dateString ,fileName, profilePath);
    }

    @ApiOperation(value = "Profile Remove DELETE", notes = "DELETE 방식으로 파일 삭제")
    @DeleteMapping("/profileRemove/{dateString}/{fileName}")
    private Map<String, Boolean> removeProfileFile(@PathVariable String fileName) {
        // 프로필 사진 삭제
        return removeFile(fileName, profilePath);
    }





    private ResponseEntity<Resource> getViewFile(String dateString, String fileName, String uploadPath) {
        // 첨부파일 조회
        Resource resource = new FileSystemResource(uploadPath +File.separator
                + dateString + File.separator + fileName);


        String resourceName = resource.getFilename();
        HttpHeaders headers = new HttpHeaders();

        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    private Map<String, Boolean> removeFile(String fileName, String uploadPath) {
        // 파일 삭제
        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
        String resourceName = resource.getFilename();


        Map<String, Boolean> resultMap = new HashMap<>();
        boolean removed = false;
        try{
            String contentType = Files.probeContentType(resource.getFile().toPath());
            removed = resource.getFile().delete(); //resource.delete 메서드로 삭제

            //썸네일이 존재한다면
            if(contentType.startsWith("image")){
                File thumbFile = new File(uploadPath + File.separator + "s_" + fileName);
                thumbFile.delete();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        resultMap.put("result", removed);
        return resultMap;
    }
}
