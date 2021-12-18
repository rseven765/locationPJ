package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText email;
    EditText password;
    EditText verifyPassword;
    EditText name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //remove title bar
        getSupportActionBar().hide();

        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        verifyPassword = findViewById(R.id.passwordEditText2);
        name = findViewById(R.id.nameEditText);

    }


    public void onClick(View view) {

        String email = this.email.getText().toString().trim();
        String password = this.password.getText().toString().trim();


        if (confirmEmail(email)) {
            if (confirmPassworld(password)) {
                addUser(email, password);
            } else {
                Toast.makeText(SignUpActivity.this, "비밀번호는 6자리 이상 입니다.",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SignUpActivity.this, "이메일 형식을 확인 해주세요.",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public boolean confirmEmail(String tempEmail) {
        String pattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";

        if (Pattern.matches(pattern, tempEmail)) {
            return true;
        }
        return false;
    }

    public boolean confirmPassworld(String tempPassword) {

        if (tempPassword.length() > 5) {
            return true;
        }
        return false;
    }

    public void addUser(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();

                            addUserDb(uid);

                        } else {
                            Log.d("error", task.getException().toString());
                        }
                    }
                });
    }


    public void verityEmail() {

    }

    public void addUserDb(String uid) {
        String name = this.name.getText().toString().trim();
        Map<String, Object> userDb = new HashMap<>();
        userDb.put("name", name);
        db.collection("users")
                .document(uid)
                .set(userDb)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("error", e.toString());
                    }
                });
    }


}