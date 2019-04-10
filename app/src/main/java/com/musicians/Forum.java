package com.musicians;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.musicians.R;



public class Forum extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        private String[] message = new String[20];
        message[0] = "user1: hello";
        message[1] = "user2: you too";


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, message);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);


        // Create a message handling object as an anonymous class.
        //private OnItemClickListener messageClickedHandler = new OnItemClickListener() {
        //    public void onItemClick(AdapterView parent, View v, int position, long id) {
                // Do something in response to the click
        //    }
        //};

        //listView.setOnItemClickListener(messageClickedHandler);




    }
}
