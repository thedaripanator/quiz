package com.quiz.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quiz.demo.service.QuizService;

@Controller
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    // start quiz
    @GetMapping("/")
    public String startQuiz(Model model) {
        quizService.startQuiz();
        model.addAttribute("question", quizService.getCurrentQuestion());
        return "quiz";
    }

    // submit answer
    @PostMapping("/submit")
    public String submitAnswer(@RequestParam int option, Model model) {

        quizService.submitAnswer(option);

        if (quizService.hasNext()) {
            model.addAttribute("question", quizService.getCurrentQuestion());
            return "quiz";
        } else {
            model.addAttribute("score", quizService.getScore());
            model.addAttribute("total", quizService.getTotalQuestions());
            return "result";
        }
    }
}
