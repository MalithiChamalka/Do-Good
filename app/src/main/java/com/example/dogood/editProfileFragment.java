package com.example.dogood;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


public class editProfileFragment extends Fragment {

    Button cancel, btnUpdate;
    TextView email, dob, popUpClose;
    EditText name, nic, phoneNumber, location, occupation;
    ImageView editProfilePicture;
    DatabaseReference ref;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    public Uri imgUri;

    Dialog popUpDialog;

    Calendar mCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    public editProfileFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

//        popUpDialog = new Dialog(getActivity());
//        popUpDialog.setContentView(R.layout.profile_popup_btn);

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        String userId = mAuth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference(userId);

        editProfilePicture = (ImageView) view.findViewById(R.id.editProfilePicture);

        email = (TextView) view.findViewById(R.id.txtEmail);
        name = view.findViewById(R.id.txtName);
        dob = (TextView) view.findViewById(R.id.txtDob);
        nic = view.findViewById(R.id.txtNic);
        phoneNumber = view.findViewById(R.id.txtPhoneNumber);
        location = view.findViewById(R.id.txtLocation);
        occupation = view.findViewById(R.id.txtOccupation);

        getData();

        editProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showPopUp();
                choosePicture();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener,
                        year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        cancel = view.findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BottomNavigationView)getActivity().findViewById(R.id.bottomNavigationView)).setSelectedItemId(R.id.navigationProfile);
            }
        });

        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

        return view;
    }

    private void getData() {

        String userId = mAuth.getCurrentUser().getUid();
        storageReference.child("Profile_Pictures/" + userId).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(editProfilePicture);
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

    private void updateData() {

        String sname = name.getText().toString();
        String sdob = dob.getText().toString();
        String snic = nic.getText().toString();
        String sphoneNumber = phoneNumber.getText().toString();
        String slocation = location.getText().toString();
        String soccupation = occupation.getText().toString();

        if (sname.isEmpty() || sdob.isEmpty() || snic.isEmpty() || sphoneNumber.isEmpty() || slocation.isEmpty() || soccupation.isEmpty()) {
            Toast.makeText(getActivity(), "Text Fields are Empty!", Toast.LENGTH_SHORT).show();
        }
        else {
            ref.child("name").setValue(sname);
            ref.child("dob").setValue(sdob);
            ref.child("nic").setValue(snic);
            ref.child("phoneNumber").setValue(sphoneNumber);
            ref.child("location").setValue(slocation);
            ref.child("occupation").setValue(soccupation);

            ((BottomNavigationView)getActivity().findViewById(R.id.bottomNavigationView)).setSelectedItemId(R.id.navigationProfile);
        }

    }

    private void updateLabel() {
        String mFormat = "yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(mFormat, Locale.US);

        dob.setText(sdf.format(mCalendar.getTime()));
    }

//    private void showPopUp() {
//        popUpDialog.show();
//        popUpClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popUpDialog.dismiss();
//            }
//        });
//
//    }

    private void choosePicture() {
        Intent selectPicture = new Intent();
        selectPicture.setType("image/*");
        selectPicture.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(selectPicture, 1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imgUri = data.getData();
            editProfilePicture.setImageURI(imgUri);

            uploadProfilePicture();
        }

    }

    private void uploadProfilePicture() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Uploading Image");
        progressDialog.show();

        final String userId = mAuth.getCurrentUser().getUid();
        StorageReference riversRef = storageReference.child("Profile_Pictures/" + userId);
        riversRef.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Image Uploaded!", Toast.LENGTH_SHORT).show();
                        ((BottomNavigationView)getActivity().findViewById(R.id.bottomNavigationView)).setSelectedItemId(R.id.navigationProfile);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Upload Failed!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercentage = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploading... " + (int) progressPercentage + "%");
                    }
                });

    }

}