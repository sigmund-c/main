package cardibuddy.logic.commands;

import static cardibuddy.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static cardibuddy.testutil.TypicalIndexes.INDEX_SECOND_CARD;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;

import cardibuddy.logic.CommandHistory;
import static cardibuddy.logic.commands.CommandTestUtil.DESC_QUESTION1;
import static cardibuddy.logic.commands.CommandTestUtil.DESC_QUESTION2;
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
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCardCommandTest {
    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager();
    private UiManager ui = new UiManager(new LogicManager(model
            , new StorageManager( new JsonCardiBuddyStorage(Paths.get("data" , "cardibuddy.json"))
            , new JsonUserPrefsStorage(Paths.get("preferences.json")))));
    private LogicToUiManager logicToUiManager = new LogicToUiManager(ui);

    @Test
    public void equals() {
        final EditCardCommand standardCommand = new EditCardCommand(INDEX_FIRST_CARD, DESC_QUESTION1, logicToUiManager);

        // same values -> returns true
        EditCardCommand.EditCardDescriptor copyDescriptor = new EditCardCommand.EditCardDescriptor(DESC_QUESTION1);
        EditCardCommand commandWithSameValues = new EditCardCommand(INDEX_FIRST_CARD, copyDescriptor, logicToUiManager);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCardCommand(INDEX_SECOND_CARD, DESC_QUESTION1, logicToUiManager)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCardCommand(INDEX_FIRST_CARD, DESC_QUESTION2, logicToUiManager)));
    }
}
