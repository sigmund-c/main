package cardibuddy.logic.commands;


import static java.util.Objects.requireNonNull;

import java.util.List;

import cardibuddy.commons.core.Messages;
import cardibuddy.commons.core.index.Index;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.deck.Deck;

/**
 * A class for the test command, used to initiate a test session.
 */
public class TestCommand extends Command {

    public static final String COMMAND_WORD = "test";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Starts a test session with the deck indicated by the index\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_TEST_SESSION_SUCCESS = "";

    private final Index targetIndex;

    public TestCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Deck> lastShownList = model.getFilteredDeckList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
        }

        Deck deckToTest =
                lastShownList.get(targetIndex.getZeroBased());
        model.testDeck(deckToTest);
        return new CommandResult(MESSAGE_TEST_SESSION_SUCCESS, false, false);
    }
  
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TestCommand // instanceof handles nulls
                && targetIndex.equals(((TestCommand) other).targetIndex)); // state check
    }
}
