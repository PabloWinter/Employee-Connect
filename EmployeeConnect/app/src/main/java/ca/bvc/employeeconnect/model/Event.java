package ca.bvc.employeeconnect.model;

public class Event {

    public String nameEvent;
    public String noteEvent;
    public String eventStart;

    /**
     * Constructor
     * @param nameEvent
     * @param noteEvent
     * @param eventStart
     * @param eventEnd
     */
    public Event(String nameEvent, String noteEvent, String eventStart, String eventEnd) {
        this.nameEvent = nameEvent;
        this.noteEvent = noteEvent;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
    }

    public String eventEnd;

    /**
     * get for name Event
     * @return
     */
    public String getNameEvent() {
        return nameEvent;
    }

    /**
     * set for Event Name
     * @param nameEvent
     */
    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    /**
     * Get for Event Note
     * @return
     */
    public String getNoteEvent() {
        return noteEvent;
    }

    /**
     * set for Event Note
     * @param noteEvent
     */
    public void setNoteEvent(String noteEvent) {
        this.noteEvent = noteEvent;
    }

    /**
     * Get for Event Start Time
     * @return
     */
    public String getEventStart() {
        return eventStart;
    }

    /**
     * Set for Event Start TIme
     * @param eventStart
     */
    public void setEventStart(String eventStart) {
        this.eventStart = eventStart;
    }

    /**
     * Get for Event End
     * @return
     */
    public String getEventEnd() {
        return eventEnd;
    }

    /**
     * Set for Event End
     * @param eventEnd
     */
    public void setEventEnd(String eventEnd) {
        this.eventEnd = eventEnd;
    }
}
