package cardibuddy.logic.commands;

import static cardibuddy.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static cardibuddy.testutil.TypicalIndexes.INDEX_SECOND_CARD;
import static cardibuddy.testutil.TypicalDecks.getTypicalCardiBuddy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import cardibuddy.logic.CommandHistory;
import static cardibuddy.logic.commands.CommandTestUtil.DESC_QUESTION1;
import static cardibuddy.logic.commands.CommandTestUtil.DESC_QUESTION2;
import cardibuddy.logic.Logic;
import cardibuddy.logic.LogicManager;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.model.Model;
import cardibuddy.model.ModelManager;
import cardibuddy.model.UserPrefs;
import cardibuddy.storage.CardiBuddyStorage;
import cardibuddy.storage.JsonCardiBuddyStorage;
import cardibuddy.storage.JsonUserPrefsStorage;
import cardibuddy.storage.Storage;
import cardibuddy.storage.StorageManager;
import cardibuddy.storage.UserPrefsStorage;
import cardibuddy.ui.UiManager;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCardCommandTest {

    private Model model = new ModelManager(getTypicalCardiBuddy(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private Path userPrefsFilePath = Paths.get("preferences.json");
    private UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsFilePath);
    private Path cardibuddyFilePath = Paths.get("data" , "cardibuddy.json");
    private CardiBuddyStorage cardiBuddyStorage = new JsonCardiBuddyStorage(cardibuddyFilePath);
    private Storage storage = new StorageManager(cardiBuddyStorage, userPrefsStorage);
    private Logic logic = new LogicManager(model, storage);
    private UiManager ui = new UiManager(logic);
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
