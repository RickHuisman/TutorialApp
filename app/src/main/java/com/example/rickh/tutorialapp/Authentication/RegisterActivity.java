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

import com.example.rickh.tutorialapp.R;
import com.example.rickh.tutorialapp.SectionsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText EmailET;
    private EditText UserNameET;
    private EditText PasswordET;
    private TextView LoginTV;
    private Button RegisterBtn;
    private String Email, UserName, Password;


    private FirebaseAuth auth;

    private ProgressDialog Progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EmailET = (EditText)findViewById(R.id.UserEmailET);
        UserNameET = (EditText)findViewById(R.id.UserNameET);
        PasswordET = (EditText)findViewById(R.id.UserPasswordET);
        RegisterBtn = (Button)findViewById(R.id.RegisterBt);
        LoginTV = (TextView)findViewById(R.id.LoginTV);

        Progress = new ProgressDialog(this);

        auth = FirebaseAuth.getInstance();


        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
            }
        });

        LoginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void startRegister() {
        Email = EmailET.getText().toString();
        UserName = UserNameET.getText().toString();
        Password = PasswordET.getText().toString();

        if(!TextUtils.isEmpty(UserName) && !TextUtils.isEmpty(Password) && !TextUtils.isEmpty(Email)) {

            Progress.setMessage("Signing Up....");
            Progress.show();


            auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()) {
                        String userID = auth.getCurrentUser().getUid();


                        Progress.dismiss();

                        Intent mainIntent = new Intent(RegisterActivity.this, SectionsActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);
                    }
                }
            });
        }
    }
}
