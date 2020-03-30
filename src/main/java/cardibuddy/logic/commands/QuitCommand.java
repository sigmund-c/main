package cardibuddy.logic.commands;

import cardibuddy.commons.core.LogsCenter;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.testsession.exceptions.NoOngoingTestException;

import java.util.logging.Logger;

import static cardibuddy.commons.core.Messages.MESSAGE_NO_TESTSESSION;
import static java.util.Objects.requireNonNull;

public class QuitCommand extends Command {
    public static final String COMMAND_WORD = "quit";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "Quits the current test session before it is completed.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_QUIT_SUCCESS = "Quit the test session.";
    private static final Logger logger = LogsCenter.getLogger(NextCommand.class);
    private LogicToUiManager logicToUiManager;

    public QuitCommand(LogicToUiManager logicToUiManager) {
        this.logicToUiManager = logicToUiManager;
    }

    /**
     * Gets the next question in the test queue, if any.
     *
     * @param model {@code Model} which the command should operate on.
     * @return
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {

            model.clearTestSession();
            logicToUiManager.showTestEnd();

            return new CommandResult(MESSAGE_QUIT_SUCCESS, false, false);
        } catch (NoOngoingTestException e) {
            throw new CommandException(MESSAGE_NO_TESTSESSION);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this ||
                other instanceof QuitCommand; // short circuit if same object
    }
}
