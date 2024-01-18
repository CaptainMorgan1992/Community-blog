package com.communityblog.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
public class Blogpost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime date;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "author_id")
    private User author;

    public Blogpost() {
        this.date = LocalDateTime.now();
    }

}
