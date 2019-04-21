package com.example.musicians;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class ProfilePage extends AppCompatActivity {
    private Button saveButton;
    private TextView name;
    private TextView email;
    private TextView mobile;
    private TextView aboutMe;
    private TextView location;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String TAG = "ProfilePage";
    private Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        edit = (Button) findViewById(R.id.edit_profile_button);

        name = (TextView) findViewById(R.id.profile_name);
        email = (TextView) findViewById(R.id.profile_email);
        mobile = (TextView) findViewById(R.id.profile_number);
        location = (TextView) findViewById(R.id.profile_location);
        aboutMe = (TextView) findViewById(R.id.profile_about);


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        User data = document.toObject(User.class);

                        name.setText(data.firstname+" "+data.lastname);

                        location.setText(data.city);

                        email.setText(data.email);

                        aboutMe.setText(data.aboutme);

                        mobile.setText(data.mobile);

                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProfilePage.this,  EditProfilePage.class));
            }
        });
    }
}
