package com.example.subhamtandon.scbapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetsAlteringActivity extends AppCompatActivity {

    TextInputEditText setEditText;

    EditText addQuestionEditText, addOptionAEditText, addOptionBEditText, addOptionCEditText, addOptionDEditText, addExplanationEditText;
    Spinner correctOptionSpinner;
    Button submitQuestionButton, submitSetButton;

    DatabaseReference databaseReference, databaseReferenceRandom;

    CardView cardViewAddQuestion;
    String whichSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets_altering);

        final String professional = getIntent().getStringExtra("PROFESSIONAL");
        final String subject = getIntent().getStringExtra("SUBJECT");
        final String chapter = getIntent().getStringExtra("CHAPTER");

        Toast.makeText(this, professional + " : " + subject + " : " + chapter, Toast.LENGTH_SHORT).show();

        setEditText = (TextInputEditText) findViewById(R.id.setEditText);
        submitSetButton = (Button) findViewById(R.id.submitSetButton);
        //databaseReference = FirebaseDatabase.getInstance().getReference("App").child("Study").child(professional).child(subject).child("MCQs");

        addQuestionEditText = findViewById(R.id.addQuestionEditText);
        addOptionAEditText = findViewById(R.id.addOptionAEditText);
        addOptionBEditText = findViewById(R.id.addOptionBEditText);
        addOptionCEditText = findViewById(R.id.addOptionCEditText);
        addOptionDEditText = findViewById(R.id.addOptionDEditText);
        addExplanationEditText = findViewById(R.id.addExplanationEditText);
        correctOptionSpinner = findViewById(R.id.correctOptionSpinner);
        submitQuestionButton = findViewById(R.id.submitQuestionButton);
        cardViewAddQuestion = findViewById(R.id.cardViewAddQuestion);

        databaseReference = FirebaseDatabase.getInstance().getReference("App").child("Study");
        databaseReferenceRandom = FirebaseDatabase.getInstance().getReference("App").child("Study").child("Random");

        submitSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichSet = setEditText.getText().toString();

                String ready = "true";

                if (TextUtils.isEmpty(whichSet)){
                    setEditText.setError(getString(R.string.error_field_required));
                    ready = "false";
                }

                if(ready.equalsIgnoreCase("true")){
                    cardViewAddQuestion.setVisibility(View.VISIBLE);

                    /*databaseReference.child("Chapters")
                            .child(chapter)
                            .child("Sets")
                            .child(whichSet)
                            .setValue(null);

                    Intent done = new Intent(SetsAlteringActivity.this, NewQuestionActivity.class);
                    //done.putExtra("TYPE","MCQs");
                    done.putExtra("PROFESSIONAL",professional);
                    done.putExtra("SUBJECT",subject);
                    done.putExtra("CHAPTER", chapter);
                    //done.putExtra("MODE",mode);
                    done.putExtra("SET",whichSet);
                    startActivity(done);*/
                }

            }
        });

        submitQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = addQuestionEditText.getText().toString();
                String optionA = addOptionAEditText.getText().toString();
                Boolean optionAValue = false;
                String optionB = addOptionBEditText.getText().toString();
                Boolean optionBValue = false;
                String optionC = addOptionCEditText.getText().toString();
                Boolean optionCValue = false;
                String optionD = addOptionDEditText.getText().toString();
                Boolean optionDValue = false;
                String correctAnswer = correctOptionSpinner.getSelectedItem().toString();
                String explanation = addExplanationEditText.getText().toString();

                String ready = "true";
                if (TextUtils.isEmpty(question)){
                    addQuestionEditText.setError(getString(R.string.error_field_required));
                    ready = "false";
                }
                if (TextUtils.isEmpty(optionA)){
                    addOptionAEditText.setError(getString(R.string.error_field_required));
                    ready = "false";
                }
                if (TextUtils.isEmpty(optionB)){
                    addOptionBEditText.setError(getString(R.string.error_field_required));
                    ready = "false";
                }
                if (TextUtils.isEmpty(optionC)){
                    addOptionCEditText.setError(getString(R.string.error_field_required));
                    ready = "false";
                }
                if (TextUtils.isEmpty(optionD)){
                    addOptionDEditText.setError(getString(R.string.error_field_required));
                    ready = "false";
                }
                if (correctAnswer.equalsIgnoreCase("Choose Correct Option")){
                    Toast.makeText(SetsAlteringActivity.this, "Choose correct option from the Dropdown", Toast.LENGTH_SHORT).show();
                    ready = "false";
                }
//                if (TextUtils.isEmpty(explanation)){
//                    addExplanationEditText.setError(getString(R.string.error_field_required));
//                    ready = "false";
//                }

                if (ready.equalsIgnoreCase("true")){

                    if (correctAnswer.equals("Option A")){
                        optionAValue = true;
                        optionBValue = false;
                        optionCValue = false;
                        optionDValue = false;
                    }

                    if (correctAnswer.equals("Option B")){
                        optionAValue = false;
                        optionBValue = true;
                        optionCValue = false;
                        optionDValue = false;
                    }

                    if (correctAnswer.equals("Option C")){
                        optionAValue = false;
                        optionBValue = false;
                        optionCValue = true;
                        optionDValue = false;
                    }

                    if (correctAnswer.equals("Option D")){
                        optionAValue = false;
                        optionBValue = false;
                        optionCValue = false;
                        optionDValue = true;
                    }

                    if(explanation.equalsIgnoreCase("")){
                        explanation = "null";
                    }

                    Explanation explanation1 = new Explanation(explanation);
                    OptionA optionA1 = new OptionA(optionA, optionAValue);
                    OptionB optionB1 = new OptionB(optionB, optionBValue);
                    OptionC optionC1 = new OptionC(optionC, optionCValue);
                    OptionD optionD1 = new OptionD(optionD, optionDValue);
                    Question question1 = new Question(question);

                    NewQuestion newQuestion = new NewQuestion(chapter, explanation1, optionA1, optionB1, optionC1, optionD1, question1, whichSet);

                    String id = databaseReference.push().getKey();

                    databaseReference.child(professional)
                            .child(subject)
                            .child("MCQs")
                            .child("Questions")
                            .child(id)
                            .setValue(newQuestion).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });

                    databaseReference.child(professional)
                            .child(subject)
                            .child("MCQs")
                            .child("Chapters")
                            .child(chapter)
                            .child("Sets")
                            .child(whichSet)
                            .child(id)
                            .setValue(question1.getQuestionText());

                    databaseReferenceRandom.child(professional)
                            .child("Questions")
                            .child(id)
                            .setValue(subject);

                    databaseReferenceRandom.child(professional)
                            .child(subject)
                            .child("Questions")
                            .child(id)
                            .setValue(chapter);

                    Toast.makeText(SetsAlteringActivity.this, question+optionA+optionB+optionC+optionD+correctAnswer+explanation, Toast.LENGTH_SHORT).show();
                    Toast.makeText(SetsAlteringActivity.this, "Addition of new question done", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
        });
    }

}
