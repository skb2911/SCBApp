package com.company.ssDev.que9;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class AddingOptionBActivity extends AppCompatActivity {

    private final static int PICK_IMAGE_REQUEST = 3;
    private static int flag = 0;

    EditText editTextOption2;
    Button buttonChooseImageOption2, uploadImageOption2;
    TextView notificationOption2;
    ImageView showImageOption2;
    ProgressBar progressBar2;
    Switch switch2;

    Uri imageOption2Uri;

    Boolean optionBValue;

    StorageReference storageReference;
    DatabaseReference databaseReference;
    StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_option_b);

        final String professional = getIntent().getStringExtra("PROFESSIONAL");
        final String subject = getIntent().getStringExtra("SUBJECT");
        final String chapter = getIntent().getStringExtra("CHAPTER");
        //final String mode = getIntent().getStringExtra("MODE");
        final String set = getIntent().getStringExtra("SET");
        final String id = getIntent().getStringExtra("ID");

        editTextOption2 = (EditText) findViewById(R.id.editTextOption2);
        buttonChooseImageOption2 = (Button) findViewById(R.id.buttonChooseImageOption2);
        uploadImageOption2 = (Button) findViewById(R.id.uploadImageOption2);
        notificationOption2 = (TextView) findViewById(R.id.notificationOption2);
        showImageOption2 = (ImageView) findViewById(R.id.showImageOption2);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        switch2 = (Switch) findViewById(R.id.switch2);

        storageReference = FirebaseStorage.getInstance().getReference("Uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("App").child("Study");

        buttonChooseImageOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFileChooser();

            }
        });

        uploadImageOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ready = "true";
                if (editTextOption2.getText().toString().trim().equals("")){
                    editTextOption2.setError(getString(R.string.error_field_required));
                    ready="false";
                }

                if(ready.equals("true")){

                    databaseReference.child(professional)
                            .child(subject)
                            .child("MCQs")
                            .child("Questions")
                            .child(id)
                            .child("Option B")
                            .child("optionBText")
                            .setValue(editTextOption2.getText().toString());

                    optionBValue = switch2.isChecked();

                    databaseReference.child(professional)
                            .child(subject)
                            .child("MCQs")
                            .child("Questions")
                            .child(id)
                            .child("Option B")
                            .child("optionBValue")
                            .setValue(optionBValue);

                    if (imageOption2Uri != null){

                        if (uploadTask != null && uploadTask.isInProgress()) {
                            Toast.makeText(AddingOptionBActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                        }else {

                            StorageReference fileReference = storageReference
                                    .child(professional)
                                    .child(subject)
                                    .child("MCQs")
                                    .child("Questions")
                                    .child(id)
                                    .child("Option B")
                                    .child(System.currentTimeMillis() + "." + getFileExtension(imageOption2Uri));

                            uploadTask = fileReference.putFile(imageOption2Uri)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    progressBar2.setProgress(0);
                                                }
                                            }, 500);

                                            Toast.makeText(AddingOptionBActivity.this, "Upload successful", Toast.LENGTH_LONG).show();

                                            databaseReference.child(professional)
                                                    .child(subject)
                                                    .child("MCQs")
                                                    .child("Questions")
                                                    .child(id)
                                                    .child("Option B")
                                                    .child("optionBImageUrl");
                                                   // .setValue(taskSnapshot.getDownloadUrl().toString());

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            Toast.makeText(AddingOptionBActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    })
                                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                            progressBar2.setProgress((int) progress);

                                        }
                                    });
                        }
                    }
                    else {

                        databaseReference.child(professional)
                                .child(subject)
                                .child("MCQs")
                                .child("Questions")
                                .child(id)
                                .child("Option B")
                                .child("optionBImageUrl")
                                .setValue("No Image Selected");
                    }

                    Intent next = new Intent(AddingOptionBActivity.this, AddingOptionCActivity.class);
                    next.putExtra("PROFESSIONAL", professional);
                    next.putExtra("SUBJECT", subject);
                    next.putExtra("CHAPTER", chapter);
                    //next.putExtra("MODE",mode);
                    next.putExtra("SET",set);
                    next.putExtra("ID",id);
                    startActivity(next);
                }
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void openFileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select An Image"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){

            imageOption2Uri = data.getData();
            showImageOption2.setImageURI(imageOption2Uri);
            notificationOption2.setText("A file is selected : " + data.getData().getLastPathSegment());
        }

    }
}
