package com.example.v_registration_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.v_registration_login.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    Button buttonLogin;
    ProgressBar progressBar;
    TextView goToRegister;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btn_Login);
        goToRegister = findViewById(R.id.RegisterNow);
        progressBar = findViewById(R.id.progressBar);

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Registration.class);
                startActivity(intent);
                finish();
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                buttonLogin.setVisibility(View.INVISIBLE);
                String email, password;
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();

                if (email.matches("")) {
                    Toast.makeText(Login.this, "Valid email must be provided", Toast.LENGTH_SHORT).show();
                    editTextEmail.setError("Cannot be empty");
                    progressBar.setVisibility(View.INVISIBLE);
                    buttonLogin.setVisibility(View.VISIBLE);
                    return;
                }
                if (password.matches("")) {
                    Toast.makeText(Login.this, "Enter a valid password", Toast.LENGTH_SHORT).show();
                    editTextPassword.setError("Cannot be empty");
                    progressBar.setVisibility(View.INVISIBLE);
                    buttonLogin.setVisibility(View.VISIBLE);
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    FirebaseUser fUser = task.getResult().getUser();
                                    String userId = fUser.getUid();
                                    db.collection("users").document(userId).get()
                                            .addOnSuccessListener(documentSnapshot -> {
                                                if(documentSnapshot.exists()){
                                                    String username = documentSnapshot.getString("username");
                                                    String email = documentSnapshot.getString("email");
                                                    String address = documentSnapshot.getString("address");
                                                    String phonenumber = documentSnapshot.getString("phonenumber");
                                                    String role = documentSnapshot.getString("role");
                                                    User user = new User(userId, username, email, address, phonenumber, role);

                                                    SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
                                                    User.saveUserToSharedPreference(sharedPreferences,user);

                                                    Toast.makeText(getApplicationContext(), "Login successfully.", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }else {
                                                    Toast.makeText(Login.this, "Login failed",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                } else {

                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                buttonLogin.setVisibility(View.VISIBLE);
            }
        });


    }
}