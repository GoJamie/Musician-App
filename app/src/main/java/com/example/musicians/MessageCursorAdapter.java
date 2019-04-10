package com.example.musicians;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

// https://guides.codepath.com/android/Populating-a-ListView-with-a-CursorAdapter

public class MessageCursorAdapter extends CursorAdapter {
    public MessageCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.message_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // message_username is the field which displays the username of the person who sent the message.
        //message_body contains the actual message.
        TextView message_username = (TextView) view.findViewById(R.id.message_username);
        TextView message_body = (TextView) view.findViewById(R.id.message_body);
        // Extract properties from cursor
        String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
        String message = cursor.getString(cursor.getColumnIndexOrThrow("message"));
        // Populate fields with extracted properties
        message_username.setText(username);
        message_body.setText(message);
    }
}

