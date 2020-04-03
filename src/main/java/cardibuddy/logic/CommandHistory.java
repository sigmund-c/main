package cardibuddy.logic;

import static java.util.Objects.requireNonNull;

import cardibuddy.commons.core.Messages;
import cardibuddy.logic.commands.exceptions.CommandException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents the history of commands.
 */
public class CommandHistory {

    private static final CommandHistory commandHistory = new CommandHistory();

    private final ObservableList<String> history = FXCollections.observableArrayList();
    private final ObservableList<String> unmodifiableHistory =
            FXCollections.unmodifiableObservableList(history);

    private CommandHistory() {

    }

    public CommandHistory(CommandHistory commandHistory) {
        setList(commandHistory);
    }

    /**
     * Adds an {code commandString} string to the list.
     */
    public void add(String commandString) {
        requireNonNull(commandString);
        history.add(commandString);
    }

    public static CommandHistory getCommandHistory() {
        return commandHistory;
    }

    /**
     * Removes the equivalent string from the list.
     * The string must exist in the list.
     */
    public void remove(String commandString) throws CommandException {
        requireNonNull(commandString);
        if (!history.remove(commandString)) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

    public void setList(CommandHistory replacement) {
        requireNonNull(replacement);
        history.setAll(replacement.history);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<String> getCommandHistoryList() {
        return unmodifiableHistoryList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CommandHistory // instanceof handles nulls
                && historyList.equals(((CommandHistory) other).historyList));
    }

    @Override
    public int hashCode() {
        return historyList.hashCode();
    }
}
