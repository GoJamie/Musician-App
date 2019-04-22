package com.example.musicians;

import android.database.MatrixCursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;

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

    String event_uid;
    private static final String TAG = "ForumPage";

    private Button SendButton;

    private TextInputEditText EditMessage;

    private String username;

    private List<Message> messagelist;

    private MatrixCursor MessageCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_page);

        SendButton = findViewById(R.id.send_message);
        EditMessage = findViewById(R.id.edit_message);

        event_uid = getIntent().getStringExtra("event_uid");
        // TODO need to figure out how to point to a particular event's message database.


        final String user_uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final ListView messages_list = findViewById(R.id.messages_list);
        DocumentReference UserdocRef = db.collection("users").document(user_uid);
        UserdocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        User user = document.toObject(User.class);
                        username = user.getFirstname() + " " + user.getLastname() + ": ";
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        final DocumentReference docRef = db.collection("events").document(event_uid);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                MessageCursor= new MatrixCursor(new String[] {"_id", "username","message"});

                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Event data = snapshot.toObject(Event.class);
                    messagelist = data.getMessageList();
                    if (messagelist.isEmpty() == false) {
                        int id = 0;
                        for (Message message : messagelist) {
                            MessageCursor.newRow()
                                    .add("_id", id++)
                                    .add("username", message.username)
                                    .add("message", message.message);
                        }
                    }
                    MessageCursorAdapter MessageAdapter = new MessageCursorAdapter(ForumPage.this, MessageCursor);
                    // Attach cursor adapter to the messages_list
                    messages_list.setAdapter(MessageAdapter);

                    Log.d(TAG, "Current data: " + snapshot.getData());
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });

        // Find ListView to populate
        // Setup cursor adapter using cursor
        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = EditMessage.getText().toString();
                messagelist.add(new Message(username, message));
                db.collection("events").document(event_uid).update("messageList", messagelist);
                EditMessage.setText("");
            }
        });



    }

}