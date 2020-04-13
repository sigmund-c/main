package cardibuddy.logic.commands;

import static cardibuddy.logic.commands.CommandTestUtil.assertCommandFailure;
import static cardibuddy.testutil.TypicalIndexes.INDEX_FIRST_DECK;
import static cardibuddy.testutil.TypicalIndexes.INDEX_SECOND_DECK;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;

import cardibuddy.commons.core.Messages;
import cardibuddy.commons.core.index.Index;
import cardibuddy.logic.CommandHistory;
import cardibuddy.logic.LogicManager;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.model.Model;
import cardibuddy.model.ModelManager;
import cardibuddy.storage.JsonCardiBuddyStorage;
import cardibuddy.storage.JsonUserPrefsStorage;
import cardibuddy.storage.StorageManager;
import cardibuddy.ui.UiManager;

import org.junit.jupiter.api.Test;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteDeckCommandTest {
    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager();
    private UiManager ui = new UiManager(new LogicManager(model
            , new StorageManager( new JsonCardiBuddyStorage(Paths.get("data" , "cardibuddy.json"))
            , new JsonUserPrefsStorage(Paths.get("preferences.json")))));
    private LogicToUiManager logicToUiManager = new LogicToUiManager(ui);

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDeckList().size() + 1);
        DeleteDeckCommand deleteDeckCommand = new DeleteDeckCommand(outOfBoundIndex, logicToUiManager);

        assertCommandFailure(deleteDeckCommand, model, commandHistory, Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDeckList().size() + 1);
        DeleteDeckCommand deleteDeckCommand = new DeleteDeckCommand(outOfBoundIndex, logicToUiManager);

        // execution failed -> cardibuddy state not added into model
        assertCommandFailure(deleteDeckCommand, model, commandHistory, Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);

        // single cardibuddy state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, Messages.MESSAGE_NOTHING_TO_UNDO);
        assertCommandFailure(new RedoCommand(), model, commandHistory, Messages.MESSAGE_NOTHING_TO_REDO);
    }

    @Test
    public void equals() {
        DeleteDeckCommand deleteDeckFirstCommand = new DeleteDeckCommand(INDEX_FIRST_DECK, logicToUiManager);
        DeleteDeckCommand deleteDeckSecondCommand = new DeleteDeckCommand(INDEX_SECOND_DECK, logicToUiManager);

        // same object -> returns true
        assertTrue(deleteDeckFirstCommand.equals(deleteDeckFirstCommand));

        // same values -> returns true
        DeleteDeckCommand deleteDeckFirstCommandCopy = new DeleteDeckCommand(INDEX_FIRST_DECK, logicToUiManager);
        assertTrue(deleteDeckFirstCommand.equals(deleteDeckFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteDeckFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteDeckFirstCommand.equals(null));

        // different deck -> returns false
        assertFalse(deleteDeckFirstCommand.equals(deleteDeckSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoDeck(Model model) {
        model.updateFilteredDeckList(p -> false);

        assertTrue(model.getFilteredDeckList().isEmpty());
    }
}
