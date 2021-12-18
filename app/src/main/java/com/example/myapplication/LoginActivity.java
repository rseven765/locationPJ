package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;


    TextView signUp;

    String email = "";
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //remove title bar
        getSupportActionBar().hide();

        //gestureDetector for sign up
        signUp = (TextView) findViewById(R.id.signUp);
        signUp.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // TODO Auto-generated method stub
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    //do stuff here
                    Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Toast.makeText(LoginActivity.this, "login success",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void reload(){
        EditText emailText = (EditText)findViewById(R.id.emailEditText);
        EditText passwordText = (EditText)findViewById(R.id.nameEditText);
        email = emailText.getText().toString();
        password = passwordText.getText().toString();


        if(!confirmEmail()){
            Toast.makeText(LoginActivity.this, "잘못된 이메일 형식 입니다.",
                    Toast.LENGTH_SHORT).show();
            return;
        }else if(!confirmPassword()){
            Toast.makeText(LoginActivity.this, "비밀번호는 6자리 이상입니다.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("kdqlcqiecnlqi", "signInWithEmail:success");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("kdqlcqiecnlqi", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public boolean confirmEmail(){
        String pattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
        String tempEmail = email;
        if(Pattern.matches(pattern, tempEmail)){
            return true;
        }
        return false;
    }
    public boolean confirmPassword(){
        String tempPassword = password;
        if(tempPassword.length()>5){
            return true;
        }
        return false;
    }

    public void loginBtnClick(View view){
        reload();

    }
}