package cardibuddy.logic.commands;

import static cardibuddy.logic.commands.CommandTestUtil.*;
import static cardibuddy.testutil.TypicalIndexes.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cardibuddy.testutil.TypicalDecks.getTypicalCardiBuddy;

import cardibuddy.AppParameters;
import cardibuddy.MainApp;
import cardibuddy.commons.core.Config;
import cardibuddy.logic.*;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.flashcard.Card;
import cardibuddy.model.flashcard.SearchCardKeywordsPredicate;
import cardibuddy.storage.*;
import cardibuddy.ui.Ui;
import cardibuddy.ui.UiManager;
import org.junit.jupiter.api.Test;

import cardibuddy.commons.core.Messages;
import cardibuddy.commons.core.index.Index;
import cardibuddy.model.Model;
import cardibuddy.model.ModelManager;
import cardibuddy.model.UserPrefs;
import cardibuddy.model.deck.Deck;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCardCommandTest {
    private CommandHistory commandHistory = new CommandHistory();
    UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(Paths.get("preferences.json"));
    UserPrefs userPrefs = new UserPrefs();
    CardiBuddyStorage cardiBuddyStorage = new JsonCardiBuddyStorage(userPrefs.getCardiBuddyFilePath());
    private Storage storage = new StorageManager(cardiBuddyStorage, userPrefsStorage);
    private Model model = new ModelManager(getTypicalCardiBuddy(), new UserPrefs());
    private Logic logic = new LogicManager(model, storage);
    private Ui ui = new UiManager(logic);
    private LogicToUiManager logicToUiManager = new LogicToUiManager((UiManager) ui);

//    @Test
//    public void execute_validIndexUnfilteredList_success() {
//        Deck deckToDelete = model.getFilteredDeckList().get(INDEX_FIRST_DECK.getZeroBased());
//        DeleteDeckCommand deleteDeckCommand = new DeleteDeckCommand(INDEX_FIRST_DECK);
//
//        String expectedMessage = String.format(DeleteDeckCommand.MESSAGE_DELETE_DECK_SUCCESS, deckToDelete);
//
//        ModelManager expectedModel = new ModelManager(model.getCardiBuddy(), new UserPrefs());
//        expectedModel.deleteDeck(deckToDelete);
//        expectedModel.commitCardiBuddy();
//
//        assertNotEquals(expectedModel, model);
////        assertCommandSuccess(deleteDeckCommand, model, expectedMessage, expectedModel, commandHistory);
//    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDeckList().size() + 1);
        DeleteCardCommand deleteCardCommand = new DeleteCardCommand(outOfBoundIndex, logicToUiManager);

        assertCommandFailure(deleteCardCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

//    @Test
//    public void execute_validIndexFilteredList_success() {
//        showDeckAtIndex(model, INDEX_FIRST_DECK);
//
//        Deck deckToDelete = model.getFilteredDeckList().get(INDEX_FIRST_DECK.getZeroBased());
//        DeleteDeckCommand deleteDeckCommand = new DeleteDeckCommand(INDEX_FIRST_DECK, logicToUiManager);
//
//        String expectedMessage = String.format(DeleteDeckCommand.MESSAGE_DELETE_DECK_SUCCESS, deckToDelete);
//
//        Model expectedModel = new ModelManager(model.getCardiBuddy(), new UserPrefs());
//        expectedModel.deleteDeck(deckToDelete);
//        expectedModel.commitCardiBuddy();
//        showNoDeck(expectedModel);
//
//        assertCommandSuccess(deleteDeckCommand, model, expectedMessage, expectedModel, commandHistory);
//    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
//        showCardAtIndex(model, INDEX_FIRST_CARD);

        assertTrue(INDEX_FIRST_CARD.getZeroBased() < model.getFilteredFlashcardList().size());

        Card card = model.getFilteredFlashcardList().get(INDEX_FIRST_CARD.getZeroBased());
        final String[] splitName = card.getQuestion().toString().split("\\s+");
        model.updateFilteredFlashcardList(new SearchCardKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredFlashcardList().size());

        Index outOfBoundIndex = INDEX_SECOND_CARD;
        // ensures that outOfBoundIndex is still in bounds of cardibuddy list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCardiBuddy().getFlashcardList().size());

        DeleteCardCommand deleteCardCommand = new DeleteCardCommand(outOfBoundIndex, logicToUiManager);

        assertCommandFailure(deleteCardCommand, model, commandHistory, Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

//    @Test
//    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
//        Deck deckToDelete = model.getFilteredDeckList().get(INDEX_FIRST_DECK.getZeroBased());
//        DeleteDeckCommand deleteDeckCommand = new DeleteDeckCommand(INDEX_FIRST_DECK, logicToUiManager);
//        Model expectedModel = new ModelManager(model.getCardiBuddy(), new UserPrefs());
//        expectedModel.deleteDeck(deckToDelete);
//        expectedModel.commitCardiBuddy();
//
//        // delete -> first deck deleted
//        deleteDeckCommand.execute(model, commandHistory);
//
//        // undo -> reverts cardibuddy back to previous state and filtered person list to show all persons
//        expectedModel.undo();
//        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);
//
//        // redo -> same first deck deleted again
//        expectedModel.redo();
//        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);
//    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFlashcardList().size() + 1);
        DeleteCardCommand deleteCardCommand = new DeleteCardCommand(outOfBoundIndex, logicToUiManager);

        // execution failed -> cardibuddy state not added into model
        assertCommandFailure(deleteCardCommand, model, commandHistory, Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);

        // single cardibuddy state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, Messages.MESSAGE_NOTHING_TO_UNDO);
        assertCommandFailure(new RedoCommand(), model, commandHistory, Messages.MESSAGE_NOTHING_TO_REDO);
    }

    /**
     * 1. Deletes a {@code Person} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted deck in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the deck object regardless of indexing.
     */
//    @Test
//    public void executeUndoRedo_validIndexFilteredList_sameDeckDeleted() throws Exception {
//        DeleteDeckCommand deleteDeckCommand = new DeleteDeckCommand(INDEX_FIRST_DECK, logicToUiManager);
//        Model expectedModel = new ModelManager(model.getCardiBuddy(), new UserPrefs());
//
//        showDeckAtIndex(model, INDEX_SECOND_DECK);
//        Deck deckTodelete = model.getFilteredDeckList().get(INDEX_FIRST_DECK.getZeroBased());
//        expectedModel.deleteDeck(deckTodelete);
//        expectedModel.commitCardiBuddy();
//
//        // delete -> deletes second deck in unfiltered deck list / first deck in filtered deck list
//        deleteDeckCommand.execute(model, commandHistory);
//
//        // undo -> reverts cardibuddy back to previous state and filtered deck list to show all decks
//        expectedModel.undo();
//        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);
//
//        assertNotEquals(deckTodelete, model.getFilteredDeckList().get(INDEX_FIRST_DECK.getZeroBased()));
//        // redo -> deletes same second deck in unfiltered deck list
//        expectedModel.redo();
//        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel, commandHistory);
//    }

    @Test
    public void equals() {
        DeleteCardCommand deleteCardFirstCommand = new DeleteCardCommand(INDEX_FIRST_CARD, logicToUiManager);
        DeleteCardCommand deleteCardSecondCommand = new DeleteCardCommand(INDEX_SECOND_CARD, logicToUiManager);

        // same object -> returns true
        assertTrue(deleteCardFirstCommand.equals(deleteCardFirstCommand));

        // same values -> returns true
        DeleteCardCommand deleteCardFirstCommandCopy = new DeleteCardCommand(INDEX_FIRST_CARD, logicToUiManager);
        assertTrue(deleteCardFirstCommand.equals(deleteCardFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteCardFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteCardFirstCommand.equals(null));

        // different deck -> returns false
        assertFalse(deleteCardFirstCommand.equals(deleteCardSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoCard(Model model) {
        model.updateFilteredFlashcardList(p -> false);

        assertTrue(model.getFilteredFlashcardList().isEmpty());
    }
}
