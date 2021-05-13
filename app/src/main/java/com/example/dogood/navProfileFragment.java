package com.example.dogood;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class navProfileFragment extends Fragment {

    Button editProfile, changePsw;
    TextView email, name, dob, nic, phoneNumber, location, occupation;
    ImageView profileImagesView;
    private DatabaseReference ref;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    public navProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nav_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        profileImagesView = view.findViewById(R.id.profileImagesView);

        name = view.findViewById(R.id.txtUserName);
        email = view.findViewById(R.id.txtUserEmail);
        dob = view.findViewById(R.id.lblDobView);
        nic = view.findViewById(R.id.lblNicView);
        phoneNumber = view.findViewById(R.id.lblPhoneNumberView);
        location = view.findViewById(R.id.lblLocationView);
        occupation = view.findViewById(R.id.lblOccupationView);

        getData();



        editProfile = view.findViewById(R.id.btnToGoEditProfilePage);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Call edit profile fragment
                Fragment editProfileFragment = new editProfileFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_layout, editProfileFragment );
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        changePsw = view.findViewById(R.id.btnToGoEditChangePswPage);
        changePsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                Call edit profile fragment
                Fragment changePswFragment = new changePswFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_layout, changePswFragment );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        getActivity().getWindow().setStatusBarColor(getActivity().getResources().getColor(R.color.colorBlue));

        // Inflate the layout for this fragment
        return view;
    }

    private void getData() {

        String userId = mAuth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference(userId);
        storageReference.child("Profile_Pictures/" + userId).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImagesView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                name.setText(snapshot.child("name").getValue().toString());
                email.setText(snapshot.child("email").getValue().toString());
                dob.setText(snapshot.child("dob").getValue().toString());
                nic.setText(snapshot.child("nic").getValue().toString());
                phoneNumber.setText(snapshot.child("phoneNumber").getValue().toString());
                location.setText(snapshot.child("location").getValue().toString());
                occupation.setText(snapshot.child("occupation").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}