package cardibuddy.logic;

import cardibuddy.logic.commands.Command;
import cardibuddy.logic.commands.UndoableCommand;

import static java.util.Objects.requireNonNull;

/**
 * API of the CommandHistory component
 */
public interface CommandHistory {
    // public boolean canUndoCommand();

    public void pushUndoCommand(Command command);

    public UndoableCommand popUndoCommand();

    public UndoableCommand peekUndoCommand();

    public int sizeUndoStack();

    public void pushRedoCommand(Command command);

    public UndoableCommand popRedoCommand();

    public UndoableCommand peekRedoCommand();

    public int sizeRedoStack();

    public void clearRedoStack();
}
