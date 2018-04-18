package com.example.strig.baiterekapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    int progress = 0;
    int userAnswer;
    int userScore = 0;

    private List<Question> questions;
    private List<String> correctAnswers;

    TextView questionTitle;
    TextView question;
    List<Button> answerButtons;
    Button answerButton1;
    Button answerButton2;
    Button answerButton3;

    EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questions = new ArrayList<>();
        correctAnswers = Arrays.asList(getResources().getStringArray(R.array.quiz_correct_answers));

        List<String> answers1 = Arrays.asList(getResources().getStringArray(R.array.question_1_answers));
        List<String> answers2 = Arrays.asList(getResources().getStringArray(R.array.question_2_answers));
        List<String> answers3 = Arrays.asList(getResources().getStringArray(R.array.question_3_answers));
        List<String> answers4 = Arrays.asList(getResources().getStringArray(R.array.question_4_answers));
        List<String> answers5 = Arrays.asList(getResources().getStringArray(R.array.question_5_answers));

        questions.add(new Question(getResources().getString(R.string.question_1), answers1));
        questions.add(new Question(getResources().getString(R.string.question_2), answers2));
        questions.add(new Question(getResources().getString(R.string.question_3), answers3));
        questions.add(new Question(getResources().getString(R.string.question_4), answers4));
        questions.add(new Question(getResources().getString(R.string.question_5), answers5));

        questionTitle = findViewById(R.id.question_title);
        question = findViewById(R.id.question);
        username = findViewById(R.id.username);
        answerButtons = new ArrayList<>();
        answerButton1 = findViewById(R.id.answer_1);
        answerButton2 = findViewById(R.id.answer_2);
        answerButton3 = findViewById(R.id.answer_3);
        answerButtons.add(answerButton1);
        answerButtons.add(answerButton2);
        answerButtons.add(answerButton3);

        reloadAnswers();
    }


    public void reloadAnswers() {
        final int correctAnswer = Integer.valueOf(correctAnswers.get(progress));
        Question q = questions.get(progress);
        questionTitle.setText(String.format(getResources().getString(R.string.question_title), progress + 1));
        question.setText(q.getQuestion());
        for (int i = 0; i < 3; i++) {
            answerButtons.get(i).setText(q.getAnswers().get(i));
            answerButtons.get(i).setBackground(getResources().getDrawable(R.drawable.button_grey));
        }

        for (int i = 0; i < 3; i++) {
            final int finalI = i;
            answerButtons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    answerButtons.get(correctAnswer)
                            .setBackground(getResources().getDrawable(R.drawable.button_green));
                    userAnswer = finalI;
                    if (userAnswer == correctAnswer) {
                        userScore++;
                    } else {
                        answerButtons.get(userAnswer)
                                .setBackground(getResources().getDrawable(R.drawable.button_red));
                    }

                    for (Button answerButton : answerButtons) {
                        answerButton.setOnClickListener(null);
                    }

                    new CountDownTimer(2000, 1000) {
                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            progress++;
                            if (progress < questions.size()) {
                                reloadAnswers();
                            } else {
                                questionTitle.setText(getResources().getString(R.string.quiz_completed));
                                question.setText(String.format(getResources().getString(R.string.score_text), userScore, questions.size()));
                                for (Button answerButton : answerButtons) {
                                    answerButton.setVisibility(View.GONE);
                                }
                                username.setVisibility(View.VISIBLE);
                                answerButtons.get(1).setVisibility(View.VISIBLE);
                                answerButtons.get(1).setText(R.string.back_to_menu);
                                answerButtons.get(1).setBackground(getResources().getDrawable(R.drawable.button_green));
                                answerButtons.get(1).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (!username.getText().toString().equals("")) {
                                            Intent i = new Intent(QuizActivity.this, DescriptionActivity.class);
                                            startActivity(i);
                                            Toast.makeText(QuizActivity.this, "Your result was saved", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    }.start();
                }
            });
        }
    }
}
