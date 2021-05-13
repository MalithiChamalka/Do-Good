package com.example.dogood;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class navLogoutFragment extends Fragment {

    Button clickedYes, clickedNo;
    private FirebaseAuth mAuth;

    public navLogoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nav_logout, container, false);
        getActivity().getWindow().setStatusBarColor(getActivity().getResources().getColor(R.color.white));

        mAuth = FirebaseAuth.getInstance();

        clickedNo = view.findViewById(R.id.btnLogoutNo);
        clickedNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Show home fragment
                ((BottomNavigationView)getActivity().findViewById(R.id.bottomNavigationView)).setSelectedItemId(R.id.navigationHome);
            }
        });

        clickedYes = view.findViewById(R.id.btnLogoutYes);
        clickedYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();

//                Show main activity
                Intent MainActivity = new Intent(getActivity(), MainActivity.class);
                startActivity(MainActivity);
                getActivity().finish();
            }
        });


        return view;
    }

    public void returnFragment() {

    }

}