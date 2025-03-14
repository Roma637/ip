import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDateTime start;
    private LocalDateTime end;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");

    // For compatibility with existing data
    private String startString;
    private String endString;

    public Event(String description, String startStr, String endStr) {
        super(description);
        this.startString = startStr;
        this.endString = endStr;
        this.start = parseDateTime(startStr);
        this.end = parseDateTime(endStr);
        this.taskType = "E";
    }

    private LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            return LocalDateTime.parse(dateTimeStr, INPUT_FORMATTER);
        } catch (Exception e) {
            // If parsing fails, keep the original string but return null DateTime
            System.out.println("Warning: Could not parse date '" + dateTimeStr + "'. Using original string.");
            return null;
        }
    }

    @Override
    public String toString() {
        String formattedStart = (start != null) ?
                start.format(OUTPUT_FORMATTER) :
                startString; // Fall back to original string

        String formattedEnd = (end != null) ?
                end.format(OUTPUT_FORMATTER) :
                endString; // Fall back to original string

        return super.toString() + " (from: " + formattedStart + " to: " + formattedEnd + ")";
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public String getStartString() {
        return startString;
    }

    public void setStartString(String startStr) {
        this.startString = startStr;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getEndString() {
        return endString;
    }

    public void setEndString(String endStr) {
        this.endString = endStr;
    }
}