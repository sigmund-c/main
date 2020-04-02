package cardibuddy.logic.commands;

import cardibuddy.commons.core.Messages;
import cardibuddy.logic.CardiBuddyStack;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;

import static cardibuddy.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Redo the last command that was undone.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redoes the last command by the user.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Redone last command: %1$s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        CardiBuddyStack cardiBuddyStack = CardiBuddyStack.getCardiBuddyStack();
        requireAllNonNull(model, cardiBuddyStack);

        if (!cardiBuddyStack.canRedo()) {
            throw new CommandException(Messages.MESSAGE_NOTHING_TO_REDO);
        }

        cardiBuddyStack.popRedo().redo(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
