package cardibuddy.logic.commands;
import static cardibuddy.commons.core.Messages.MESSAGE_TEST_ONGOING;
import static java.util.Objects.requireNonNull;

import cardibuddy.logic.CommandHistory;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.CardiBuddy;
import cardibuddy.model.Model;

/**
 * Clears all decks and cards from the cardibuddy.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_SUCCESS = "Cardibuddy has been cleared";

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        if (model.hasOngoingTestSession()) {
            throw new CommandException(MESSAGE_TEST_ONGOING);
        }
        requireNonNull(model);
        model.setCardiBuddy(new CardiBuddy());
        model.commitCardiBuddy();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
