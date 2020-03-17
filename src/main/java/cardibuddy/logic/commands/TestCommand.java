package cardibuddy.logic.commands;

import cardibuddy.model.Model;

/**
 * Test Command
 */
public class TestCommand extends Command {

    public static final String COMMAND_WORD = "test";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Started a test session with the given deck index.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_USAGE, false, true);
    }
}
