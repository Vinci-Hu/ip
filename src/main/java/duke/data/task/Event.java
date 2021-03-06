package duke.data.task;

/**
 * Represents an Event in the task list.
 * Guarantees: Has an at/ field indicating the venue argument
 */
public class Event extends Task {

    protected String at;

    public Event(String description, String at) {
        super(description);
        this.at = at;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + at + ")";
    }

    @Override
    public String toText(int number) {
        return "E|" + number + "|" + description + "|" + at;
    }
}
