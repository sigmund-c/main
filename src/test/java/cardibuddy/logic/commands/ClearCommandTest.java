package cardibuddy.logic.commands;

import static cardibuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static cardibuddy.testutil.TypicalDecks.getTypicalCardiBuddy;

import org.junit.jupiter.api.Test;

import cardibuddy.logic.CommandHistory;
import cardibuddy.model.CardiBuddy;
import cardibuddy.model.Model;
import cardibuddy.model.ModelManager;
import cardibuddy.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitCardiBuddy();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalCardiBuddy(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalCardiBuddy(), new UserPrefs());
        expectedModel.setCardiBuddy(new CardiBuddy());
        expectedModel.commitCardiBuddy();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);
    }

}
