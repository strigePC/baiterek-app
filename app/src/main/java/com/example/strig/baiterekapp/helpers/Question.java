package com.example.strig.baiterekapp.helpers;

import java.util.List;

public class Question {
    private String question;
    private List<String> answers;

    public Question(String question, List<String> answers) {
        setQuestion(question);
        setAnswers(answers);
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
