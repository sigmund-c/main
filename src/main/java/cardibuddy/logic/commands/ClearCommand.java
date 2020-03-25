package cardibuddy.logic.commands;
import static java.util.Objects.requireNonNull;

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
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.setCardiBuddy(new CardiBuddy());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
