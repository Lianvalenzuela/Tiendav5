package com.example.tiendav1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private Button signUpButton;
    private TextInputEditText emailEditText;
    private EditText passwordEditText;
    private FirebaseAuth mAuth;
    private TextView inicio;
    private FirebaseAnalytics mFirebaseanalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //analytics
        mFirebaseanalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString("message", "Integracion de firebase completa");
        mFirebaseanalytics.logEvent("InitScreen", bundle);

        //firebase autenticacion
        mAuth = FirebaseAuth.getInstance();

        emailEditText = (TextInputEditText) findViewById(R.id.email);
        passwordEditText = (TextInputEditText) findViewById(R.id.password);
        signUpButton = findViewById(R.id.signUpButton);
        inicio = (TextView) findViewById(R.id.inicio);
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AuthActivity.class);
                startActivity(intent);
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = String.valueOf(emailEditText.getText());
                password = String.valueOf(passwordEditText.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(HomeActivity.this, "Ingresar email: ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(HomeActivity.this, "Ingresar contraseña: ", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(HomeActivity.this, "Registro exitoso!", Toast.LENGTH_SHORT).show();
                                    Intent intentR = new Intent(HomeActivity.this, AuthActivity.class);
                                    startActivity(intentR);
                                }else{
                                    Toast.makeText(HomeActivity.this, "Registro fallido", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}