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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class signup extends AppCompatActivity {
    private LinearLayout linear3;
    private DatabaseReference reference;
    private DatabaseReference dbRef;
    EditText email_res,username_res,pass_res,repass_res;
    LinearLayout signup;
    FirebaseAuth fAuth;
    String password;
    String email;
    String username;
    String repassword;
    LottieAnimationView LA;
    ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        imageView=(ImageView)findViewById(R.id.img);
        LA=(LottieAnimationView)findViewById(R.id.LA);
        signup=(LinearLayout)findViewById(R.id.signup);
        username_res=(EditText)findViewById(R.id.username_res);
        email_res=(EditText)findViewById(R.id.email_res);
        pass_res=(EditText)findViewById(R.id.pass_res);
        repass_res=(EditText)findViewById(R.id.repass_res);
        fAuth= FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        if(fAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity2.class));
            finish();
        }

        // signup on click listener
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validData();

            }
        });




        linear3 =(LinearLayout) findViewById(R.id.linear3);
        linear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity();
            }
        });
    }

    private void validData() {
         email = email_res.getText().toString().trim();
         password = pass_res.getText().toString().trim();
         username= username_res.getText().toString().trim();
         repassword=repass_res.getText().toString().trim();

        if (TextUtils.isEmpty(email))
        {
            email_res.setError("Email is Required");
            email_res.requestFocus();
        }

        else if(TextUtils.isEmpty(password))
        {
            pass_res.setError("Password is Required");
            pass_res.requestFocus();
        }

        else if (password.length()<6)
        {
            pass_res.requestFocus();
            pass_res.setError("Password must be Greater than 6 Charcters");
        }
        else if (username.isEmpty())
        {
            username_res.setError("Enter Your Name");
            username_res.requestFocus();
        }
        else if(!password.equals(repassword))
        {
            repass_res.setError("Both Password are not match");
            repass_res.requestFocus();
        }

        else {
            imageView.setVisibility(View.GONE);
            LA.setVisibility(View.VISIBLE);
            createUser();
           }

        }



    private void createUser() {
        //register the users in firebase
        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {

                    Toast.makeText(signup.this,"User Created",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                    uploadData();
                }else {
                    Toast.makeText(signup.this,"Error !!"+ task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(signup.this,"Error !!"+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

    private void uploadData() {
        dbRef=reference.child("users");
        String key = dbRef.push().getKey();
        reference.child("users");

        HashMap<String, String>user = new HashMap<>();
        user.put("key",key);
        user.put("name",username);
        user.put("email",email);
        user.put("pass",password);

        dbRef.child(key).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(signup.this,"user created !!",Toast.LENGTH_LONG).show();


                        }else {
                            Toast.makeText(signup.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(signup.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void MainActivity() {
        Intent intent2 = new Intent(this, MainActivity.class);
        finish();
    }
}