package com.example.agent_agrilife;


import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ApproveRejectClaim extends AppCompatActivity {
    Button approve, reject;

    Context context = this;
    String pass_word="";


    // Initialize Firebase Storage reference
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.approve_reject_claim);
        init();
        //Toast.makeText(context, farmerId, Toast.LENGTH_SHORT).show();
        DocumentReference document=FirebaseFirestore.getInstance().collection("AgentDetails").document(FirebaseAuth.getInstance().getUid());
        document.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                assert value != null;
                pass_word=value.getString("password");
                //Toast.makeText(context, pass_word, Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void init(){

        approve=(Button) findViewById(R.id.approve);
        reject=(Button) findViewById(R.id.reject);

 //Create an array of image file paths
        String[] imagePaths ={"file1", "file2", "file3", "file4"};

        // Assuming you have four ImageViews in your layout with IDs: imageView1, imageView2, imageView3, imageView4
        ImageView[] imageViews = {
                (ImageView) findViewById(R.id.imageView1),
                (ImageView) findViewById(R.id.imageView2),
                (ImageView) findViewById(R.id.imageView3),
                (ImageView) findViewById(R.id.imageView4)
        };
       // Toast.makeText(context, farmerId, Toast.LENGTH_SHORT).show();
        Bundle bundle = getIntent().getExtras();
        String farmerId = bundle.getString("farmId");
        for (int i = 0; i < imagePaths.length; i++) {
            // Create a reference to the image file

            StorageReference imageRef = firebaseStorage.getReference(farmerId).child(imagePaths[i]);

            GlideApp.with(this)
                    .load(imageRef)
                    .into(imageViews[i]);
        }


        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyAgentApprove();

            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyAgentReject();
            }
        });

    }

    //reject claim
    void verifyAgentReject()
    {

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.reject_prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.password_prompt);
        EditText reason = (EditText) promptsView
                .findViewById(R.id.reason);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                String pass_code = userInput.getText().toString();

                                if(pass_code.equals(pass_word)) {
                                    Toast.makeText(getApplicationContext(),"Authentication Successful", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Authentication failed!!", Toast.LENGTH_SHORT).show();
                                }
                                // if passcode is same as password / unique code, he is allwed to put request
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
//        return agent_verified[0];
    }


    //for approval
    void verifyAgentApprove() {

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.agent_verification_prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.password_prompt);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                String pass_code = userInput.getText().toString();
                               // Toast.makeText(context, pass_code, Toast.LENGTH_SHORT).show();

                                if(pass_code.equals(pass_word)) {

                                    Toast.makeText(getApplicationContext(),"Authentication Successful", Toast.LENGTH_SHORT).show();

                                }
                                else
                                {

                                    Toast.makeText(getApplicationContext(),"Authentication failed!!!", Toast.LENGTH_SHORT).show();
                                }
                                // if passcode is same as password / unique code, he is allwed to put request
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}