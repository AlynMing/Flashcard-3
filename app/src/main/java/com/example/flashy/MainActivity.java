package com.example.flashy;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                }
            });

            findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((TextView) findViewById((R.id.flashcard_answer))).setVisibility(View.GONE);
                    ((TextView) findViewById((R.id.flashcard_question))).setVisibility(View.VISIBLE);
                }
            });



    }
}
