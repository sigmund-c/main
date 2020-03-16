package cardibuddy.logic.commands;

import static cardibuddy.logic.parser.CliSyntax.PREFIX_DECK;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_FLASHCARD;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_TAG;

import static java.util.Objects.requireNonNull;

import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.deck.Deck;

/**
 * Adds a deck to the cardibuddy.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a deck/flashcard to the cardibuddy book. "
            + "Parameters: "
            + "[" + PREFIX_DECK + "Deck \n"
            + "[" + PREFIX_FLASHCARD + "Flashcard \n"
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DECK + "cs2105 "
            + PREFIX_TAG + "Hard "
            + PREFIX_TAG + "Software Engineering";

    public static final String MESSAGE_SUCCESS = "New deck added: %1$s";
    public static final String MESSAGE_DUPLICATE_DECK = "This deck already exists in the cardibuddy library";

    private final Deck toAdd;
    /**
     * Creates an AddCommand to add the specified {@code Deck}
     */
    public AddCommand(Deck deck) {
        requireNonNull(deck);
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
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
