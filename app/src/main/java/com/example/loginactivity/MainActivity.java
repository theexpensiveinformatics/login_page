package com.example.loginactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
        LinearLayout linear3;
        LinearLayout linearg;
        EditText pass_log,email_log;
        LinearLayout login;
        FirebaseAuth fAuth;
        ImageView img;
        LottieAnimationView Lanim;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Lanim=(LottieAnimationView)findViewById(R.id.Lanim);
        img=(ImageView)findViewById(R.id.img);
        email_log=(EditText)findViewById(R.id.email_log);
        pass_log=(EditText)findViewById(R.id.pass_log);
        login=(LinearLayout)findViewById(R.id.signup);
        fAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_log.getText().toString().trim();
                String password = pass_log.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    email_log.setError("Email is Required");
                } else if (TextUtils.isEmpty(password)) {
                    pass_log.setError("Password is Required");
                } else if (password.length() < 6) {
                    pass_log.setError("Password must be Greater than 6 Charcters");
                }
                else{
                    img.setVisibility(View.GONE);
                    Lanim.setVisibility(View.VISIBLE);
                //Authenticate the user
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(MainActivity.this, "Lodged In !!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                        } else {
                            Toast.makeText(MainActivity.this, "Error !!" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
            }
        });

        linear3 = (LinearLayout) findViewById(R.id.linear3);
        linear3.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View view) {
                signup();
            }
        });

    }

    public void signup() {
        Intent intent = new Intent(this, signup.class);
        startActivity(intent);
        finish();
    }




}
