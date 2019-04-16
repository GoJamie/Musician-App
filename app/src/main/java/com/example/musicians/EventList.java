package com.example.musicians;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

// Tutorial used to create recycler view:
// https://code.tutsplus.com/tutorials/getting-started-with-recyclerview-and-cardview-on-android--cms-23465

// TODO: thath required api stuffs seems stupid. Can you remove them somehow?

public class EventList extends AppCompatActivity {

    private RecyclerView event_recycler;
    private List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);
        event_recycler = findViewById(R.id.event_recycler);
        LinearLayoutManager event_manager = new LinearLayoutManager(this);
        event_recycler.setLayoutManager(event_manager);

        initializeData();
        initializeAdapter();
    }

    private void initializeData(){
        events = new ArrayList<>();
        // TODO: REMOVE HARDCODED NAMES
        //DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy ss:mm:HH");
        Date date = new Date();
        //dateFormat.format((TemporalAccessor) date);
        events.add(new Event("Polar Bear Pitching", "Oulu City Center", "Oulu", "Torikatu 1", date, 6, "Sampo123", "public"));
    }

    private void initializeAdapter(){
        RecyclerView.Adapter event_adapter = new EventAdapter(events);
        event_recycler.setAdapter(event_adapter);
    }

}
