package cardibuddy.logic.commands;

import cardibuddy.logic.CommandHistory;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;

/**
 * Represents an add command to be extended into add deck and add card commands.
 */
public abstract class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits a deck or flashcard identified by the index number used in the displayed cardibuddy book.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " deck 1 \n"
            + "Example: " + COMMAND_WORD + " card 1 \n";

    public abstract boolean equals(Object other);
}
