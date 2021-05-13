package com.example.dogood;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import static com.example.dogood.home.*;


public class navHomeFragment extends Fragment {


    public navHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getWindow().setStatusBarColor(getActivity().getResources().getColor(R.color.white));

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav_home, container, false);
    }
}