package com.example.pink_jelly.controller;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootTest
@Log4j2
class MemberControllerTest {
    @Value("${com.example.tempUpload.path}")
    private String tempPath;

    @Value("${com.example.profileUpload.path}")
    private String profilePath;

    @Test
    public void checkFileTest() {
        /* tempPath 폴더의 파일을 불러옴 */
        File folder = new File(tempPath + "/2023-11-16"); // 폴더 객체 생성
        File[] imageList = folder.listFiles(); // 폴더 내의 파일 목록 가져오기
        log.info("testzz");

        // 파일 출력
        if (imageList != null) {
            for (File file : imageList) {
                log.info(file.getName());
            }
        }
    }
    
    @Test
    public void copyFileTest() {
        /* 파일 복사 */
        // 서버에서 파일 입출력의 가장 큰 특징은 읽기와 쓰기가 다른 클래스를 사용.
        // 스트림이라는 용어는 사용하는데 강물처럼 파일이 한방향으로 으로만 흐름

        String imgName = "92a86e98-333c-4b29-bbfb-c92cd4691e0a_뿌링요거.jpg"; // 임시 폴더에 존재하는 파일이름.
        String dateFolder = "2023-11-16";

        try {
            // 읽을 파일과 쓰기 파일을 구분해서 객체 생성.
            FileInputStream fileInputStream = new FileInputStream(tempPath + File.separator + dateFolder + File.separator + imgName);
            FileOutputStream fileOutputStream = new FileOutputStream(profilePath + File.separator + dateFolder + File.separator + imgName);

            // 파일 복사의 경우 buffer를 사용해야 속도가 빠름.
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            
            int i;
            // 파일은 한번에 읽지 않고, 일정 크기 만큼만 읽음. read() 메서드는 읽은 크기를 반환하는데 파일의 끝에 오면 -1을 반환
            while ((i = bufferedInputStream.read()) != -1) { // 파일 끝까지 읽기
                bufferedOutputStream.write(i); // 읽은 만큼 쓰기
            }
            bufferedInputStream.close();
            bufferedOutputStream.close();
            fileInputStream.close();
            fileOutputStream.close();

            Files.delete(Paths.get(tempPath + File.separator + dateFolder + File.separator + imgName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}