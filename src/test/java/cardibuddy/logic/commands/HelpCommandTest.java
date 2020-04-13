package cardibuddy.logic.commands;

import static cardibuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static cardibuddy.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import cardibuddy.logic.CommandHistory;
import cardibuddy.model.Model;
import cardibuddy.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true,
                false, false, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel, commandHistory);
    }
}
