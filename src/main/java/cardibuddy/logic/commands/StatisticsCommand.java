package cardibuddy.logic.commands;

import static cardibuddy.commons.core.Messages.MESSAGE_TEST_ONGOING;
import static java.util.Objects.requireNonNull;

import java.util.List;

import cardibuddy.commons.core.Messages;
import cardibuddy.commons.core.index.Index;
import cardibuddy.logic.CommandHistory;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.deck.Deck;

/**
 * Shows the Statistics report of a specific Deck or every Deck at once.
 */
public class StatisticsCommand extends Command {

    public static final String COMMAND_WORD = "statistics";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the user's statistics in general (if no parameters),\n"
            + " of a deck (if deck_index is included),\n"
            + " or of a testSession of a deck (if deck_index AND test_index is included.\n"
            + "Parameters: (optional) DECK_INDEX (optional) TEST_INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_OPEN_STATISTIC_SUCCESS = "Opened statistics";
    public static final String MESSAGE_OPEN_DECK_STATISTIC_SUCCESS = "Opened statistics of Deck: %1$s";
    public static final String MESSAGE_OPEN_SESSION_STATISTIC_SUCCESS =
                                                "Opened statistics of Session %1$d of Deck %2$s";

    private LogicToUiManager logicToUiManager;

    private final Index deckIndex;
    private final Index sessionIndex;

    /**
     * Opens the Statistic report of all the Decks.
     * @param logicToUiManager manager to set the UI into
     */
    public StatisticsCommand(LogicToUiManager logicToUiManager) {
        this.deckIndex = null;
        this.sessionIndex = null;
        this.logicToUiManager = logicToUiManager;
    }

    /**
     * Opens the Statistic report of a specific Deck.
     * @param deckIndex target Deck
     * @param logicToUiManager manager to set the UI into
     */
    public StatisticsCommand(Index deckIndex, LogicToUiManager logicToUiManager) {
        this.deckIndex = deckIndex;
        this.sessionIndex = null;
        this.logicToUiManager = logicToUiManager;
    }

    /**
     * Opens the Statistic report of a specific Deck.
     * @param deckIndex target Deck
     * @param logicToUiManager manager to set the UI into
     */
    public StatisticsCommand(Index deckIndex, Index sessionIndex, LogicToUiManager logicToUiManager) {
        this.deckIndex = deckIndex;
        this.sessionIndex = sessionIndex;
        this.logicToUiManager = logicToUiManager;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        if (model.hasOngoingTestSession()) {
            throw new CommandException(MESSAGE_TEST_ONGOING);
        }

        // result to show statistics (in general)
        if (deckIndex == null) {
            logicToUiManager.openStatisticPanel();
            return new CommandResult(
                    String.format(MESSAGE_OPEN_STATISTIC_SUCCESS));
        }

        List<Deck> lastShownList = model.getFilteredDeckList();
        if (deckIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
        }
        Deck deckToShow = lastShownList.get(deckIndex.getZeroBased());

        // result to show statistics of a Deck
        if (sessionIndex == null) {
            logicToUiManager.openStatisticPanel(deckIndex.getZeroBased());
            return new CommandResult(
                    String.format(MESSAGE_OPEN_DECK_STATISTIC_SUCCESS, deckToShow));
        }

        // result to show statistics of a TestSession of a Deck
        logicToUiManager.openStatisticPanel(deckIndex.getZeroBased(), sessionIndex.getZeroBased());
        return new CommandResult(
                String.format(MESSAGE_OPEN_SESSION_STATISTIC_SUCCESS, sessionIndex.getOneBased(), deckToShow));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StatisticsCommand) // instanceof handles nulls
                && deckIndex.equals(((StatisticsCommand) other).deckIndex)
                && sessionIndex.equals(((StatisticsCommand) other).sessionIndex); // state check
    }


}
