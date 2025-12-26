package com.quiz.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.quiz.demo.service.QuizService;

@Controller
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    /* ===================== START ===================== */

    @GetMapping("/start")
    public String startPage() {
        return "start";
    }

    @GetMapping("/")
    public String startQuiz(Model model) {
        quizService.startQuiz();
        addQuizAttributes(model);
        return "quiz";
    }

    /* ===================== SUBMIT / NEXT ===================== */

    @PostMapping("/submit")
    public String submit(@RequestParam int option, Model model) {

        quizService.submitAnswer(option);

        if (quizService.hasNext()) {
            quizService.goToNext();
            addQuizAttributes(model);
            return "quiz";
        }

        model.addAttribute("score", quizService.getScore());
        model.addAttribute("total", quizService.getTotalQuestions());
        return "result";
    }

    /* ===================== NAVIGATION ===================== */

    @GetMapping("/question/{index}")
    public String goToQuestion(@PathVariable int index, Model model) {
        quizService.goToQuestion(index);
        addQuizAttributes(model);
        return "quiz";
    }

    @GetMapping("/previous")
    public String previousQuestion(Model model) {
        quizService.goToPrevious();
        addQuizAttributes(model);
        return "quiz";
    }

    /* ===================== REVIEW PAGE ===================== */

    @GetMapping("/result-review")
    public String review(Model model) {
        model.addAttribute("questions", quizService.getQuestions());
        model.addAttribute("answers", quizService.getAnswers());
        return "result-review";
    }

    /* ===================== SHARED MODEL ===================== */

    private void addQuizAttributes(Model model) {
        model.addAttribute("question", quizService.getCurrentQuestion());
        model.addAttribute("current", quizService.getCurrentIndex() + 1);
        model.addAttribute("total", quizService.getTotalQuestions());
        model.addAttribute("attempted", quizService.getAttemptedQuestions());
    }
}
