package authenticate;
import androidx.annotation.NonNull;
import  androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import  android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.agent_agrilife.MainActivity;
import  com.example.agent_agrilife.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    ImageView imageView;

    FirebaseAuth firebaseAuth;
    long pressedTime;

    Button signin,register;

    TextInputLayout email, password;

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseAuth firebaseAuth;
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

          super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);



        imageView=findViewById(R.id.profile_pic);
        signin=findViewById(R.id.signin);
        register=findViewById(R.id.register);
        email=findViewById(R.id.farmEmail);
        password=findViewById(R.id.password);

        firebaseAuth=FirebaseAuth.getInstance();


        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        signin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String email_str= Objects.requireNonNull(email.getEditText()).getText().toString().trim();
                String pass_str= Objects.requireNonNull(password.getEditText()).getText().toString().trim();
                firebaseAuth.signInWithEmailAndPassword(email_str,pass_str).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                        else {
                            Toast.makeText(LoginActivity.this, task.getException() + "Failed!! try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

}