package com.example.dogood;

import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class navQrFragment extends Fragment {

    Button btnScanQr;

    public navQrFragment() {
        // Required empty public constructor
    }

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nav_qr, container, false);
        getActivity().getWindow().setStatusBarColor(getActivity().getResources().getColor(R.color.white));

        btnScanQr = view.findViewById(R.id.btnScanQr);
        btnScanQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanner();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void scanner() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(navQrFragment.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("");
        integrator.setCameraId(0);
        integrator.setOrientationLocked(false);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result =  IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getContext(), "QR Scan Canceled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), result.getContents().toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}