package fitlogger.command;

import fitlogger.ui.Ui;
import fitlogger.workout.Workout;
import fitlogger.workoutlist.WorkoutList;

/**
 * Deletes a FitLogger.command.workout from the in-memory FitLogger.command.workout list by name or by user-facing index.
 *
 * <p>Name matching is case-insensitive and compares against the full FitLogger.command.workout name.
 * Index matching uses one-based user input (e.g., "3") and maps it to
 * zero-based internal indexing.</p>
 */
public class DeleteCommand extends Command {
    /** The FitLogger.command.workout list managed by the application. */
    private final WorkoutList workouts;

    /** The FitLogger.command.workout name provided by the user for deletion. */
    private final String workoutName;

    /**
     * Creates a delete command with the target FitLogger.command.workout list and FitLogger.command.workout name.
     *
     * @param workouts The FitLogger.command.workout list containing existing workouts.
     * @param workoutName The FitLogger.command.workout name to delete.
     */
    public DeleteCommand(WorkoutList workouts, String workoutName) {
        this.workouts = workouts;
        this.workoutName = workoutName;
    }

    /**
     * Executes the delete operation and prints feedback to the user.
     *
     * <p>If no FitLogger.command.workout name is provided, a usage hint is shown. If a matching FitLogger.command.workout
     * exists, it is removed from the list; otherwise, a not-found message is shown.</p>
     *
     * @param ui UI component used to display command results.
     */
    @Override
    public void execute(Ui ui) {
        if (workoutName == null || workoutName.isBlank()) {
            ui.temporaryOutput("Please specify a FitLogger.command.workout to delete. "
                    + "Usage: delete FitLogger.command.workout <WORKOUT_NAME> or delete <index>");
            return;
        }

        String normalizedName = workoutName.trim();
        int indexToDelete = findWorkoutIndex(normalizedName);

        if (indexToDelete != -1) {
            String deletedWorkoutName = workouts.getWorkout(indexToDelete).getDescription();
            workouts.deleteWorkout(indexToDelete);
            ui.temporaryOutput("Deleted FitLogger.command.workout: " + deletedWorkoutName);
            return;
        }

        ui.temporaryOutput("Workout not found: " + normalizedName);
    }

    /**
     * Resolves the FitLogger.command.workout to delete from user input.
     *
     * <p>The input is first treated as a one-based index (e.g., {@code 1} maps to
     * internal index {@code 0}). If it is not a valid integer, the input is treated
     * as a FitLogger.command.workout name and matched case-insensitively.</p>
     *
     * @param input Raw user-provided value after the delete command.
     * @return The zero-based FitLogger.command.workout index if a match is found; {@code -1} otherwise.
     */
    private int findWorkoutIndex(String input) {
        Integer oneBasedIndex = parseUserProvidedIndex(input);
        if (oneBasedIndex != null) {
            int zeroBasedIndex = oneBasedIndex - 1;
            if (zeroBasedIndex >= 0 && zeroBasedIndex < workouts.getSize()) {
                return zeroBasedIndex;
            }
            return -1;
        }

        return findWorkoutIndexByName(input);
    }

    /**
     * Parses a one-based FitLogger.command.workout index from user input.
     *
     * @param input Raw user input that may represent an integer index.
     * @return Parsed one-based index, or {@code null} if input is not numeric.
     */
    private Integer parseUserProvidedIndex(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    /**
     * Finds a FitLogger.command.workout by exact description match (case-insensitive).
     *
     * @param workoutNameToFind The FitLogger.command.workout name to search for.
     * @return The zero-based index of the first matching FitLogger.command.workout, or {@code -1} if none matches.
     */
    private int findWorkoutIndexByName(String workoutNameToFind) {
        for (int i = 0; i < workouts.getSize(); i++) {
            Workout workout = workouts.getWorkout(i);
            if (workout.getDescription().equalsIgnoreCase(workoutNameToFind)) {
                return i;
            }
        }
        return -1;
    }
}

