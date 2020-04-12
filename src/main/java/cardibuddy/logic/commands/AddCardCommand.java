package cardibuddy.logic.commands;

import cardibuddy.logic.CommandHistory;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;

/**
 * Adds a Flashcard to the specified deck.
 */
public abstract class AddCardCommand extends AddCommand {
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        return null;
    }
}
