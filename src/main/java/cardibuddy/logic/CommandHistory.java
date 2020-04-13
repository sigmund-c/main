package cardibuddy.logic;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents the history of exceptions.
 */
public class CommandHistory {

    private static final CommandHistory commandHistory = new CommandHistory();

    private final ObservableList<String> history = FXCollections.observableArrayList();
    private final ObservableList<String> unmodifiableHistory =
            FXCollections.unmodifiableObservableList(history);

    public CommandHistory() {}

    public CommandHistory(CommandHistory commandHistory) {
        history.addAll(commandHistory.history);
    }

    /**
     * Adds an {code commandString} string to the list.
     */
    public void add(String commandString) {
        requireNonNull(commandString);
        history.add(commandString);
    }

    /**
     * Returns an unmodifiable view of {@code userInputHistory}.
     */
    public ObservableList<String> getHistory() {
        return unmodifiableHistory;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof CommandHistory)) {
            return false;
        }

        // state check
        CommandHistory other = (CommandHistory) obj;
        return history.equals(other.history);
    }

    @Override
    public int hashCode() {
        return history.hashCode();
    }
}
