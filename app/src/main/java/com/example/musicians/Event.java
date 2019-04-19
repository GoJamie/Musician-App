package com.example.musicians;

public class Event extends EventList {
    String name;
    String time;
    int participants;
    String owner;
    String city;
    String address;

    Event(String name, String city, String address, String time, int participants, String owner) {
        this.name = name; // The name of the event
        this.city = city; // The city of the event
        this.address = address; // The address of the event
        this.time = time; // The time of the event
        this.participants = participants; // The number of participants to the event
        this.owner = owner; // The person who has created the event
    }
}