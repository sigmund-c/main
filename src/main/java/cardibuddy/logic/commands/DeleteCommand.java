package cardibuddy.logic.commands;

/**
 * Represents a delete command to be extended into search deck and search card exceptions.
 */
public abstract class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD

            + ": Deletes a deck or flashcard identified by the index number used in the displayed cardibuddy book.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " deck 1 \n"
            + "Example: " + COMMAND_WORD + " card 1 \n";

    public abstract boolean equals(Object other);

}
