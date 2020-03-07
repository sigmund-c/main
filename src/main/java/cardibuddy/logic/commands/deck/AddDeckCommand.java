package cardibuddy.logic.commands.deck;

import cardibuddy.logic.commands.Command;
import cardibuddy.logic.commands.CommandResult;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.deck.Deck;

import static java.util.Objects.requireNonNull;

/**
 * Adds a deck to the cardibuddy book.
 */
public class AddDeckCommand extends Command {

    public static final String COMMAND_WORD = "add deck";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a deck to the cardibuddy book. "
            + "Parameters: "
            + PREFIX_Title + "Title "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_Title + "cs2103t "
            + PREFIX_TAG + "Hard "
            + PREFIX_TAG + "Software Engineering";

    public static final String MESSAGE_SUCCESS = "New deck added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This deck already exists in the cardibuddy library";

    private final Deck toAdd;
    /**
     * Creates an AddCommand to add the specified {@code Deck}
     */
    public AddDeckCommand(Deck deck) {
        requireNonNull(Deck);
        toAdd = deck;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasDeck(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_DECK);
        }

        model.addDeck(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDeckCommand // instanceof handles nulls
                && toAdd.equals(((AddDeckCommand) other).toAdd));
    }
}
