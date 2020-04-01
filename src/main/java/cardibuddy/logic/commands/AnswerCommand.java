package cardibuddy.logic.commands;

import static cardibuddy.commons.core.Messages.MESSAGE_NO_TESTSESSION;
import static java.util.Objects.requireNonNull;

import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.testsession.TestResult;

/**
 * A class for the test command, used to initiate a test session.
 */
public class AnswerCommand extends Command {

    public static final String COMMAND_WORD = "ans";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Submits your answer to CardiBuddy.\n"
            + "Example: " + COMMAND_WORD + " Waterfall is not an agile approach.";

    public static final String MESSAGE_ANS_SUCCESS = "Answer submitted!"
            + "\nType 'force' to force mark your answer as correct, or"
            + "\nType next to move on to the next question.";

    private LogicToUiManager logicToUiManager;
    private String userAnswer;

    public AnswerCommand(LogicToUiManager logicToUiManager, String userAnswer) {
        this.logicToUiManager = logicToUiManager;
        this.userAnswer = userAnswer;
    }

    /**
     * Submits the user's answer to the {@code TestSession} stored in the model.
     * A {@code Result} is obtained which is passed to the Ui to display the outcome to the user.
     *
     * @param model {@code Model} which the command should operate on.
     * @return CommandResult object
     * @throws CommandException when there is no ongoing test session
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            TestResult testResult = model.submitAnswer(userAnswer);
            logicToUiManager.showTestResult(testResult);
            return new CommandResult(MESSAGE_ANS_SUCCESS, false, false);
        } catch (NullPointerException e) {
            throw new CommandException(MESSAGE_NO_TESTSESSION);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AnswerCommand && ((AnswerCommand) other).userAnswer.equals(userAnswer));
    }
}

