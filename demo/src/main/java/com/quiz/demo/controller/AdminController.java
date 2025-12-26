package com.quiz.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("questions", quizService.getAllQuestions());
        model.addAttribute("questionCount", quizService.getQuestionCount());
        return "admin";
    }

    @PostMapping("/save")
    public String saveQuestion(@ModelAttribute("question") Question question) {
        quizService.saveQuestion(question);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String editQuestion(@PathVariable int id, Model model) {
        model.addAttribute("question", quizService.getQuestionById(id));
        model.addAttribute("questions", quizService.getAllQuestions());
        model.addAttribute("questionCount", quizService.getQuestionCount());
        return "admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable int id) {
        quizService.deleteQuestion(id);
        return "redirect:/admin";
    }
}
