package com.example.flashy;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FlashcardDatabase flashcardDatabase;

    List<Flashcard> allFlashcards;

    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
        }

//        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
//            @Override
////            public void onClick(View view) {
////                ((TextView) MainActivity.this.findViewById(R.id.flashcard_question)).setVisibility(view.GONE);
////                ((TextView) MainActivity.this.findViewById(R.id.flashcard_answer)).setVisibility(view.VISIBLE);
////            }

        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView) findViewById((R.id.flashcard_question))).setVisibility(View.GONE);
                ((TextView) findViewById((R.id.flashcard_answer))).setVisibility(View.VISIBLE);

                View answerSideView = findViewById(R.id.flashcard_answer);
                View questionSideView = findViewById(R.id.flashcard_question);

                // get the center for the clipping circle
                int cx = answerSideView.getWidth() / 2;
                int cy = answerSideView.getHeight() / 2;

                // get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);

                // create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(answerSideView, cx, cy, 0f, finalRadius);

                // hide the question and show the answer to prepare for playing the animation!
                questionSideView.setVisibility(View.INVISIBLE);
                answerSideView.setVisibility(View.VISIBLE);

                anim.setDuration(3000);
                anim.start();
            }
        });

        findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView) findViewById((R.id.flashcard_answer))).setVisibility(View.GONE);
                ((TextView) findViewById((R.id.flashcard_question))).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);

                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // advance our pointer index so we can show the next card
                currentCardDisplayedIndex++;

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }

                // set the question and answer TextViews with data from the database
                ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());

                final Animation leftOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.right_in);

                findViewById(R.id.flashcard_question).startAnimation(leftOutAnim);

                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        findViewById(R.id.flashcard_question).startAnimation(rightInAnim);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && data != null) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            String question = data.getExtras().getString("question"); // 'string1' needs to match the key we used when we put the string in the Intent
            String answer = data.getExtras().getString("answer");

            ((TextView) findViewById(R.id.flashcard_question)).setText(question);

            ((TextView) findViewById(R.id.flashcard_answer)).setText(answer);

            flashcardDatabase.insertCard(new Flashcard(question, answer));

            allFlashcards = flashcardDatabase.getAllCards();
        }
    }

}
