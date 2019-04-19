package com.example.musicians;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
        db.collection("events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Map<String, Object> event = document.getData();
                                events.add(new Event(event.get("name").toString(),event.get("city").toString(),event.get("address").toString(),event.get("date").toString(),Integer.parseInt(event.get("participants").toString()),event.get("owner").toString()));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        // Add a new document with a generated ID
        initializeData();
        initializeAdapter();
    }

    private void initializeData(){
        events = new ArrayList<>();
        // TODO: REMOVE HARDCODED NAMES
        //DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy ss:mm:HH");
        String date = "2018/06/24";
        //dateFormat.format((TemporalAccessor) date);
        events.add(new Event("Polar Bear Pitching", "Oulu", "Torikatu 1", date, 6, "Sampo123"));
        events.add(new Event("Polar Bear Pitching", "Oulu", "Torikatu 1", date, 6, "Sampo123"));
        events.add(new Event("Polar Bear Pitching", "Oulu", "Torikatu 1", date, 6, "Sampo123"));
        events.add(new Event("Polar Bear Pitching", "Oulu", "Torikatu 1", date, 6, "Sampo123"));
        events.add(new Event("Polar Bear Pitching", "Oulu", "Torikatu 1", date, 6, "Sampo123"));
    }

    private void initializeAdapter(){
        RecyclerView.Adapter event_adapter = new EventAdapter(events);
        event_recycler.setAdapter(event_adapter);
    }

}
