package com.musicians;

import java.util.Date;

public class event_information extends Event{
    String name;
    Date time;
    String location;
    int participants;
    String owner;
    String city;
    String address;

    event_information(String name, String location, String city, String address, Date time, int participants, String owner) {
        this.name = name; // The name of the event
        this.location = location; // The location of the event
        this.city = city; // The city of the event
        this.address = address; // The address of the event
        this.time = time; // The time of the event
        this.participants = participants; // The number of participants to the event
        this.owner = owner; // The person who has created the event
    }
}