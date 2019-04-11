package ca.bvc.employeeconnect.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ca.bvc.employeeconnect.R;
import ca.bvc.employeeconnect.model.Event;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventListHolder> {

    private Context context;
    private ArrayList<Event> listEvents;

    public EventListAdapter(Context context, ArrayList<Event> events) {
        this.context = context;
        this.listEvents = events;
    }

    @NonNull
    @Override
    public EventListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_items, parent, false);
        EventListHolder holder = new EventListHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventListHolder eventListHolder, final int position) {
        eventListHolder.eventName .setText(listEvents.get(position).getNameEvent());
        eventListHolder.eventNotes.setText(listEvents.get(position).getNoteEvent());
        eventListHolder.eventStart.setText(listEvents.get(position).getEventEnd());
        eventListHolder.eventEnd.setText(listEvents.get(position).getEventEnd());
    }

    @Override
    public int getItemCount() {
        return listEvents.size();
    }

    public class EventListHolder extends RecyclerView.ViewHolder {
        TextView eventName;
        TextView eventNotes;
        TextView eventStart;
        TextView eventEnd;

        public EventListHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.event_name);
            eventNotes = itemView.findViewById(R.id.event_note);
            eventStart = itemView.findViewById(R.id.event_start_shift);
            eventEnd = itemView.findViewById(R.id.event_end_shift);
        }
    }
}
