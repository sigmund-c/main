package cardibuddy.logic.commands.testsession;

import static cardibuddy.commons.core.Messages.MESSAGE_UNANSWERED_QUESTION;
import static java.util.Objects.requireNonNull;

import cardibuddy.logic.CommandHistory;
import cardibuddy.logic.commands.Command;
import cardibuddy.logic.commands.CommandResult;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.testsession.exceptions.AlreadyCorrectException;
import cardibuddy.model.testsession.exceptions.UnansweredQuestionException;
/**
 * A command class called in a test session, when the user forces their answer to be marked correct.
 */
public class ForceCommand extends Command {

    public static final String COMMAND_WORD = "force";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Forces CardiBuddy to mark the answer you gave as correct. "
            + "The flashcard will not be added back into your test session.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_FORCE_SUCCESS = "Marked your answer as correct. Type next to move on.";

    public static final String MESSAGE_FORCE_FAIL_ALREADYCORRECT = "Unable to force correct: "
            + "Your answer is already correct!";

    public ForceCommand() {

    }

    /**
     * Gets the current {@code TestSession} to change the {@code TestResult} of the current flashcard.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        try {
            model.forceCorrect();
        } catch (UnansweredQuestionException e) {
            throw new CommandException(String.format(MESSAGE_UNANSWERED_QUESTION, "Unable to force correct", ""));
        } catch (AlreadyCorrectException e) {
            throw new CommandException(MESSAGE_FORCE_FAIL_ALREADYCORRECT);
        }
        return new CommandResult(MESSAGE_FORCE_SUCCESS, false, false, false, false);
    }
}
