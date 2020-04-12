package cardibuddy.logic.commands;

import static cardibuddy.commons.core.Messages.MESSAGE_TEST_ONGOING;
import static java.util.Objects.requireNonNull;

import cardibuddy.commons.core.Messages;
import cardibuddy.logic.CommandHistory;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.deck.SearchDeckKeywordsPredicate;


/**
 * Finds and lists all decks and cards in cardibuddy whose title contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchDeckCommand extends SearchCommand {

    public static final String COMMAND_WORD = "deck";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all decks whose titles contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " java";

    private final SearchDeckKeywordsPredicate predicate;

    public SearchDeckCommand(SearchDeckKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        if (model.hasOngoingTestSession()) {
            throw new CommandException(MESSAGE_TEST_ONGOING);
        }
        model.updateFilteredDeckList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_DECKS_LISTED_OVERVIEW, model.getFilteredDeckList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchDeckCommand // instanceof handles nulls
                && predicate.equals(((SearchDeckCommand) other).predicate)); // state check
    }


}
