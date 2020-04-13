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
import cardibuddy.model.flashcard.Card;

/**
 * Deletes a card from the opened deck.
 */
public class DeleteCardCommand extends DeleteCommand {

    public static final String COMMAND_WORD = "card";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a flashcard identified by the index number used in the displayed cardibuddy book.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: delete " + COMMAND_WORD + "1";

    public static final String MESSAGE_DELETE_CARD_SUCCESS = "Deleted Card:\n%1$s";

    private final Index targetIndex;
    private LogicToUiManager logicToUiManager;

    public DeleteCardCommand(Index targetIndex, LogicToUiManager logicToUiManager) {
        this.targetIndex = targetIndex;
        this.logicToUiManager = logicToUiManager;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        if (model.hasOngoingTestSession()) {
            throw new CommandException(MESSAGE_TEST_ONGOING);
        }

        List<Card> lastShownList = logicToUiManager.getDisplayedDeck().getFilteredFlashcardList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
        }

        Card cardToDelete = lastShownList.get(targetIndex.getZeroBased());
        logicToUiManager.getDisplayedDeck().deleteCard(cardToDelete);
        logicToUiManager.updateFlashcardPanel();
        model.commitCardiBuddy();

        return new CommandResult(String.format(MESSAGE_DELETE_CARD_SUCCESS, cardToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCardCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCardCommand) other).targetIndex)); // state check
    }

}
