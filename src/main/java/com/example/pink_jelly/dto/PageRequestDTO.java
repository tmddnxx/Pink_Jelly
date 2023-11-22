package com.example.pink_jelly.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size = 10;

    private String type;
    private String keyword;
    private String link;

    public int getSkip() { return (page-1) * size;}

    public String getLink(){
        if (link == null){
            StringBuilder builder = new StringBuilder();
            builder.append("page=" + this.page);
            builder.append("&size=" + this.size);
            if(this.type != null ){
                builder.append("&type=" + type);
            }
            if(this.keyword != null){
                try{
                    builder.append("&keyword=" + URLEncoder.encode(keyword, "utf-8"));
                } catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
            }
            return builder.toString();
        }
        return link;
    }
}
