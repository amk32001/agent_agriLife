package com.example.agent_agrilife;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ApproveRejectClaim extends AppCompatActivity {
    Button approve, put_on_hold;
    ImageView damaged_crop , sowing_certificate;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.approve_reject_claim);
        init();

    }
    private void init(){
        approve = findViewById(R.id.approve);
        put_on_hold = findViewById(R.id.put_on_hold);
        damaged_crop = findViewById(R.id.damage_pic);
        sowing_certificate = findViewById(R.id.sowing_certificate);

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean authentic_agen = verifyAgent();
            }
        });

        put_on_hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean authentic_agen = verifyAgent();

            }
        });

    }

    boolean verifyAgent() {
        final boolean[] agent_verified = {false};
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.agent_verification_prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                String pass_code = userInput.getText().toString();
                                agent_verified[0] = true;
                                // if passcode is same as password / unique code, he is allwed to put request
                                Toast.makeText(context, pass_code, Toast.LENGTH_SHORT).show();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                agent_verified[0] = false;
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
        return agent_verified[0];
    }

}