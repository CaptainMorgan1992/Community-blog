package com.communityblog.model;

import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.Data;
import org.apache.catalina.User;

import java.time.LocalDateTime;
import java.util.Date;

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
    private BlogUser author;


}
