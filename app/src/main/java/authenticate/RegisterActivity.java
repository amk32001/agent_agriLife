package authenticate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.agent_agrilife.MainActivity;
import com.example.agent_agrilife.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity
{
    //variables related to firebase.


    public static String passwordAuth;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    Button register;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    TextInputLayout full_name,email,password,confirm_pass,aadhar;//security_que;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        register=findViewById(R.id.register1);
        full_name=findViewById(R.id.full_name);
        email=findViewById(R.id.email_reg);
        password=findViewById(R.id.password_reg);
        confirm_pass=findViewById(R.id.password_reg1);
        aadhar=findViewById(R.id.adhar_card_no);
       // security_que=findViewById(R.id.secure_que);


        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        //Setting DOB
        dateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                datePickerDialog.show();
            }
        });

        //Register button click

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String fullNameStr = Objects.requireNonNull(full_name.getEditText()).getText().toString().trim();
                    String emailStr = Objects.requireNonNull(email.getEditText()).getText().toString().trim();
                    String passStr = Objects.requireNonNull(password.getEditText()).getText().toString().trim();
                    String confirmPassStr = Objects.requireNonNull(confirm_pass.getEditText()).getText().toString().trim();
                    String aadharStr = Objects.requireNonNull(aadhar.getEditText()).getText().toString().trim();
                    String date = dateButton.getText().toString().trim();
                    //String secure_que= Objects.requireNonNull(security_que.getEditText()).getText().toString().trim();

                   if(!checkForValidity(fullNameStr, emailStr, passStr, confirmPassStr, aadharStr))//this func checks for validity of each field.
                   {

                   }
                   else {

                       //to check user already exist or not.
                       firebaseAuth.fetchSignInMethodsForEmail(emailStr).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                           @Override
                           public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                               boolean check= task.getResult().getSignInMethods().isEmpty();
                               if(!check)
                               {
                                   email.setError("This email already exist!! please type other email");
                               }
                               else
                               {
                                   //passwordAuth=passStr;
                                   firebaseAuth.createUserWithEmailAndPassword(emailStr, passStr)
                                           .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                               @Override
                                               public void onComplete(@NonNull Task<AuthResult> task) {
                                                   try {
                                                       AgentDetails agentDetails = new AgentDetails(fullNameStr, emailStr, aadharStr,date,passStr);
                                                       firebaseFirestore.collection("AgentDetails").document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).set(agentDetails).
                                                       addOnCompleteListener(new OnCompleteListener<Void>() {
                                                           @Override
                                                           public void onComplete(@NonNull Task<Void> task) {
                                                               Toast.makeText(RegisterActivity.this, "Successfully registered on app", Toast.LENGTH_SHORT).show();
                                                               startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                                           }
                                                       });
                                                   }
                                                   catch(Exception e)
                                                   {
                                                       Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                   }
                                               }
                                           });


                               }
                           }
                       });
                   }
                }
            });


    }

    private boolean checkForValidity(String fullNameStr,String emailStr,String passStr,String confirmPassStr,String aadharStr)
    {
        if(fullNameStr.isEmpty())
        {
            full_name.setError("Please fill this field");
            //full_name.requestFocus();
            return false;
        }

        if(emailStr.isEmpty())
        {
            email.setError("Please fill this field");
            //email.requestFocus();
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches())
        {
            email.setError("It is invalid format");
            //email.requestFocus();
            return false;
        }

        if(passStr.isEmpty())
        {
            password.setError("Please fill this field");
            //password.requestFocus();
            return false;
        }

        if(confirmPassStr.isEmpty())
        {
            confirm_pass.setError("Please fill this field");
            //confirm_pass.requestFocus();
            return false;
        }

        if(!passStr.equals(confirmPassStr))
        {
            confirm_pass.setError("Not matching with above password");
           // confirm_pass.requestFocus();
            return false;
        }


        return true;
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());



    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }
}