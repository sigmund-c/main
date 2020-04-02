package cardibuddy.logic.commands;

import static cardibuddy.commons.core.Messages.MESSAGE_NO_TESTSESSION;
import static cardibuddy.commons.core.Messages.MESSAGE_TEST_COMPLETE;
import static cardibuddy.commons.core.Messages.MESSAGE_UNANSWERED_QUESTION;
import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import cardibuddy.commons.core.LogsCenter;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.flashcard.Question;
import cardibuddy.model.testsession.exceptions.NoOngoingTestException;
import cardibuddy.model.testsession.exceptions.UnansweredQuestionException;

/**
 * A class for the test command, used to initiate a test session.
 */
public class NextCommand extends Command {

    public static final String COMMAND_WORD = "next";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the next flashcard in the test queue, if any.\n"
            + "If you think your answer is correct, enter next force to force correct.\n"
            + "Example: " + COMMAND_WORD + "\n"
            + "OR: " + COMMAND_WORD + " force";

    public static final String MESSAGE_NEXT_SUCCESS = "Showing the next question";

    private static final Logger logger = LogsCenter.getLogger(NextCommand.class);

    private LogicToUiManager logicToUiManager;

    public NextCommand(LogicToUiManager logicToUiManager) {
        this.logicToUiManager = logicToUiManager;
    }


    /**
     * Gets the next question in the test queue, if any.
     *
     * @param model {@code Model} which the command should operate on.
     * @return CommandResult object
     * @throws CommandException if there is no currently ongoing test session, or if the user has not answered the question yet.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            if (!model.isTestComplete()) {
                Question question = model.getNextQuestion();
                logicToUiManager.showTestQuestion(question);
                return new CommandResult(MESSAGE_NEXT_SUCCESS, false, false);
            } else { // if no more flashcards in the test queue
                model.clearTestSession();
                logicToUiManager.showTestEnd();
                return new CommandResult(MESSAGE_TEST_COMPLETE, false, false);
            }
        } catch (NoOngoingTestException e) {
            throw new CommandException(MESSAGE_NO_TESTSESSION);
        } catch (UnansweredQuestionException e) {
            throw new CommandException(String.format
                    (MESSAGE_UNANSWERED_QUESTION,
                            "Cannot go to the next question",
                            "\nType 'skip' if you want to skip this question."));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || other instanceof NextCommand; // short circuit if same object
    }
}
