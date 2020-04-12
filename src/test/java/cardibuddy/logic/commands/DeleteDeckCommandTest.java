package cardibuddy.logic.commands;

import static cardibuddy.testutil.TypicalIndexes.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cardibuddy.logic.commands.CommandTestUtil.assertCommandFailure;
import static cardibuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static cardibuddy.logic.commands.CommandTestUtil.showDeckAtIndex;
import static cardibuddy.testutil.TypicalDecks.getTypicalCardiBuddy;

import cardibuddy.logic.Logic;
import cardibuddy.logic.LogicManager;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.storage.*;
import cardibuddy.ui.UiManager;
import org.junit.jupiter.api.Test;

import cardibuddy.commons.core.Messages;
import cardibuddy.commons.core.index.Index;
import cardibuddy.logic.CommandHistory;
import cardibuddy.model.Model;
import cardibuddy.model.ModelManager;
import cardibuddy.model.UserPrefs;
import cardibuddy.model.deck.Deck;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteDeckCommandTest {
    private Model model = new ModelManager(getTypicalCardiBuddy(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private CommandHistory commandHistory = new CommandHistory();
    private Path userPrefsFilePath = Paths.get("preferences.json");
    private UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsFilePath);
    private Path cardibuddyFilePath = Paths.get("data" , "cardibuddy.json");
    private CardiBuddyStorage cardiBuddyStorage = new JsonCardiBuddyStorage(cardibuddyFilePath);
    private Storage storage = new StorageManager(cardiBuddyStorage, userPrefsStorage);
    private Logic logic = new LogicManager(model, storage);
    private UiManager ui = new UiManager(logic);
    private LogicToUiManager logicToUiManager = new LogicToUiManager(ui);

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Deck deckToDelete = model.getFilteredDeckList().get(INDEX_FIRST_DECK.getZeroBased());
        DeleteDeckCommand deleteCommand = new DeleteDeckCommand(INDEX_FIRST_DECK, logicToUiManager);

        String expectedMessage = String.format(DeleteDeckCommand.MESSAGE_DELETE_DECK_SUCCESS, deckToDelete);

        ModelManager expectedModel = new ModelManager(model.getCardiBuddy(), new UserPrefs());
        expectedModel.deleteDeck(deckToDelete);
        expectedModel.commitCardiBuddy();

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel, commandHistory);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDeckList().size() + 1);
        DeleteDeckCommand deleteDeckCommand = new DeleteDeckCommand(outOfBoundIndex, logicToUiManager);

        assertCommandFailure(deleteDeckCommand, model, commandHistory, Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showDeckAtIndex(model, INDEX_FIRST_DECK);

        Deck deckToDelete = model.getFilteredDeckList().get(INDEX_FIRST_DECK.getZeroBased());
        DeleteDeckCommand deleteDeckCommand = new DeleteDeckCommand(INDEX_FIRST_DECK);

        String expectedMessage = String.format(DeleteDeckCommand.MESSAGE_DELETE_DECK_SUCCESS, deckToDelete);

        Model expectedModel = new ModelManager(model.getCardiBuddy(), new UserPrefs());
        expectedModel.deleteDeck(deckToDelete);
        expectedModel.commitCardiBuddy();
        showNoDeck(expectedModel);

        assertCommandSuccess(deleteDeckCommand, model, expectedMessage, expectedModel, commandHistory);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showDeckAtIndex(model, INDEX_FIRST_DECK);

        Index outOfBoundIndex = INDEX_SECOND_DECK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCardiBuddy().getDeckList().size());

        DeleteDeckCommand deleteDeckCommand = new DeleteDeckCommand(outOfBoundIndex, logicToUiManager);

        assertCommandFailure(deleteDeckCommand, model, commandHistory, Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Deck deckToDelete = model.getFilteredDeckList().get(INDEX_FIRST_DECK.getZeroBased());
        DeleteDeckCommand deleteDeckCommand = new DeleteDeckCommand(INDEX_FIRST_DECK);
        Model expectedModel = new ModelManager(model.getCardiBuddy(), new UserPrefs());
        expectedModel.deleteDeck(deckToDelete);
        expectedModel.commitAddressBook();

        // delete -> first person deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Person} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.deletePerson(personToDelete);
        expectedModel.commitAddressBook();

        // delete -> deletes second person in unfiltered person list / first person in filtered person list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(personToDelete, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> deletes same second person in unfiltered person list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
