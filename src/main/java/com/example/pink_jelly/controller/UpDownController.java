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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@Log4j2
public class UpDownController {
    @Value("${com.example.mainBoardUpload.path}")
    private String mainBoardPath; //메인게시판 저장 경로

    //메인보드 file controller------------------------
    @ApiOperation(value = "MainBoard Upload Post", notes = "POST 방식으로 파일 등록")
    @PostMapping(value = "/mainBoardUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<UploadResultDTO> mainBoardUpload(UploadFileDTO uploadFileDTO) {
        log.info(uploadFileDTO);
        if(uploadFileDTO.getFiles() != null) {
            final List<UploadResultDTO> list = new ArrayList<>();
            for(MultipartFile multipartFile : uploadFileDTO.getFiles()) {
                String originalName = multipartFile.getOriginalFilename(); //파일 이름 저장
                log.info("originalName: " + originalName);

                String uuid = UUID.randomUUID().toString();

                Path savePath = Paths.get(mainBoardPath, uuid + "_" + originalName); //저장되는 이름
                boolean isImage = false;
                try{
                    multipartFile.transferTo(savePath); //실제 파일 저장

                    //이미지 파일이면 섬네일 생성 저장
                    if(Files.probeContentType(savePath).startsWith("image")) {
                        log.info("fiels.probeContentType: " + Files.probeContentType(savePath));
                        isImage = true;
                        File thumbFile = new File(mainBoardPath, "s_" + uuid + "_" + originalName);
                        Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 100, 400); //썸내일 생성
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                list.add(UploadResultDTO.builder()
                        .uuid(uuid)
                        .fileName(originalName)
                        .isImage(isImage)
                        .build());
            }
            return list;
        }
        return null;
    }

    @ApiOperation(value = "view 파일", notes = "GET방식으로 첨부파일 조회")
    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewMainBoardFileGet(@PathVariable String fileName){
        Resource resource = new FileSystemResource(mainBoardPath + File.separator + fileName);
        log.info(File.separator); // 경로 / 표시
        String resourceName = resource.getFilename();
        HttpHeaders headers = new HttpHeaders();
        try{
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @ApiOperation(value = "remove 파일", notes = "DELETE 방식으로 파일 삭제")
    @DeleteMapping("/mainBoardRemove/{fileName}")
    public Map<String, Boolean> removeMainBoardFile(@PathVariable String fileName) {
        Resource resource = new FileSystemResource(mainBoardPath + File.separator + fileName);
        String resourceName = resource.getFilename();


        Map<String, Boolean> resultMap = new HashMap<>();
        boolean removed = false;
        try{
            String contentType = Files.probeContentType(resource.getFile().toPath());
            removed = resource.getFile().delete(); //resource.delete 메서드로 삭제

            //썸네일이 있으면
            if(contentType.startsWith("image")){
                File thumbFile = new File(mainBoardPath + File.separator + "s_" + fileName);
                thumbFile.delete();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        resultMap.put("result", removed);
        return resultMap;
    }
}
