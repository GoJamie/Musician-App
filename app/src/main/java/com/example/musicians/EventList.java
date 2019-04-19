package com.example.musicians;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
    private List<Event> events;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);
        event_recycler = findViewById(R.id.event_recycler);
        LinearLayoutManager event_manager = new LinearLayoutManager(this);
        event_recycler.setLayoutManager(event_manager);
        initializeData();
        db.collection("events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Event data = document.toObject(Event.class);
                                events.add(data);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        // Add a new document with a generated ID
        initializeAdapter();
    }

    private void initializeData(){

        String date = "2018/06/24";
        List<Message> messagelist = new ArrayList<Message>();
        messagelist.add(new Message("Sampo","Hi"));

        Event example_event1 = new Event("Polar Bear Pitching", "test1", "Oulu", "Torikatu 1", date, 6, "Sampo123",messagelist);

        db.collection("events")
                .add(example_event1)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

        Event example_event2 = new Event("Polar Panda Pitching", "test2", "Helsinki", "Torikatu 1", date, 6, "Nechir123",messagelist);

        db.collection("events")
                .add(example_event2)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
     }

    private void initializeAdapter(){
        RecyclerView.Adapter event_adapter = new EventAdapter(events);
        event_recycler.setAdapter(event_adapter);
    }
}
