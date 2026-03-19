package fitlogger.workout;

import java.time.LocalDate;

/**
 * Represents an abstract FitLogger.command.workout in the FitLogger.command.FitLogger application.
 * This class serves as a base for specific FitLogger.command.workout types and contains
 * shared logic for tracking completion status and dates.
 */
public abstract class Workout {

    /** The description or name of the FitLogger.command.workout activity. */
    protected String description;

    /** The date when the FitLogger.command.workout was performed or is scheduled for. */
    protected LocalDate date;

    /** Tracks whether the FitLogger.command.workout has been completed. */
    protected boolean isDone;

    /**
     * Initializes a new Workout with a description and date.
     * The initial status of the FitLogger.command.workout is set to not done.
     *
     * @param description A short summary of the FitLogger.command.workout.
     * @param date        The date of the FitLogger.command.workout.
     */
    public Workout(String description, LocalDate date) {
        this.description = description;
        this.date = date;
        this.isDone = false;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public boolean getDoneStatus() {
        return isDone;
    }

    /**
     * Returns a visual indicator of the FitLogger.command.workout's completion status.
     *
     * @return "[X]" if completed, "[ ]" otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]");
    }

    /**
     * Updates the FitLogger.command.workout status to completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Updates the FitLogger.command.workout status to incomplete.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Formats the FitLogger.command.workout data into a standardized string for file storage.
     * Each child class must implement this to ensure its specific data
     * (e.g., distance for runs) is saved correctly.
     *
     * @return A pipe-separated string representing the FitLogger.command.workout's data.
     */
    public abstract String toFileFormat();

    /**
     * Returns a string representation of the FitLogger.command.workout for display to the user.
     *
     * @return A formatted string containing status, description, and date.
     */
    @Override
    public String toString() {
        return getStatusIcon() + " " + description + " (Date: " + date + ")";
    }
}
