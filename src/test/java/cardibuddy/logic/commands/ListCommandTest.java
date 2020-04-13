package cardibuddy.logic.commands;

import static cardibuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static cardibuddy.logic.commands.CommandTestUtil.showDeckAtIndex;
import static cardibuddy.testutil.TypicalIndexes.INDEX_FIRST_DECK;
import static cardibuddy.testutil.TypicalDecks.getTypicalCardiBuddy;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cardibuddy.logic.CommandHistory;
import cardibuddy.logic.Logic;
import cardibuddy.logic.LogicManager;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.model.Model;
import cardibuddy.model.ModelManager;
import cardibuddy.model.UserPrefs;
import cardibuddy.storage.*;
import cardibuddy.ui.UiManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();
    private Path userPrefsFilePath = Paths.get("preferences.json");
    private UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsFilePath);
    private Path cardibuddyFilePath = Paths.get("data" , "cardibuddy.json");
    private CardiBuddyStorage cardiBuddyStorage = new JsonCardiBuddyStorage(cardibuddyFilePath);
    private Storage storage = new StorageManager(cardiBuddyStorage, userPrefsStorage);
    private Model model = new ModelManager();
    private Logic logic = new LogicManager(model, storage);
    private UiManager ui = new UiManager(logic);
    private LogicToUiManager logicToUiManager = new LogicToUiManager(ui);

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalCardiBuddy(), new UserPrefs());
        expectedModel = new ModelManager(model.getCardiBuddy(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(logicToUiManager), model, ListCommand.MESSAGE_SUCCESS, expectedModel,
                commandHistory);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showDeckAtIndex(model, INDEX_FIRST_DECK);
        assertCommandSuccess(new ListCommand(logicToUiManager), model, ListCommand.MESSAGE_SUCCESS, expectedModel,
                commandHistory);
    }
}

