package com.example.musicians;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.firestore.FirebaseFirestore;

// https://guides.codepath.com/android/Populating-a-ListView-with-a-CursorAdapter

// if a message is sent: this should be called to update the messages notifyDataSetChanged(). However it may not be needed due to the onitem click listener.

// Create a message handling object as an anonymous class.
/*
private OnItemClickListener messageClickedHandler = new OnItemClickListener() {
public void onItemClick(AdapterView parent, View v, int position, long id) {
        // Do something in response to the click
        // Add item to database --> add new message forumpage.
        }
        };

        messages_list.setOnItemClickListener(messageClickedHandler);
*/
public class ForumPage extends AppCompatActivity {
    
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_page);


        //TodoDatabaseHandler is a SQLiteOpenHelper class connecting to SQLite
        //TodoDatabaseHandler handler = new TodoDatabaseHandler(this);
        // Get access to the underlying writeable database
        //SQLiteDatabase db = handler.getWritableDatabase();
        //Query for items from the database and get a cursor back
        // TODO need to figure out how to point to a particular event's message database.


        Cursor MessageCursor = db.rawQuery("SELECT  * FROM message_database", null);

        // Find ListView to populate
        ListView messages_list = findViewById(R.id.messages_list);
        // Setup cursor adapter using cursor
        MessageCursorAdapter MessageAdapter = new MessageCursorAdapter(this, MessageCursor);
        // Attach cursor adapter to the messages_list
        messages_list.setAdapter(MessageAdapter);
    }


}