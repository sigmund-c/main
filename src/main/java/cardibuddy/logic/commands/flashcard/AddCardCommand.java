package cardibuddy.logic.commands.flashcard;

import cardibuddy.logic.commands.Command;
import cardibuddy.logic.commands.CommandResult;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.flashcard.Flashcard;

import static java.util.Objects.requireNonNull;

/**
 * Adds a deck to the cardibuddy book.
 */
public class AddCardCommand extends Command {

    public static final String COMMAND_WORD = "add card";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a card to a specific deck. "
            + "Parameters: "
            + PREFIX_Title + "Title "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_Title + "cs2103t "
            + PREFIX_TAG + "Hard "
            + PREFIX_TAG + "Software Engineering";

    public static final String MESSAGE_SUCCESS = "New card added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This card already exists in the deck";

    private final Flashcard toAdd;
    /**
     * Creates an AddCommand to add the specified {@code Deck}
     */
    public AddCardCommand(Flashcard card) {
        requireNonNull(Flashcard);
        toAdd = card;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasCard(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CARD);
        }

        model.addCard(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCardCommand // instanceof handles nulls
                && toAdd.equals(((AddCardCommand) other).toAdd));
    }
}
