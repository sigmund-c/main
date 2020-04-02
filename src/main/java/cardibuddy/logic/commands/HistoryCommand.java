package cardibuddy.logic.commands;

import cardibuddy.logic.CommandHistory;
import cardibuddy.model.Model;

import java.util.Collections;
import java.util.LinkedList;

import static java.util.Objects.requireNonNull;

/**
 * Shows the history of commands.
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the user's history.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Displayed command history: \n%1$s";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        LinkedList<String> commandList = new LinkedList<>(CommandHistory.getCommandHistory().getCommandHistoryList());

        Collections.reverse(commandList);

        return new CommandResult(String.format(MESSAGE_SUCCESS, commandList));
    }
}
