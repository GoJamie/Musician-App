package com.example.musicians;

public class Message {
    String username;
    String message;


    message_structure(String username, String message) {
        this.username = username; // The username of the event.
        this.message = message; // The message body: actual message.
    }
}