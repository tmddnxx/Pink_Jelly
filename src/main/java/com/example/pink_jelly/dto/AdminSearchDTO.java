package com.example.pink_jelly.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Arrays;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminSearchDTO {
    private String keyword; //검색내용
    private String[] types; //검색어
    private LocalDate from; //시작 날짜
    private LocalDate to; //끝 날짜

    public boolean checkType(String type) {
        if (this.types == null || this.types.length == 0) {
            return false;
        }
        return Arrays.asList(this.types).contains(type);
    }
}
