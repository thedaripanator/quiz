package com.quiz.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.quiz.demo.model.Question;
import com.quiz.demo.repository.QuestionRepository;

@Service
public class QuizService {

    private final QuestionRepository repository;

    // Quiz runtime data
    private List<Question> questions;
    private List<Integer> answers;      // stores selected options
    private int currentIndex;
    private int score;

    // Track attempted questions
    @SuppressWarnings("FieldMayBeFinal")
    private Set<Integer> attemptedQuestions = new HashSet<>();

    public QuizService(QuestionRepository repository) {
        this.repository = repository;
    }

    /* ===================== ADMIN METHODS ===================== */

    public Question saveQuestion(Question question) {
        return repository.save(question);
    }

    public long getQuestionCount() {
        return repository.count();
    }

    public List<Question> getAllQuestions() {
        return repository.findAll();
    }

    public Question getQuestionById(int id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteQuestion(int id) {
        repository.deleteById(id);
    }

    /* ===================== QUIZ METHODS ===================== */

    public void startQuiz() {
        questions = repository.findAll();
        currentIndex = 0;
        score = 0;
        attemptedQuestions.clear();

        // initialize answers list
        answers = new ArrayList<>();
        for (@SuppressWarnings("unused") Question question : questions) {
            answers.add(null);
        }
    }

    public Question getCurrentQuestion() {
        return questions.get(currentIndex);
    }

    public void submitAnswer(int option) {
        attemptedQuestions.add(currentIndex);
        answers.set(currentIndex, option);

        if (option == questions.get(currentIndex).getCorrectAnswer()) {
            score++;
        }
    }

    public boolean hasNext() {
        return currentIndex < questions.size() - 1;
    }

    public void goToNext() {
        if (hasNext()) {
            currentIndex++;
        }
    }

    public boolean hasPrevious() {
        return currentIndex > 0;
    }

    public void goToPrevious() {
        if (hasPrevious()) {
            currentIndex--;
        }
    }

    public void goToQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            currentIndex = index;
        }
    }

    /* ===================== GETTERS ===================== */

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getTotalQuestions() {
        return questions.size();
    }

    public int getScore() {
        return score;
    }

    public Set<Integer> getAttemptedQuestions() {
        return attemptedQuestions;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public List<Integer> getAnswers() {
        return answers;
    }

}
