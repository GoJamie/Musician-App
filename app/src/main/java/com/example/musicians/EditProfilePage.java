package com.example.musicians;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditProfilePage extends AppCompatActivity {
    private static final String TAG = "EditProfilePage";

    private TextView mDisplayDate;
    private Button saveButton;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText mobile;
    private EditText aboutMe;
    private EditText city;

    public FirebaseFirestore db = FirebaseFirestore.getInstance();

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_page);
        firstName   = (EditText)findViewById(R.id.firstName_profile);

        lastName   = (EditText)findViewById(R.id.lastName_profile);

        email   = (EditText)findViewById(R.id.email_profile);

        mobile   = (EditText)findViewById(R.id.mobile_profile);

        aboutMe   = (EditText)findViewById(R.id.aboutMe_profile);

        city   = (EditText)findViewById(R.id.city_profile);


        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> a  = document.getData();
                        firstName.setText(a.get("firstname").toString());

                        lastName.setText(a.get("lastname").toString());

                        city.setText(a.get("city").toString());

                        email.setText(a.get("email").toString());

                        aboutMe.setText(a.get("aboutMe").toString());

                        mDisplayDate.setText(a.get("date").toString());

                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        mDisplayDate = (TextView) findViewById(R.id.tvDate2);
        saveButton = (Button) findViewById(R.id.save_edit);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        EditProfilePage.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);

            }
        };

        saveButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        String firstname_ = firstName.getText().toString();

                        String lastname_ = lastName.getText().toString();

                        String mobile_ = mobile.getText().toString();

                        String city_ = city.getText().toString();

                        String email_ = email.getText().toString();

                        String about_me_ = aboutMe.getText().toString();
                        
                        String date = mDisplayDate.getText().toString();

                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        User create_user = new User(firstname_, lastname_, mobile_, city_, email_, about_me_, date, uid);

                        db.collection("users").document(uid)
                                .set(create_user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully written!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error writing document", e);
                                    }
                                });
                    }
                });

    }
}
