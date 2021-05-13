package com.example.dogood;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class changePswFragment extends Fragment {

    Button cancel, btnPswUpdate;
    private FirebaseAuth mAuth;
    EditText email, psw, newPsw, rNewPsw;

    public changePswFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_change_psw, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        cancel = view.findViewById(R.id.btnPswCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BottomNavigationView)getActivity().findViewById(R.id.bottomNavigationView)).setSelectedItemId(R.id.navigationProfile);
            }
        });


        btnPswUpdate = view.findViewById(R.id.btnPswUpdate);
        btnPswUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = view.findViewById(R.id.txtCurrentEmail);
                psw = view.findViewById(R.id.txtCurrentPsw);
                newPsw = view.findViewById(R.id.txtNewPsw);
                rNewPsw = view.findViewById(R.id.txtReEnterPsw);

                String semail = email.getText().toString();
                String spsw = psw.getText().toString();
                String snewPsw = newPsw.getText().toString();
                String srNewPsw = rNewPsw.getText().toString();

                if (semail.isEmpty() || spsw.isEmpty() || snewPsw.isEmpty() || srNewPsw.isEmpty()) {
                    Toast.makeText(getActivity(), "Text Fields are Empty!", Toast.LENGTH_SHORT).show();
                }
                else if (!emailValidate.isValidEmail(semail)) {
                    Toast.makeText(getActivity(), "Your Email Not Validate", Toast.LENGTH_SHORT).show();
                }
                else if (!snewPsw.equals(srNewPsw)) {
                    Toast.makeText(getActivity(), "Confirm Password Not Matched!", Toast.LENGTH_SHORT).show();
                }
                else {
//                    Change password
                    AuthCredential credential = EmailAuthProvider.getCredential(semail, spsw);
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(snewPsw).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getActivity(), "Password Update Successful!", Toast.LENGTH_SHORT).show();
                                                    ((BottomNavigationView)getActivity().findViewById(R.id.bottomNavigationView)).setSelectedItemId(R.id.navigationProfile);
                                                }
                                                else {
                                                    String msg = task.getException().toString();
                                                    Toast.makeText(getActivity(), "Error" + msg, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                    else {
                                        String msg = task.getException().toString();
                                        Toast.makeText(getActivity(), "Error" + msg, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

        return view;
    }

}