package com.example.musicians;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

public class UserList extends AppCompatActivity {

    private RecyclerView user_recycler;
    private static final String TAG = "UserList";
    Context context;
    private List<User> users = new ArrayList<>();;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);
        user_recycler = findViewById(R.id.user_recycler);
        LinearLayoutManager user_manager = new LinearLayoutManager(this);
        user_recycler.setLayoutManager(user_manager);
        //initializeData();
        // Add a new document with a generated ID
        users.add(new User("hello", "asd", "asd", "asd", "asd", "asd", "asd", "asd"));
        initializeAdapter();

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
