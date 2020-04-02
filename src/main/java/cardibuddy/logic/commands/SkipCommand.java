package cardibuddy.logic.commands;

import static java.util.Objects.requireNonNull;

import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;

public class SkipCommand extends Command{

    public static final String COMMAND_WORD = "skip";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Skip the current flashcard being tested.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SKIP_SUCCESS = "Successfully skipped the question.";

    private LogicToUiManager logicToUiManager;

    public SkipCommand(LogicToUiManager logicToUiManager) {
        this.logicToUiManager = logicToUiManager;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            return null;
        }
    }

}
