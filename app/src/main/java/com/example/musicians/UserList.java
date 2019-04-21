package com.example.musicians;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class UserList extends AppCompatActivity {

    private RecyclerView user_recycler;
    private static final String TAG = "UserList";
    Context context;
    private List<User> users;
    private String event_uid;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);
        event_uid = getIntent().getStringExtra("event_uid");
        user_recycler = findViewById(R.id.user_recycler);
        LinearLayoutManager user_manager = new LinearLayoutManager(this);
        user_recycler.setLayoutManager(user_manager);
        //initializeData();
        // Add a new document with a generated ID
        DocumentReference docRef = db.collection("events").document(event_uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Event data = document.toObject(Event.class);
                        users = data.getParticipants();
                        initializeAdapter();
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        user_recycler.addOnItemTouchListener(
                new UserRecyclerItemClickListener(context, user_recycler ,new UserRecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        //
                        startActivity(new Intent(UserList.this, Dashboard.class));
                    }

                })
        );

    }


    private void initializeAdapter(){
        RecyclerView.Adapter user_adapter = new UserAdapter(users);
        user_recycler.setAdapter(user_adapter);
    }
}
