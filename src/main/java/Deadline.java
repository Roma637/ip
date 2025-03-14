import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDateTime deadline;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");

    public Deadline(String description, String deadlineStr) {
        super(description);
        this.deadline = parseDeadline(deadlineStr);
        this.taskType = "D";
    }

    private LocalDateTime parseDeadline(String deadlineStr) {
        try {
            return LocalDateTime.parse(deadlineStr, INPUT_FORMATTER);
        } catch (Exception e) {
            // If parsing fails, return a default date
            System.out.println("Warning: Could not parse date '" + deadlineStr + "'. Using original string.");
            return null;
        }
    }

    @Override
    public String toString() {
        String formattedDeadline = (deadline != null) ?
                deadline.format(OUTPUT_FORMATTER) :
                getDeadlineString(); // Fall back to original string
        return super.toString() + " (by: " + formattedDeadline + ")";
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    // For compatibility with existing data
    private String deadlineString;

    public String getDeadlineString() {
        return deadlineString;
    }

    public void setDeadlineString(String deadlineStr) {
        this.deadlineString = deadlineStr;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}