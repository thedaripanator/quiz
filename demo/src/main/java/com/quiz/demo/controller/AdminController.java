package com.quiz.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quiz.demo.model.Question;
import com.quiz.demo.service.QuizService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final QuizService quizService;

    public AdminController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute("question", new Question());
        model.addAttribute("questionCount", quizService.getQuestionCount());
        return "admin";
    }

    @PostMapping("/add")
    public String addQuestion(@ModelAttribute("question") Question question) {
        quizService.saveQuestion(question);
        return "redirect:/admin";
    }
}
