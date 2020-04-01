package cardibuddy.logic.commands;

import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;

/**
 * Parent class of all commands in CardiBuddy that can be undone
 * */
public abstract class UndoableCommand extends Command {
    private boolean isExecuted = false;
    public abstract void reverse(Model model) throws CommandException;
    public abstract String getCommandWord();

    public boolean isExecuted() {
        return isExecuted;
    }

    public void setExecuted(boolean executed) {
        isExecuted = executed;
    }
}
