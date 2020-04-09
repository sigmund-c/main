package cardibuddy.logic.commands.testsession;

import static cardibuddy.commons.core.Messages.MESSAGE_NO_TESTSESSION;
import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import cardibuddy.commons.core.LogsCenter;
import cardibuddy.logic.CommandHistory;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.Command;
import cardibuddy.logic.commands.CommandResult;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.testsession.exceptions.NoOngoingTestException;

/**
 * A command to quit the current test session prematurely,
 * if there are still flashcards remaining in the test queue.
 */
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
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        try {

            model.clearTestSession();
            logicToUiManager.showTestEnd();

            return new CommandResult(MESSAGE_QUIT_SUCCESS, false, false, false, false);
        } catch (NoOngoingTestException e) {
            throw new CommandException(MESSAGE_NO_TESTSESSION);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || other instanceof QuitCommand; // short circuit if same object
    }
}
