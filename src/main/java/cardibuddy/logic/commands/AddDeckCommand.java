package cardibuddy.logic.commands;

import static cardibuddy.commons.core.Messages.MESSAGE_TEST_ONGOING;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_DECK;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import cardibuddy.logic.CommandHistory;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Card;

/**
 * Adds a deck to the cardibuddy storage.
 */
public class AddDeckCommand extends AddCommand {

    public static final String COMMAND_WORD = "d";

    public static final String MESSAGE_USAGE = "add d/: Adds a deck to the cardibuddy book. \n"
            + "Parameters: "
            + PREFIX_DECK + "DECK_TITLE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example (adding a deck): " + COMMAND_WORD + " "
            + PREFIX_DECK + "CS2103T "
            + PREFIX_TAG + "Hard "
            + PREFIX_TAG + "Software Engineering";

    public static final String MESSAGE_SUCCESS = "New deck added: %1$s";
    public static final String MESSAGE_DUPLICATE_DECK = "This deck already exists in the cardibuddy library";

    private static List<Card> flashcards = new ArrayList<>();

    private final Deck toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Deck}
     */
    public AddDeckCommand(Deck deck) {
        requireNonNull(deck);
        toAdd = deck;
        flashcards = deck.getFlashcards();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        if (model.hasOngoingTestSession()) {
            throw new CommandException(MESSAGE_TEST_ONGOING);
        }
        if (model.hasDeck(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_DECK);
        }
        model.addDeck(toAdd);
        model.commitCardiBuddy();

        LOGGER.info("Deck has been added");

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDeckCommand // instanceof handles nulls
                && toAdd.equals(((AddDeckCommand) other).toAdd));
    }
}
