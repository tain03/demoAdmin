package com.example.demo.models;

import com.example.demo.service.QuizService;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String category;
    private Integer numQ;

    @ManyToMany
    private List<Question> questions;
}