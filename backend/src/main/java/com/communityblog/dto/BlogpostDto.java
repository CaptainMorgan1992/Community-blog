package com.communityblog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BlogpostDto {

    private Long id;

    private String title;

    @Size(max = 255, message = "Too many characters. Maximum allowed: 255")
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String author;

    public BlogpostDto() {
        this.date = LocalDate.now();
    }
}
