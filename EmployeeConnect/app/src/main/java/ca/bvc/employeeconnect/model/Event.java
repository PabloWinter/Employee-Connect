package ca.bvc.employeeconnect.model;

public class Event {

    public String nameEvent;
    public String noteEvent;
    public String eventStart;

    public Event(String nameEvent, String noteEvent, String eventStart, String eventEnd) {
        this.nameEvent = nameEvent;
        this.noteEvent = noteEvent;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
    }

    public String eventEnd;

    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public String getNoteEvent() {
        return noteEvent;
    }

    public void setNoteEvent(String noteEvent) {
        this.noteEvent = noteEvent;
    }

    public String getEventStart() {
        return eventStart;
    }

    public void setEventStart(String eventStart) {
        this.eventStart = eventStart;
    }

    public String getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(String eventEnd) {
        this.eventEnd = eventEnd;
    }
}
