package cardibuddy.logic.commands;

import static cardibuddy.commons.core.Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX;
import static cardibuddy.logic.commands.CommandTestUtil.assertCommandFailure;
import static cardibuddy.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static cardibuddy.testutil.TypicalIndexes.INDEX_SECOND_CARD;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import cardibuddy.commons.core.Messages;
import cardibuddy.commons.core.index.Index;
import cardibuddy.logic.CommandHistory;
import cardibuddy.logic.Logic;
import cardibuddy.logic.LogicManager;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.model.Model;
import cardibuddy.model.ModelManager;
import cardibuddy.storage.CardiBuddyStorage;
import cardibuddy.storage.JsonCardiBuddyStorage;
import cardibuddy.storage.JsonUserPrefsStorage;
import cardibuddy.storage.Storage;
import cardibuddy.storage.StorageManager;
import cardibuddy.storage.UserPrefsStorage;
import cardibuddy.ui.UiManager;

import org.junit.jupiter.api.Test;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCardCommandTest {
    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager();
    private UiManager ui = new UiManager(new LogicManager(model
            , new StorageManager( new JsonCardiBuddyStorage(Paths.get("data" , "cardibuddy.json"))
            , new JsonUserPrefsStorage(Paths.get("preferences.json")))));
    private LogicToUiManager logicToUiManager = new LogicToUiManager(ui);

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDeckList().size() + 1);
        DeleteCardCommand deleteCardCommand = new DeleteCardCommand(outOfBoundIndex, logicToUiManager);

        assertCommandFailure(deleteCardCommand, model, commandHistory,
                MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFlashcardList().size() + 1);
        DeleteCardCommand deleteCardCommand = new DeleteCardCommand(outOfBoundIndex, logicToUiManager);

        // execution failed -> cardibuddy state not added into model
        assertCommandFailure(deleteCardCommand, model, commandHistory, Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);

        // single cardibuddy state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, Messages.MESSAGE_NOTHING_TO_UNDO);
        assertCommandFailure(new RedoCommand(), model, commandHistory, Messages.MESSAGE_NOTHING_TO_REDO);
    }

    @Test
    public void equals() {
        DeleteCardCommand deleteCardFirstCommand = new DeleteCardCommand(INDEX_FIRST_CARD, logicToUiManager);
        DeleteCardCommand deleteCardSecondCommand = new DeleteCardCommand(INDEX_SECOND_CARD, logicToUiManager);

        // same object -> returns true
        assertTrue(deleteCardFirstCommand.equals(deleteCardFirstCommand));

        // same values -> returns true
        DeleteCardCommand deleteCardFirstCommandCopy = new DeleteCardCommand(INDEX_FIRST_CARD, logicToUiManager);
        assertTrue(deleteCardFirstCommand.equals(deleteCardFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteCardFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteCardFirstCommand.equals(null));

        // different deck -> returns false
        assertFalse(deleteCardFirstCommand.equals(deleteCardSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoCard(Model model) {
        model.updateFilteredFlashcardList(p -> false);

        assertTrue(model.getFilteredFlashcardList().isEmpty());
    }

//    public void getUserPrefsStorage() {
//        this.userPrefsStorage;
//    }
}
