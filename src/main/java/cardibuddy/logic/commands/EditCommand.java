package cardibuddy.logic.commands;

import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;

public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
