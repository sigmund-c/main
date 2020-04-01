package cardibuddy.logic.commands;

import cardibuddy.commons.core.LogsCenter;
import cardibuddy.logic.CommandHistory;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;

import java.util.logging.Logger;

import static cardibuddy.commons.core.Messages.MESSAGE_NOTHING_TO_UNDO;
import static java.util.Objects.requireNonNull;

public class UndoCommand extends Command{
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undoes the last command by the user. Can be a deck or flashcard command.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_UNDO_SUCCESS = "Undone last command.";

    private static final Logger logger = LogsCenter.getLogger(AddCommand.class);

    private CommandHistory commandHistory;

    public UndoCommand(CommandHistory commandHistory) {
        this.commandHistory = commandHistory;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.canUndo(commandHistory)) {
            throw new CommandException(MESSAGE_NOTHING_TO_UNDO);
        }

        UndoableCommand lastCommand = commandHistory.popUndoCommand();
        lastCommand.reverse(model);

        logger.config("Undoing.");

        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UndoCommand); // instanceof handles nulls
    }
}
