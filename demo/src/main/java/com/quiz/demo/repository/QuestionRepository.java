package com.quiz.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quiz.demo.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
