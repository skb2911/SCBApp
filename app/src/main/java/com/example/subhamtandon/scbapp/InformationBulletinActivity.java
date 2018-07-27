package com.example.subhamtandon.scbapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class InformationBulletinActivity extends AppCompatActivity {

    RecyclerView recyclerViewInfos;
    EditText addInfoEditText;
    Button addInfo;

    String newInfo;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_bulletin);

        recyclerViewInfos = findViewById(R.id.recyclerViewInformationBulletinAdmin);

        progressBar = findViewById(R.id.progressBarForInfoList);

        addInfoEditText = findViewById(R.id.addInfoEditText);
        newInfo = addInfoEditText.getText().toString();

        addInfo = findViewById(R.id.addInfo);

        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("App").child("Information");
        databaseReference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot!=null) {
                    String info = dataSnapshot.child("InfoText").getValue(String.class);

                    ((AdapterForInfoList) recyclerViewInfos.getAdapter()).update(info);
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else
                    progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(InformationBulletinActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);

            }
        });

        recyclerViewInfos.setLayoutManager(new LinearLayoutManager(InformationBulletinActivity.this));
        AdapterForInfoList adapterForInfoList = new AdapterForInfoList(recyclerViewInfos, InformationBulletinActivity.this,new ArrayList<String>());
        recyclerViewInfos.setAdapter(adapterForInfoList);

        addInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Information");
                String infoKey = databaseReference.push().getKey();

                databaseReference.child(infoKey).child("InfoText").setValue(newInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            Toast.makeText(InformationBulletinActivity.this, "New Information Added", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(InformationBulletinActivity.this, "New Information not added", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


    }
}