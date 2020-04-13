package cardibuddy.logic.commands;

import static cardibuddy.logic.parser.CliSyntax.PREFIX_ANSWER;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_QUESTION;
import static cardibuddy.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;
import static java.util.Objects.requireNonNull;

import cardibuddy.logic.CommandHistory;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.flashcard.Flashcard;

/**
 * Adds a Flashcard (without image) to a deck.
 */
public class AddFlashcardCommand extends AddCardCommand {
    public static final String COMMAND_WORD = "q";

    public static final String MESSAGE_USAGE = "add q/ a/: Adds a flashcard to a deck. \n"
            + "Parameters: "
            + PREFIX_QUESTION + "QUESTION "
            + PREFIX_ANSWER + "ANSWER "
            + "Example (adding a flashcard): " + COMMAND_WORD + "/ "
            + "A queue cannot be implemented using an array "
            + PREFIX_ANSWER + "F ";

    public static final String MESSAGE_SUCCESS = "New flashcard added:\n%1$s";
    public static final String MESSAGE_DUPLICATE_FLASHCARD = "This flashcard already exists in the deck";

    private final Flashcard toAdd;

    private LogicToUiManager logicToUiManager;

    public AddFlashcardCommand(Flashcard flashcard, LogicToUiManager logicToUiManager) {
        this.logicToUiManager = logicToUiManager;
        requireNonNull(flashcard);
        toAdd = flashcard;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);

        if (logicToUiManager.getDisplayedDeck().hasFlashcard(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FLASHCARD);
        }

        model.addFlashcard(toAdd);
        model.commitCardiBuddy();
        logicToUiManager.getDisplayedDeck().addCard(toAdd);
        LOGGER.info("Flashcard has been added");

        logicToUiManager.getDisplayedDeck().updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
        logicToUiManager.updateFlashcardPanel();

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddFlashcardCommand // instanceof handles nulls
                && toAdd.equals(((AddFlashcardCommand) other).toAdd));
    }

}
