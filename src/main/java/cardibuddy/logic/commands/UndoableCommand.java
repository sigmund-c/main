package cardibuddy.logic.commands;

import static cardibuddy.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.CardiBuddy;
import cardibuddy.model.Model;
import cardibuddy.model.ReadOnlyCardiBuddy;

/**
 * Represents the commands that can be undone/redone.
 */
public abstract class UndoableCommand extends Command {
    private ReadOnlyCardiBuddy previousCardiBuddy;

    protected abstract CommandResult executeUndoableCommand(Model model) throws CommandException;

    /**
     * Stores the current state of {@code model#archivedOrderBook}.
     */
    private void saveCardiBuddy(Model model) {
        requireNonNull(model);
        this.previousCardiBuddy = new CardiBuddy(model.getCardiBuddy());
    }

    /**
     * Reverts all the books to the state before this command
     * was executed and updates all the list to
     * show all customers, phones, orders, schedules and archived.
     */
    protected final void undo(Model model) {
        requireAllNonNull(model, previousCardiBuddy);

        model.setCardiBuddy(previousCardiBuddy);

        model.updateFilteredDeckList(Model.PREDICATE_SHOW_ALL_DECKS);
        model.updateFilteredFlashcardList(Model.PREDICATE_SHOW_ALL_FLASHCARDS);
    }

    /**
     * Executes the command and updates the filtered person
     * list to show all persons.
     */
    protected final void redo(Model model) {
        requireNonNull(model);
        try {
            executeUndoableCommand(model);
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }

        model.updateFilteredDeckList(Model.PREDICATE_SHOW_ALL_DECKS);
        model.updateFilteredFlashcardList(Model.PREDICATE_SHOW_ALL_FLASHCARDS);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        saveCardiBuddy(model);

        return executeUndoableCommand(model);
    }
}
