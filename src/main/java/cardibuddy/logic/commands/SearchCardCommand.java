package cardibuddy.logic.commands;

import static cardibuddy.commons.core.Messages.MESSAGE_NOT_IN_DECK;
import static cardibuddy.commons.core.Messages.MESSAGE_TEST_ONGOING;
import static java.util.Objects.requireNonNull;

import cardibuddy.commons.core.Messages;
import cardibuddy.logic.CommandHistory;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.deck.exceptions.NotInDeckException;
import cardibuddy.model.flashcard.SearchCardKeywordsPredicate;


/**
 * Finds and lists all decks and cards in cardibuddy whose title contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchCardCommand extends SearchCommand {

    public static final String COMMAND_WORD = "card";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all decks whose titles contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " java";

    private final SearchCardKeywordsPredicate predicate;

    private LogicToUiManager logicToUiManager;

    public SearchCardCommand(SearchCardKeywordsPredicate predicate, LogicToUiManager logicToUiManager) {
        this.predicate = predicate;
        this.logicToUiManager = logicToUiManager;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws RuntimeException, CommandException {
        requireNonNull(model);
        if (model.hasOngoingTestSession()) {
            throw new CommandException(MESSAGE_TEST_ONGOING);
        }
        assert true;

        if (!logicToUiManager.isInDeck()) {
            throw new NotInDeckException(String.format(MESSAGE_NOT_IN_DECK
                    + " You need to open a deck first. \n" + OpenCommand.MESSAGE_USAGE));
        }

        logicToUiManager.getDisplayedDeck().updateFilteredFlashcardList(predicate);
        logicToUiManager.updateFlashcardPanel();
        return new CommandResult(
                String.format(Messages.MESSAGE_FLASHCARDS_LISTED_OVERVIEW,
                        logicToUiManager.getDisplayedDeck().getFilteredFlashcardList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchCardCommand // instanceof handles nulls
                && predicate.equals(((SearchCardCommand) other).predicate)); // state check
    }


}

