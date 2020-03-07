package com.example.flashy;

import android.content.Intent;
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

            findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                    MainActivity.this.startActivityForResult(intent, 100);


                }
            });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            String question = data.getExtras().getString("question"); // 'string1' needs to match the key we used when we put the string in the Intent
            String answer = data.getExtras().getString("answer");

            ((TextView) findViewById(R.id.flashcard_question)).setText(question);

            ((TextView) findViewById(R.id.flashcard_answer)).setText(answer);

        }
    }

}
