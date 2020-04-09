package cardibuddy.logic.commands.testsession;

import static cardibuddy.commons.core.Messages.MESSAGE_NO_TESTSESSION;
import static cardibuddy.commons.core.Messages.MESSAGE_TEST_COMPLETE;
import static java.util.Objects.requireNonNull;

import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.Command;
import cardibuddy.logic.commands.CommandResult;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.flashcard.Question;
import cardibuddy.model.testsession.exceptions.AlreadyCorrectException;
import cardibuddy.model.testsession.exceptions.EmptyTestQueueException;
import cardibuddy.model.testsession.exceptions.NoOngoingTestException;

/**
 * A command to skip the current question. Sets the {@code TestResult} to contain {@code Result.SKIPPED}
 * Not to be confused with {@code ForceCommand},
 * that can only be called when the user has answered the question wrongly.
 * ForceCommand will set {@code TestResult} to contain {@code Result.CORRECT}
 */
public class SkipCommand extends Command {

    public static final String COMMAND_WORD = "skip";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Skip the current flashcard being tested.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SKIP_SUCCESS = "Skipped the question.";
    public static final String MESSAGE_SKIP_FAIL =
            "Unable to skip the question: "
                    + "You already got it correct! (Why would you want to skip it?)"
                    + "\nType 'next' to go to the next question instead!";

    private LogicToUiManager logicToUiManager;

    public SkipCommand(LogicToUiManager logicToUiManager) {
        this.logicToUiManager = logicToUiManager;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            Question question = model.skipQuestion();
            logicToUiManager.showTestQuestion(question);
            logicToUiManager.showTestStatus(model.getTestQueueSize());
            return new CommandResult(MESSAGE_SKIP_SUCCESS, false, false, false, false);
        } catch (EmptyTestQueueException e) {
            model.clearTestSession();
            logicToUiManager.showTestEnd();
            return new CommandResult(MESSAGE_TEST_COMPLETE, false, false, false, false);
        } catch (NoOngoingTestException e) {
            throw new CommandException(MESSAGE_NO_TESTSESSION);
        } catch (AlreadyCorrectException e) {
            throw new CommandException(MESSAGE_SKIP_FAIL);
        }
    }

}
