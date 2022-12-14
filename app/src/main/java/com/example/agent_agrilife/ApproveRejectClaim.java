package com.example.agent_agrilife;


import authenticate.RegisterActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ApproveRejectClaim extends AppCompatActivity {
    Button approve, reject;

    ImageView damaged_crop , sowing_certificate;
    final Context context = this;
    String pass_word="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.approve_reject_claim);
        DocumentReference document=FirebaseFirestore.getInstance().collection("AgentDetails").document(FirebaseAuth.getInstance().getUid());
        document.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                pass_word=value.getString("password");
                //Toast.makeText(context, pass_word, Toast.LENGTH_SHORT).show();
            }
        });
        init();

    }
    private void init(){
        approve = findViewById(R.id.approve);
        reject = findViewById(R.id.reject);
        damaged_crop = findViewById(R.id.damage_pic);
        sowing_certificate = findViewById(R.id.sowing_certificate);

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyAgentApprove();
                //Toast.makeText(context, authentic_agen + "", Toast.LENGTH_SHORT).show();
//                if(authentic_agen)
//                {
//                    approve.setVisibility(View.GONE);
//                    reject.setVisibility(View.GONE);
//                }

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