package cardibuddy.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code CardiBuddy} that keeps track of its own history.
 */
public class VersionedCardiBuddy extends CardiBuddy {

    private final List<ReadOnlyCardiBuddy> cardiBuddyStateList;
    private int currentStatePointer;

    public VersionedCardiBuddy(ReadOnlyCardiBuddy initialState) {
        super(initialState);

        cardiBuddyStateList = new ArrayList<>();
        cardiBuddyStateList.add(new CardiBuddy(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code CardiBuddy} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        cardiBuddyStateList.add(new CardiBuddy(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        cardiBuddyStateList.subList(currentStatePointer + 1, cardiBuddyStateList.size()).clear();
    }

    /**
     * Restores the CardiBuddy to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(cardiBuddyStateList.get(currentStatePointer));
    }

    /**
     * Restores the CardiBuddy to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(cardiBuddyStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has CardiBuddy states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has CardiBuddy states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < cardiBuddyStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedCardiBuddy)) {
            return false;
        }

        VersionedCardiBuddy otherVersionedCardiBuddy = (VersionedCardiBuddy) other;

        // state check
        return super.equals(otherVersionedCardiBuddy)
                && cardiBuddyStateList.equals(otherVersionedCardiBuddy.cardiBuddyStateList)
                && currentStatePointer == otherVersionedCardiBuddy.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of addressBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of addressBookState list, unable to redo.");
        }
    }
}
