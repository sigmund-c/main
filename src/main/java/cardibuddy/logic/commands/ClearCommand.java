package cardibuddy.logic.commands;

import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;

/**
 * Clears all decks and cards from the cardibuddy.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
