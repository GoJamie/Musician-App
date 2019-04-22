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
import java.util.List;
import java.util.Map;

// Tutorial used to create recycler view:
// https://code.tutsplus.com/tutorials/getting-started-with-recyclerview-and-cardview-on-android--cms-23465

// TODO: thath required api stuffs seems stupid. Can you remove them somehow?

public class EventList extends AppCompatActivity {

    private RecyclerView event_recycler;
    private static final String TAG = "EventList";
    Context context;
    private List<Event> events = new ArrayList<>();;

    private List<String> event_uids = new ArrayList<String>();;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);
        event_recycler = findViewById(R.id.event_recycler);
        LinearLayoutManager event_manager = new LinearLayoutManager(this);
        event_recycler.setLayoutManager(event_manager);
        //initializeData();
        db.collection("events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                event_uids.add(document.getId());
                                Event data = document.toObject(Event.class);
                                events.add(data);
                            }
                            initializeAdapter();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        // Add a new document with a generated ID
        event_recycler.addOnItemTouchListener(
                new EventRecyclerItemClickListener(context, event_recycler ,new EventRecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        String current_uid = event_uids.get(position);
                        Intent i=new Intent(EventList.this,EventPage.class);
                        i.putExtra("event_uid", current_uid); // there are many different types of data you can package
                        startActivity(i);

                    }

                })
        );

    }

    private void initializeAdapter(){
        RecyclerView.Adapter event_adapter = new EventAdapter(events);
        event_recycler.setAdapter(event_adapter);
    }
}
