package com.quiz.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quiz.demo.service.QuizService;

@Controller
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    // START PAGE
    @GetMapping("/start")
    public String startPage() {
        return "start";
    }

    // ACTUAL QUIZ STARTS HERE
    @GetMapping("/")
    public String startQuiz(Model model) {

        quizService.startQuiz();
        addQuizAttributes(model);

        return "quiz";
    }

    @PostMapping("/submit")
    public String submit(@RequestParam int option, Model model) {

        quizService.submitAnswer(option);

        if (quizService.hasNext()) {
            addQuizAttributes(model);
            return "quiz";
        }

        model.addAttribute("score", quizService.getScore());
        model.addAttribute("total", quizService.getTotalQuestions());
        return "result";
    }

    private void addQuizAttributes(Model model) {
        model.addAttribute("question", quizService.getCurrentQuestion());
        model.addAttribute("current", quizService.getCurrentIndex() + 1);
        model.addAttribute("total", quizService.getTotalQuestions());
    }
    @GetMapping("/question/{index}")
public String goToQuestion(@PathVariable int index, Model model) {

    quizService.goToQuestion(index);
    addQuizAttributes(model);

    return "quiz";
}

}
