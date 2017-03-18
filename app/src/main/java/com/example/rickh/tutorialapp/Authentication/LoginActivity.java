package com.example.rickh.tutorialapp.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rickh.tutorialapp.R;
import com.example.rickh.tutorialapp.SectionsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextView RegisterTV;
    EditText UserPasswordEt, UserEmailEt;
    Button LoginBtn;
    String UserName, Password, Email;
    ProgressDialog Progress;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        RegisterTV = (TextView)findViewById(R.id.RegisterTV);
        UserPasswordEt = (EditText) findViewById(R.id.UserPasswordET);
        UserEmailEt = (EditText)findViewById(R.id.UserEmailET);
        LoginBtn = (Button)findViewById(R.id.LoginBtn);

        auth = FirebaseAuth.getInstance();

        Progress = new ProgressDialog(this);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });


        RegisterTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }


    private void checkLogin() {
        String Email = UserEmailEt.getText().toString().trim();
        String Password = UserPasswordEt.getText().toString().trim();

        if(!TextUtils.isEmpty(Email) && !TextUtils.isEmpty(Password)) {

            Progress.setMessage("Checking Login ...");
            Progress.show();

            auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()) {
                        Progress.dismiss();
                        Intent intent = new Intent(LoginActivity.this , SectionsActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Progress.dismiss();
                        Toast.makeText(LoginActivity.this, "Error Login", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
