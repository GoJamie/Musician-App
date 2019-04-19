package com.example.musicians;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class Dashboard extends AppCompatActivity {

    private CardView create_event;
    private CardView events;
    private CardView profile;
    private CardView logout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        create_event   = (CardView)findViewById(R.id.dash_create);

        events   = (CardView)findViewById(R.id.dash_events);

        profile   = (CardView)findViewById(R.id.dash_profile);

        logout   = (CardView)findViewById(R.id.dash_logout);

        create_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Dashboard.this, CreateEvent.class));

            }
        });
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Dashboard.this, EventList.class));

            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Dashboard.this, ProfilePage.class));

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(Dashboard.this,TestUI.class);
                i.putExtra("logout", true); // there are many different types of data you can package
                startActivity(i);

            }
        });


    }
}
