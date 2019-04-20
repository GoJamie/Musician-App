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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserList extends AppCompatActivity {

    private RecyclerView user_recycler;
    private static final String TAG = "UserList";
    Context context;
    private List<User> users = new ArrayList<>();;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);
        user_recycler = findViewById(R.id.user_recycler);
        LinearLayoutManager user_manager = new LinearLayoutManager(this);
        user_recycler.setLayoutManager(user_manager);
        //initializeData();
        // Add a new document with a generated ID
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
