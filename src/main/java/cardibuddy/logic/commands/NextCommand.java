package cardibuddy.logic.commands;
import java.util.logging.Logger;

import cardibuddy.commons.core.LogsCenter;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.flashcard.Question;

import static java.util.Objects.requireNonNull;

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
    public static final String MESSAGE_TEST_COMPLETE = "No more flashcards to test!";
    private static final Logger logger = LogsCenter.getLogger(NextCommand.class);

    private LogicToUiManager logicToUiManager;
    private String settings; // TODO: implement force correct

    public NextCommand(LogicToUiManager logicToUiManager, String settings) {
        this.logicToUiManager = logicToUiManager;
        this.settings = settings; // TODO: implement force correct
    }


    /**
     * Gets the next question in the test queue, if any.
     * @param model {@code Model} which the command should operate on.
     * @return
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.isTestComplete()) {
            Question question = model.getNextQuestion();
            logicToUiManager.showTestQuestion(question);
            return new CommandResult(MESSAGE_NEXT_SUCCESS, false, false);
        } else {
            logicToUiManager.showTestEnd();
            return new CommandResult(MESSAGE_TEST_COMPLETE, false, false);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }
}
