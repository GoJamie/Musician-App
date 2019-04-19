package com.example.musicians;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.ObjectStreamException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    String event_uid = getIntent().getStringExtra("event_uid");
    private static final String TAG = "ForumPage";

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

        final MatrixCursor MessageCursor = new MatrixCursor(new String[] {"username","message"});

        final DocumentReference docRef = db.collection("events").document(event_uid);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Event data = snapshot.toObject(Event.class);
                    List<Message> messagelist = data.getMessageList();
                    for ( Message message : messagelist ) {
                        MessageCursor.newRow()
                                .add("username", message.username)
                                .add("message", message.message);
                    }
                    Log.d(TAG, "Current data: " + snapshot.getData());
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });

        // Find ListView to populate
        ListView messages_list = findViewById(R.id.messages_list);
        // Setup cursor adapter using cursor
        MessageCursorAdapter MessageAdapter = new MessageCursorAdapter(this, MessageCursor);
        // Attach cursor adapter to the messages_list
        messages_list.setAdapter(MessageAdapter);
    }


}