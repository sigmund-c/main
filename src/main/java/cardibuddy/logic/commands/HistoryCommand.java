package cardibuddy.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;

import cardibuddy.logic.CommandHistory;
import cardibuddy.model.Model;

/**
 * Shows the history of exceptions.
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";

    public static final String MESSAGE_SUCCESS = "Displayed command history:\n%1$s";

    public static final String MESSAGE_NO_HISTORY = "You have not yet entered any exceptions.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        ArrayList<String> previousCommands = new ArrayList<>(history.getHistory());

        if (previousCommands.isEmpty()) {
            return new CommandResult(MESSAGE_NO_HISTORY);
        }

        Collections.reverse(previousCommands);
        return new CommandResult(String.format(MESSAGE_SUCCESS, String.join("\n", previousCommands)));
    }
}
