package cardibuddy.logic.commands;

import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;

/**
 * Deletes the Deck that corresponds with the index and the subsequent Flashcards.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
