package cardibuddy.logic;

import cardibuddy.logic.commands.Command;
import cardibuddy.logic.commands.UndoableCommand;

import java.util.Stack;

/**
 * The main CommandHistoryManager of CardiBuddy.
 */
public class CommandHistoryManager implements CommandHistory {
    private Stack<UndoableCommand> undoStack;
    private Stack<UndoableCommand> redoStack;
    private boolean canUndo;

    public CommandHistoryManager() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        canUndo = false;
    }

    @Override
    public boolean canUndoCommand() {
        if (sizeUndoStack() > 0) {
            canUndo = true;
        }
        return canUndo;
    }

    /**
     * Push a command into the stack to undo.
     * */
    @Override
    public void pushUndoCommand(Command command) {
        if (command instanceof UndoableCommand) {
            undoStack.push((UndoableCommand) command);
        }
    }

    /**
     * pop last command from undo stack
     * */
    @Override
    public UndoableCommand popUndoCommand() {
        UndoableCommand command = undoStack.pop();
        pushRedoCommand(command);
        return command;
    }

    /**
     * peek last command from undo stack
     * */
    @Override
    public UndoableCommand peekUndoCommand() {
        return undoStack.peek();
    }

    /**
     * return stack size of undo stack
     * */
    @Override
    public int sizeUndoStack() {
        return undoStack.size();
    }

    @Override
    public void pushRedoCommand(Command command) {
        if (command instanceof UndoableCommand) {
            redoStack.push((UndoableCommand) command);
        }
    }

    @Override
    public UndoableCommand popRedoCommand() {
        UndoableCommand command = redoStack.pop();
        pushUndoCommand(command);
        return command;
    }

    @Override
    public UndoableCommand peekRedoCommand() {
        return redoStack.peek();
    }

    @Override
    public int sizeRedoStack() {
        return redoStack.size();
    }

    /**
     * Clears the redo stack (for use when new command is executed)
     * */
    @Override
    public void clearRedoStack() {
        redoStack.clear();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof CommandHistoryManager)) {
            return false;
        }

        CommandHistoryManager commandHistoryManager = (CommandHistoryManager) other;
        return commandHistoryManager.redoStack.equals(this.redoStack)
                && commandHistoryManager.undoStack.equals(this.undoStack);
    }
}
