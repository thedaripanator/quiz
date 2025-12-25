package com.quiz.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.quiz.demo.model.Question;
import com.quiz.demo.repository.QuestionRepository;

@Service
public class QuizService {

    private final QuestionRepository repository;

    // USER quiz state
    private List<Question> questions;
    private int currentIndex;
    private int score;

    public QuizService(QuestionRepository repository) {
        this.repository = repository;
    }

    // ================= ADMIN =================

    public Question saveQuestion(Question question) {
        return repository.save(question);
    }

    // ================= USER =================

    // start quiz
    public void startQuiz() {
        questions = repository.findAll();
        currentIndex = 0;
        score = 0;
    }

    // get current question
    public Question getCurrentQuestion() {
        return questions.get(currentIndex);
    }

    // submit answer
    public void submitAnswer(int selectedOption) {
        Question q = questions.get(currentIndex);
        if (q.getCorrectAnswer() == selectedOption) {
            score++;
        }
        currentIndex++;
    }

    public boolean hasNext() {
        return currentIndex < questions.size();
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return questions.size();
    }
}
