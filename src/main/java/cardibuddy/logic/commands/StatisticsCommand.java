package cardibuddy.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import cardibuddy.commons.core.Messages;
import cardibuddy.commons.core.index.Index;
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
            + ": Shows the user's statistics in general or on a particular deck. "
            + "Parameters: (optional) INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_OPEN_STATISTIC_SUCCESS = "Opened statistics";

    public static final String MESSAGE_OPEN_DECK_STATISTIC_SUCCESS = "Opened statistics of Deck: %1$s";

    private LogicToUiManager logicToUiManager;

    private final Index targetIndex;

    /**
     * Opens the Statistic report of a specific Deck.
     * @param index target Deck
     * @param logicToUiManager manager to set the UI into
     */
    public StatisticsCommand(Index index, LogicToUiManager logicToUiManager) {
        this.targetIndex = index;
        this.logicToUiManager = logicToUiManager;
    }

    /**
     * Opens the Statistic report of all the Decks.
     * @param logicToUiManager manager to set the UI into
     */
    public StatisticsCommand(LogicToUiManager logicToUiManager) {
        this.targetIndex = null;
        this.logicToUiManager = logicToUiManager;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (targetIndex == null) { // execute if index isn't specified (Statistics of all the Decks)
            logicToUiManager.openStatisticPanel();

            //TODO: implement functionality for statistics of ALL decks

            return new CommandResult(
                    String.format(MESSAGE_OPEN_STATISTIC_SUCCESS));
        }

        List<Deck> lastShownList = model.getFilteredDeckList();


        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
        }

        Deck deckToShow = lastShownList.get(targetIndex.getZeroBased());

        System.out.println(targetIndex.getZeroBased());
        logicToUiManager.openStatisticPanel(targetIndex.getZeroBased());

        return new CommandResult(
                String.format(MESSAGE_OPEN_DECK_STATISTIC_SUCCESS, deckToShow));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StatisticsCommand) // instanceof handles nulls
                && targetIndex.equals(((StatisticsCommand) other).targetIndex); // state check
    }


}
