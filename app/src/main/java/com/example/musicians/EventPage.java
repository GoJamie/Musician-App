package com.example.musicians;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class EventPage extends AppCompatActivity {


    private static final String TAG = "EventPage";

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView mDisplayDate;

    private Button join;

    private Button show_users;

    private Button show_forum;

    private TextView name;

    private TextView description;

    private TextView address;

    private TextView city;

    private List<User> participants;

    private User joiner;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event_page);

        name   = (TextView)findViewById(R.id.event_page_name);

        description   = (TextView)findViewById(R.id.event_page_description);

        address   = (TextView)findViewById(R.id.event_page_address);

        city = (TextView)findViewById(R.id.event_page_city);

        join = (Button) findViewById(R.id.event_page_join);

        mDisplayDate = (TextView) findViewById(R.id.event_page_date);

        show_forum = (Button) findViewById(R.id.event_show_forum);

        show_users = (Button) findViewById(R.id.event_show_users);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        EventPage.this,
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

        final String event_uid= getIntent().getStringExtra("event_uid");
        DocumentReference docRef = db.collection("events").document(event_uid);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    if (document.exists()) {

                        Event data = document.toObject(Event.class);

                        for(User user : data.getParticipants()) {

                            Boolean a = user.uid.equals(uid);
                            if(a) {
                                join.setText("Leave");
                            }

                        }
                        participants = data.getParticipants();

                        name.setText("Name: " + data.getName());

                        description.setText("Description: "+data.getDescription());

                        address.setText("Address: " + data.getAddress());

                        city.setText("City: " +data.getCity());


                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }

        });

        show_forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(EventPage.this,ForumPage.class);
                i.putExtra("event_uid", event_uid); // there are many different types of data you can package
                startActivity(i);
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence pls = join.getText();
                final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                if(pls.equals("Join")) {

                    DocumentReference UserdocRef = db.collection("users").document(uid);
                    UserdocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    joiner = document.toObject(User.class);
                                    participants.add(joiner);
                                    db.collection("events").document(event_uid).update("participants", participants);
                                    join.setText("Leave");
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });

                }

                if(pls.equals("Leave")) {

                    DocumentReference UserdocRef = db.collection("users").document(uid);
                    UserdocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    joiner = document.toObject(User.class);
                                    User a = new User();
                                    for(User user : participants) {
                                        if(joiner.uid.equals(user.uid)) {
                                            a = user;
                                            join.setText("Join");
                                        }
                                    }
                                    participants.remove(a);

                                    db.collection("events").document(event_uid).update("participants", participants);
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });

                }



            }
        });

        show_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EventPage.this, UserList.class);
                i.putExtra("event_uid", event_uid); // there are many different types of data you can package
                startActivity(i);
            }
        });
    }
}

