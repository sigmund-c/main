package cardibuddy.logic;

import cardibuddy.logic.commands.Command;
import cardibuddy.logic.commands.RedoCommand;
import cardibuddy.logic.commands.UndoCommand;
import cardibuddy.logic.commands.UndoableCommand;

import java.util.LinkedList;

public class CardiBuddyStack {
    private static CardiBuddyStack cardiBuddyStack = new CardiBuddyStack();

    private LinkedList<UndoableCommand> undoStack;
    private LinkedList<UndoableCommand> redoStack;
    private boolean isUndoable;

    public CardiBuddyStack() {
        undoStack = new LinkedList<>();
        redoStack = new LinkedList<>();
        isUndoable = false;
    }

    public boolean isUndoable(Command command) {
        return command instanceof UndoableCommand;
    }

    /**
     * Pushes {@code command} into the undoStack if command is an {@code UndoableCommand}. Clears the redo-stack
     * if {@code command} is not an {@code UndoCommand} or a {@code RedoCommand}.
     */
    public void push(Command command) {
        if (!((command instanceof UndoCommand) || (command instanceof RedoCommand))) { //neither undo/redo
            redoStack.clear();
        }

        if (isUndoable(command)) { //is an undoable command
            undoStack.addFirst((UndoableCommand) command);
        }
    }

    /**
     * Pops the {@code UndoableCommand} to be undone from the undoStack.
     */
    public UndoableCommand popUndo() {
        UndoableCommand toUndo = undoStack.removeFirst();
        redoStack.addFirst(toUndo);
        return toUndo;
    }

    /**
     * Returns true if there are more commands that can be undone.
     */
    public boolean canUndo() {
        return undoStack.size() > 0;
    }

    /**
     * Pops and returns the next {@code UndoableCommand} to be redone in the stack.
     */
    public UndoableCommand popRedo() {
        UndoableCommand toRedo = redoStack.removeFirst();
        undoStack.addFirst(toRedo);
        return toRedo;
    }

    /**
     * Returns true if there are more commands that can be redone.
     */
    public boolean canRedo() {
        return redoStack.size() > 0;
    }

    public static CardiBuddyStack getCardiBuddyStack() {
        return cardiBuddyStack;
    }

    @Override
    public boolean equals(Object other) { // cannot be same object
        if (other == this) {
            return true;
        }

        if (!(other instanceof CardiBuddyStack)) { // cannot be null
            return false;
        }

        CardiBuddyStack stack = (CardiBuddyStack) other;

        return undoStack.equals(stack.undoStack)
                && redoStack.equals(stack.redoStack);
    }
}
