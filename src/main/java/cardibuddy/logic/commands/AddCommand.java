package cardibuddy.logic.commands;

import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.deck.Deck;

import static cardibuddy.logic.parser.CliSyntax.*;
import static java.util.Objects.requireNonNull;

public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    private final Deck toAdd;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a deck to the cardibuddy book. "
            + "Parameters: "
            + "[" + PREFIX_Deck + "] \n"
            + "[" + PREFIX_Flashcard + "] \n" //Has default values for cardType, question and answer
            + PREFIX_TITLE + "Title "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "cs2103t "
            + PREFIX_TAG + "Hard "
            + PREFIX_TAG + "Software Engineering";

    public static final String MESSAGE_SUCCESS = "New deck added: %1$s";
    public static final String MESSAGE_DUPLICATE_DECK = "This deck already exists in the cardibuddy library";

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
