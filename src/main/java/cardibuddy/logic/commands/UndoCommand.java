package cardibuddy.logic.commands;

import static cardibuddy.commons.core.Messages.MESSAGE_TEST_ONGOING;
import static cardibuddy.commons.util.CollectionUtil.requireAllNonNull;

import cardibuddy.commons.core.Messages;
import cardibuddy.logic.CommandHistory;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;

/**
 * Reverts the {@code model}'s CardiBuddy to its previous state.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "New deck added: %1$s";

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireAllNonNull(model);
        String lastCommand = commandHistory.getHistory().get(0);
        if (model.hasOngoingTestSession()) {
            throw new CommandException(MESSAGE_TEST_ONGOING);
        }

        if (!model.canUndo()) {
            throw new CommandException(Messages.MESSAGE_NOTHING_TO_UNDO);
        }

        model.undo();
        model.updateFilteredDeckList(Model.PREDICATE_SHOW_ALL_DECKS);
        return new CommandResult(MESSAGE_SUCCESS + "\nUndone command: " + lastCommand);
    }
}
