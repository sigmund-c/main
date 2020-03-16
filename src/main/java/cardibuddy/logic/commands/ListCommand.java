package cardibuddy.logic.commands;

import static cardibuddy.model.Model.PREDICATE_SHOW_ALL_DECKS;
import static java.util.Objects.requireNonNull;

import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;

    /**
    * Lists all decks/flashcards in the directory to the user.
    */
    public class ListCommand extends Command {
        public static final String COMMAND_WORD = "list";

        public static final String MESSAGE_SUCCESS = "Listed all decks";

        @Override
        public CommandResult execute(Model model) throws CommandException {
            requireNonNull(model);
            model.updateFilteredDeckList(PREDICATE_SHOW_ALL_DECKS);
            return new CommandResult(MESSAGE_SUCCESS);
        }
}
