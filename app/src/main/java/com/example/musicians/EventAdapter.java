package com.example.musicians;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        CardView event_card;
        TextView EventName;
        TextView EventLocationTitle;
        TextView EventLocation;
        TextView EventTime;
        TextView EventTimeTitle;
        TextView EventParticipantsAmount;


        EventViewHolder(View eventView) {
            super(eventView);
            event_card = eventView.findViewById(R.id.event_card);
            EventName = eventView.findViewById(R.id.event_name);
            EventParticipantsAmount = eventView.findViewById(R.id.event_participants_amount);
            EventLocationTitle = eventView.findViewById(R.id.event_location_title);
            EventLocation = eventView.findViewById(R.id.event_location);
            EventTime = eventView.findViewById(R.id.event_time);
            EventTimeTitle = eventView.findViewById(R.id.event_time_title);
        }
    }

    List<Event> Events;

    EventAdapter(List<Event> Events){
        this.Events = Events;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View event_view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_template, viewGroup, false);
        EventViewHolder pvh = new EventViewHolder(event_view);
        return pvh;
    }

    @Override
    public void onBindViewHolder(EventViewHolder EventViewHolder, int i) {
        EventViewHolder.EventName.setText(Events.get(i).getName());
        EventViewHolder.EventParticipantsAmount.setText(String.valueOf(Events.get(i).getParticipants()));
        EventViewHolder.EventLocation.setText(Events.get(i).getCity());
        EventViewHolder.EventTime.setText(Events.get(i).getTime());

    }

    @Override
    public int getItemCount() {
        return Events.size();
    }
}
