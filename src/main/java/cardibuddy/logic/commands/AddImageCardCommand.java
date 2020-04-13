package cardibuddy.logic.commands;

import static cardibuddy.logic.parser.CliSyntax.PREFIX_ANSWER;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_PATH;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_QUESTION;
import static cardibuddy.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;
import static java.util.Objects.requireNonNull;

import cardibuddy.logic.CommandHistory;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.flashcard.Imagecard;

/**
 * Adds a Flashcard to the specified deck.
 */
public class AddImageCardCommand extends AddCardCommand {

    public static final String COMMAND_WORD = "p";

    public static final String MESSAGE_USAGE = "add p/ q/ a/: Adds an imagecard to a deck. \n"
            + "Parameters: "
            + PREFIX_PATH + "IMAGE_FILEPATH "
            + PREFIX_QUESTION + "QUESTION "
            + PREFIX_ANSWER + "ANSWER "
            + "Example (adding an imagecard): " + COMMAND_WORD + "/ "
            + PREFIX_QUESTION + "A queue cannot be implemented using an array "
            + PREFIX_ANSWER + "F ";

    public static final String MESSAGE_SUCCESS = "New flashcard added: %1$s";
    public static final String MESSAGE_DUPLICATE_FLASHCARD = "This flashcard already exists in the deck";

    private final Imagecard toAdd;

    private LogicToUiManager logicToUiManager;

    public AddImageCardCommand(Imagecard imagecard, LogicToUiManager logicToUiManager) {
        this.logicToUiManager = logicToUiManager;
        requireNonNull(imagecard);
        toAdd = imagecard;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);

        if (logicToUiManager.getDisplayedDeck().hasFlashcard(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FLASHCARD);
        }

        logicToUiManager.getDisplayedDeck().addCard(toAdd);
        LOGGER.info("Flashcard has been added");

        logicToUiManager.getDisplayedDeck().updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
        logicToUiManager.updateFlashcardPanel();
        model.commitCardiBuddy();

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), false, false, false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddImageCardCommand // instanceof handles nulls
                && toAdd.equals(((AddImageCardCommand) other).toAdd));
    }
}
