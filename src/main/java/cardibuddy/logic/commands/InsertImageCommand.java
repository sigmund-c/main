package cardibuddy.logic.commands;

import static cardibuddy.logic.parser.CliSyntax.PREFIX_ANSWER;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_FLASHCARD;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_QUESTION;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_TAG;
import static cardibuddy.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;
import static java.util.Objects.requireNonNull;

import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.flashcard.Imagecard;

/**
 * Adds a Flashcard to the specified deck.
 */
public class InsertImageCommand extends AddCardCommand {

    public static final String COMMAND_WORD = "p";

    public static final String MESSAGE_USAGE = "add c/: Adds a flashcard to a deck. \n"
            + "Parameters: "
            + PREFIX_FLASHCARD + "DECK_INDEX "
            + PREFIX_QUESTION + "QUESTION "
            + PREFIX_ANSWER + "ANSWER "
            + "[" + PREFIX_TAG + "TAG]... \n"
            + "Example (adding a flashcard): " + COMMAND_WORD + " "
            + PREFIX_FLASHCARD + "1 "
            + PREFIX_QUESTION + "A queue cannot be implemented using an array "
            + PREFIX_ANSWER + "False ";

    public static final String MESSAGE_ADD_FLASHCARD = "To add a flashcard to the cardibuddy book. \n"
            + "Parameters: "
            + PREFIX_FLASHCARD + "DECK_INDEX "
            + PREFIX_QUESTION + "QUESTION "
            + PREFIX_ANSWER + "ANSWER"
            + "[" + PREFIX_TAG + "TAG]... \n"
            + "Example: add "
            + PREFIX_FLASHCARD + "/ Java "
            + PREFIX_QUESTION + "A queue cannot be implemented using an array "
            + PREFIX_ANSWER + "False "
            + PREFIX_TAG + "Programming";

    public static final String MESSAGE_SUCCESS = "New flashcard added: %1$s";
    public static final String MESSAGE_DUPLICATE_FLASHCARD = "This flashcard already exists in the deck";

    private final Imagecard toAdd;

    private LogicToUiManager logicToUiManager;

    public InsertImageCommand(Imagecard imagecard, LogicToUiManager logicToUiManager) {
        this.logicToUiManager = logicToUiManager;
        requireNonNull(imagecard);
        toAdd = imagecard;
    }

    @Override
    public CommandResult executeUndoableCommand(Model model) throws CommandException {
        requireNonNull(model);

        if (logicToUiManager.getDisplayedDeck().hasFlashcard(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FLASHCARD);
        }

        logicToUiManager.getDisplayedDeck().addCard(toAdd);
        LOGGER.info("Flashcard has been added");

        logicToUiManager.getDisplayedDeck().updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
        logicToUiManager.updateFlashcardPanel();

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), false, false, false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InsertImageCommand // instanceof handles nulls
                && toAdd.equals(((InsertImageCommand) other).toAdd));
    }
}
