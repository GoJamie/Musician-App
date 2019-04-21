package com.example.musicians;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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

    private TextView dialogtext;


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

                        User data = document.toObject(User.class);

                        firstName.setText(data.firstname);

                        lastName.setText(data.lastname);

                        city.setText(data.city);

                        email.setText(data.email);

                        aboutMe.setText(data.aboutme);

                        mobile.setText(data.mobile);

                        mDisplayDate.setText(data.birthday);

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
//                                        showCustomDialog("The profile info was saved");
                                        Helper.showCustomDialog("info was saved", EditProfilePage.this, Dashboard.class);


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

    private void showCustomDialog(String text) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);
        dialogtext = (TextView) findViewById(R.id.dialog_text);


        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.my_dialog, viewGroup, false);



        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        //finally creating the alert dialog and displaying it
        final  AlertDialog alertDialog = builder.create();
        alertDialog.show();

        TextView editText = (TextView) dialogView.findViewById(R.id.dialog_text);
        editText.setText(text);

        final Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            public void run() {
                alertDialog.dismiss();
                timer2.cancel(); //this will cancel the timer of the system
            }
        }, 2000);


    }

}
