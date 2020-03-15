package cardibuddy.logic.commands;

import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;

/**
 * Lists the Decks in the cardibuddy.
 */
public class ListCommand extends Command {
    public static final String COMMAND_WORD = "list";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
