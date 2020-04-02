package cardibuddy.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import cardibuddy.commons.core.Messages;
import cardibuddy.commons.core.index.Index;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.flashcard.Flashcard;

/**
 * Deletes a card from the opened deck.
 */
public class DeleteCardCommand extends DeleteCommand {

    public static final String COMMAND_WORD = "deck";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a flashcard identified by the index number used in the displayed cardibuddy book.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + "1";

    public static final String MESSAGE_DELETE_CARD_SUCCESS = "Deleted Card:\n%1$s";

    private final Index targetIndex;
    private LogicToUiManager logicToUiManager;

    public DeleteCardCommand(Index targetIndex, LogicToUiManager logicToUiManager) {
        this.targetIndex = targetIndex;
        this.logicToUiManager = logicToUiManager;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Flashcard> lastShownList = logicToUiManager.getDisplayedDeck().getFilteredFlashcardList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
        }

        Flashcard cardToDelete = lastShownList.get(targetIndex.getZeroBased());
        logicToUiManager.getDisplayedDeck().deleteCard(cardToDelete);
        logicToUiManager.updateFlashcardPanel();

        return new CommandResult(String.format(MESSAGE_DELETE_CARD_SUCCESS, cardToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCardCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCardCommand) other).targetIndex)); // state check
    }

}
