package com.communityblog.dto;

import com.communityblog.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BlogpostDto {
    private Long id;
    private String title;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String author;

    public BlogpostDto() {
        this.date = LocalDate.now();
    }

    public BlogpostDto(String title, String content, LocalDate date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }


}
