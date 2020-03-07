package cardibuddy.logic.commands.deck;

import cardibuddy.logic.commands.Command;
import cardibuddy.logic.commands.CommandResult;
import cardibuddy.model.Model;

import static java.util.Objects.requireNonNull;
//import static cardibuddy.model.Model.PREDICATE_SHOW_ALL_DECKS;

/**
 * Lists all decks in the directory to the user.
 */
public class ListDeckCommand extends Command {

    public static final String COMMAND_WORD = "list decks";

    public static final String MESSAGE_SUCCESS = "Listed all decks";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
//        model.updateFilteredDeckList(PREDICATE_SHOW_ALL_DECKS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
