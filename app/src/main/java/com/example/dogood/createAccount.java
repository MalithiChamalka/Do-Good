package com.example.dogood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class createAccount extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    private Button btnBackToLogin, createAccount;
    private EditText name, email, psw, cpsw;

    private FirebaseAuth mAuth;
    private DatabaseReference reference;
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

        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();

        btnBackToLogin = findViewById(R.id.btnBackToLogin);
        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginActivity();
            }
        });

        createAccount = findViewById(R.id.btnCreateAccount);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Text edit assign to variables
                name = findViewById(R.id.txtCreateAccountName);
                email = findViewById(R.id.txtCreateAccountEmail);
                psw = findViewById(R.id.txtCreateAccountPsw);
                cpsw = findViewById(R.id.txtCreateAccountConfirmPsw);

//                Cast to strings
                String sname = name.getText().toString();
                String semail = email.getText().toString();
                String spsw = psw.getText().toString();
                String scpsw = cpsw.getText().toString();

//                Validate and create account
                if (sname.isEmpty() || semail.isEmpty() || spsw.isEmpty() || scpsw.isEmpty()) {
                    Toast.makeText(createAccount.this, "Text Fields are Empty!", Toast.LENGTH_SHORT).show();
                }
                else if (!emailValidate.isValidEmail(semail)) {
                    Toast.makeText(createAccount.this, "Your Email Not Validate", Toast.LENGTH_SHORT).show();
                }
                else if (spsw.length() < 6 ) {
                    Toast.makeText(createAccount.this, "Your Password Not Strong", Toast.LENGTH_SHORT).show();
                }
                else if (!spsw.equals(scpsw)) {
                    Toast.makeText(createAccount.this, "Confirm Password Not Matched!", Toast.LENGTH_SHORT).show();
                }
                else {

//                      Start Progress bar
                    loadingBar = new ProgressDialog(createAccount.this);
                    loadingBar.setTitle("Creating new Account");
                    loadingBar.setMessage("Please Wait");
                    loadingBar.setCanceledOnTouchOutside(true);
                    loadingBar.show();

//                    Create Account using email and password
                    mAuth.createUserWithEmailAndPassword(semail, spsw)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(createAccount.this, "Account Create Successful", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

//                                      Get user id
                                        String userId = mAuth.getCurrentUser().getUid();

                                        reference = FirebaseDatabase.getInstance().getReference(userId);
                                        InsertAccountDetails insertAccountDetails = new InsertAccountDetails();

                                        insertAccountDetails.setName(sname);
                                        insertAccountDetails.setEmail(semail);
                                        insertAccountDetails.setDob("");
                                        insertAccountDetails.setNic("");
                                        insertAccountDetails.setPhoneNumber("");
                                        insertAccountDetails.setLocation("");
                                        insertAccountDetails.setOccupation("");

                                        reference.setValue(insertAccountDetails);

                                        homeActivity();
                                        Toast.makeText(createAccount.this, "Please Login", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        String msg = task.getException().toString();
                                        Toast.makeText(createAccount.this, "Error" + msg, Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
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

    private void loginActivity() {
        Intent login = new Intent(createAccount.this, login.class);
        startActivity(login);
        finish();
    }

    private void homeActivity() {
        Intent home = new Intent(createAccount.this, home.class);
        startActivity(home);
        finish();
    }

}