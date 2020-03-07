package cardibuddy.logic.commands.flashcard;

import cardibuddy.logic.commands.Command;
import cardibuddy.logic.commands.CommandResult;
import cardibuddy.model.Model;

import static java.util.Objects.requireNonNull;
import static cardibuddy.model.Model.PREDICATE_SHOW_ALL_CARDS;

/**
 * Lists all cards in the deck to the user.
 */
public class ListCardCommand extends Command {

    public static final String COMMAND_WORD = "list cards";

    public static final String MESSAGE_SUCCESS = "Listed all cards";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
