package cardibuddy.logic.commands;

import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;

public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
