package com.communityblog.model;

import jakarta.persistence.*;

import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
@Table
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;


}
