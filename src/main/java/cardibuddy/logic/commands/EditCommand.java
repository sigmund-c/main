package cardibuddy.logic.commands;

/**
 * Represents an add command to be extended into add deck and add card commands.
 */
public abstract class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits a deck or flashcard identified by the index number used in the displayed cardibuddy book.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " ";

    public abstract boolean equals(Object other);
}
