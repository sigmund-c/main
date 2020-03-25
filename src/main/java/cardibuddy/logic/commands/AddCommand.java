package cardibuddy.logic.commands;

import static cardibuddy.logic.parser.CliSyntax.PREFIX_ANSWER;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_DECK;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_FLASHCARD;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_QUESTION;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import cardibuddy.commons.core.LogsCenter;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Flashcard;

/**
 * Adds a deck to the cardibuddy.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a deck/flashcard to the cardibuddy book. \n"
            + "Parameters: "
            + PREFIX_DECK + "DECK_TITLE "
            + "[" + PREFIX_TAG + "TAG]... "
            + "|| "
            + PREFIX_FLASHCARD + "DECK_TITLE "
            + PREFIX_QUESTION + "QUESTION "
            + PREFIX_ANSWER + "ANSWER "
            + "[" + PREFIX_TAG + "TAG]... \n"
            + "Example (adding a deck): " + COMMAND_WORD + " "
            + PREFIX_DECK + "cs2103t "
            + PREFIX_TAG + "Hard "
            + PREFIX_TAG + "Software Engineering \n"
            + "Example (adding a flashcard): " + COMMAND_WORD + " "
            + PREFIX_FLASHCARD + "Java "
            + PREFIX_QUESTION + "A queue cannot be implemented using an array "
            + PREFIX_ANSWER + "False "
            + PREFIX_TAG + "Programming";

    public static final String MESSAGE_ADD_DECK = "To add a deck to the cardibuddy book. \n"
            + "Parameters: "
            + PREFIX_DECK + "DECK_TITLE "
            // + PREFIX_TITLE + "Title \n"
            + "[" + PREFIX_TAG + "TAG]... \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DECK + "cs2103t "
            + PREFIX_TAG + "Hard "
            + PREFIX_TAG + "Software Engineering";

    public static final String MESSAGE_ADD_FLASHCARD = "To add a flashcard to the cardibuddy book. \n"
            + "Parameters: "
            + PREFIX_FLASHCARD + "DECK_TITLE "
            + PREFIX_QUESTION + "QUESTION "
            + PREFIX_ANSWER + "ANSWER"
            + "[" + PREFIX_TAG + "TAG]... \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FLASHCARD + "Java "
            + PREFIX_QUESTION + "A queue cannot be implemented using an array "
            + PREFIX_ANSWER + "False "
            + PREFIX_TAG + "Programming";

    public static final String MESSAGE_SUCCESS = "New object added: %1$s";
    public static final String MESSAGE_DUPLICATE_DECK = "This deck already exists in the cardibuddy library";
    public static final String MESSAGE_DUPLICATE_FLASHCARD = "This flashcard already exists in the deck";

    private static final Logger logger = LogsCenter.getLogger(AddCommand.class);
    private static List<Flashcard> flashcards = new ArrayList<>();

    private final Object toAdd;
    private final Boolean isDeck;
    /**
     * Creates an AddCommand to add the specified {@code Deck}
     */
    public AddCommand(Deck deck) {
        requireNonNull(deck);
        toAdd = deck;
        isDeck = true;
        flashcards = deck.getFlashcards();
    }

    public AddCommand(Flashcard flashcard) {
        requireNonNull(flashcard);
        toAdd = flashcard;
        isDeck = false;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (isDeck) {
            if (model.hasDeck((Deck) toAdd)) {
                throw new CommandException(MESSAGE_DUPLICATE_DECK);
            }
            model.addDeck((Deck) toAdd);

            logger.info("Deck has been added");
        } else {
            if (model.hasFlashcard((Flashcard) toAdd)) {
                throw new CommandException(MESSAGE_DUPLICATE_FLASHCARD);
            }
            model.addFlashcard((Flashcard) toAdd);
            flashcards.add((Flashcard) toAdd);
            logger.info("Flashcard has been added");
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
