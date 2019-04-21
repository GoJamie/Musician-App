package com.example.musicians;

import java.util.HashMap;
import java.util.List;

public class Event {//extends EventList {
    private String name;
    private String time;
    private String description;
    private List<User> participants;
    private User owner;
    private String city;
    private String address;
    private List<Message> messageList;

    public Event() {}

    public Event(String name, String description, String city, String address, String time, List<User> participants, User owner, List<Message> messagelist) {
        this.name = name; // The name of the event
        this.city = city; // The city of the event
        this.address = address; // The address of the event
        this.time = time; // The time of the event
        this.participants = participants; // The number of participants to the event
        this.owner = owner; // The person who has created the event
        this.messageList = messagelist; // The list of message object
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public User getOwner() {
        return owner;
    }

    public String  getCity() {
        return city;
    }

    public String  getAddress() {
        return address;
    }

    public List<Message> getMessageList() {
        return messageList;
    }
}