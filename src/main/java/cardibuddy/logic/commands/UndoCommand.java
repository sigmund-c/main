package cardibuddy.logic.commands;

<<<<<<< HEAD
import cardibuddy.commons.core.Messages;
import cardibuddy.logic.CardiBuddyStack;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;

import static cardibuddy.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Undo the last {@code UndoableCommand}.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes the last command by the user.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Undone last command: %1$s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        CardiBuddyStack cardiBuddyStack = CardiBuddyStack.getCardiBuddyStack();
        requireAllNonNull(model, cardiBuddyStack);

        if (!cardiBuddyStack.canUndo()) {
            throw new CommandException(Messages.MESSAGE_NOTHING_TO_UNDO);
        }

        cardiBuddyStack.popUndo().undo(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
