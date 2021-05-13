package com.example.dogood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class login extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    Button btnCreateAccountPage, btnLogin;
    EditText email, psw;

    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorBlue));
        window.setNavigationBarColor(this.getResources().getColor(R.color.white));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);

        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        btnCreateAccountPage = findViewById(R.id.btnCreateAccountPage);
        btnCreateAccountPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAccount = new Intent(login.this, createAccount.class);
                startActivity(createAccount);
                finish();
            }
        });

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = findViewById(R.id.txtLoginEmail);
                psw = findViewById(R.id.txtLoginPsw);

                String semail = email.getText().toString();
                String spsw = psw.getText().toString();

                if (semail.isEmpty() || spsw.isEmpty()) {
                    Toast.makeText(login.this, "Text Fields are Empty!", Toast.LENGTH_SHORT).show();
                }
                else if (!emailValidate.isValidEmail(semail)) {
                    Toast.makeText(login.this, "Your Email Not Validate", Toast.LENGTH_SHORT).show();
                }
                else {

//                    Start Progress bar
                    loadingBar = new ProgressDialog(login.this);
                    loadingBar.setTitle("Waiting for login");
                    loadingBar.setMessage("Please Wait");
                    loadingBar.setCanceledOnTouchOutside(true);
                    loadingBar.show();

//                    For login
                    mAuth.signInWithEmailAndPassword(semail, spsw)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        loadingBar.dismiss();
                                        Intent home = new Intent(login.this, home.class);
                                        startActivity(home);
                                        finish();
                                    }
                                    else {
                                        loadingBar.dismiss();
                                        Toast.makeText(login.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                }

            }
        });

    }

//    Double tap back button to close app
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Double Tap To Exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}