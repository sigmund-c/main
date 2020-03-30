package cardibuddy.logic.commands;

import static cardibuddy.commons.core.Messages.MESSAGE_EMPTY_DECK;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import cardibuddy.commons.core.LogsCenter;
import cardibuddy.commons.core.Messages;
import cardibuddy.commons.core.index.Index;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Question;
import cardibuddy.model.testsession.exceptions.EmptyDeckException;

/**
 * A class for the test command, used to initiate a test session.
 */
public class TestCommand extends Command {

    public static final String COMMAND_WORD = "test";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Starts a test session with the deck indicated by the index\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_TEST_SESSION_SUCCESS = "Started a test session.";
    private static final Logger logger = LogsCenter.getLogger(TestCommand.class);

    private LogicToUiManager logicToUiManager;
    private final Index targetIndex;

    public TestCommand(Index targetIndex, LogicToUiManager logicToUiManager) {
        this.targetIndex = targetIndex;
        this.logicToUiManager = logicToUiManager;
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

        try {
            Question firstQuestion = model.testDeck(deckToTest);
            logicToUiManager.showTestQuestion(firstQuestion);
            return new CommandResult(MESSAGE_TEST_SESSION_SUCCESS, false, false);
        } catch (EmptyDeckException e) {
            throw new CommandException(MESSAGE_EMPTY_DECK);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TestCommand // instanceof handles nulls
                && targetIndex.equals(((TestCommand) other).targetIndex)); // state check
    }
}
