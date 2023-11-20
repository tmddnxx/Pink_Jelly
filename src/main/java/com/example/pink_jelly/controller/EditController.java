package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.upload.EditorUploadDTO;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Controller
@Log4j2
@RequestMapping("/editor")
public class EditController {

    @Value("${com.example.tempUpload.path}")
    private String tempPath;

    @PostMapping("/tempUpload")
    public String upload(EditorUploadDTO editUploadDTO)throws UnsupportedEncodingException {
        log.info("upload");
        log.info(editUploadDTO);

        String callback = editUploadDTO.getCallback();
        String callback_func = editUploadDTO.getCallback_func();
        String fileResult = "";
        String result = "";
        MultipartFile multipartFile = editUploadDTO.getFiledata();

        try{
            String originalName = multipartFile.getOriginalFilename();
            log.info(multipartFile.getOriginalFilename());

            String uuid = UUID.randomUUID().toString();
            String newName = uuid + "_" + originalName;

            LocalDate currentDate =  LocalDate.now(); // 오늘 날짜 가져오기
            // 날짜 포맷 시점
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateString = currentDate.format(formatter);

            String path = tempPath +"/" + dateString;
            File file = new File(path);
            if(!file.exists()){ // 폴더가 존재하지 않으면
                file.mkdirs(); // 폴더 생성
            }

            Path savePath = Paths.get(path, newName);
            multipartFile.transferTo(savePath); // 실제 파일 저장
            String contentType = Files.probeContentType(savePath);
            if(contentType.startsWith("image") && contentType != null) {
                File thumbFile = new File(path, "s_" + newName); //썸네일 생성
                Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200, 100);
            }

            originalName = URLEncoder.encode(originalName, StandardCharsets.UTF_8);
            newName = URLEncoder.encode(newName, StandardCharsets.UTF_8);
            fileResult += "&bNewLine=true&sFileName=" + originalName +
                            "&sFileURL=/editor/view/" + dateString + "/" + newName;
        } catch (Exception e){
            e.printStackTrace();
        }
        result = "redirect:" + callback + "?callback_func=" + URLEncoder.encode(callback_func, StandardCharsets.UTF_8) + fileResult;

        return result;
    }

    @GetMapping("/view/{dateFolder}/{fileName}")
    public ResponseEntity<Resource> viewFile(@PathVariable String dateFolder, @PathVariable String fileName){
        log.info(fileName);
        fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
        Resource resource = new FileSystemResource(tempPath + File.separator + dateFolder + File.separator + fileName);

        HttpHeaders headers = new HttpHeaders();

        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch (IOException e){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }

}
