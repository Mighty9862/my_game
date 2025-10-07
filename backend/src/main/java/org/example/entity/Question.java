package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "questions")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private String questionText;

    @Column(nullable = false)
    private String answerText;

    @Column(nullable = false)
    private int points;

    @Column(nullable = false)
    private boolean answered = false;
}