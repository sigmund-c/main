package cardibuddy.logic.commands;

import static cardibuddy.logic.commands.CommandTestUtil.assertCommandFailure;
import static cardibuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static cardibuddy.logic.commands.CommandTestUtil.showDeckAtIndex;
import static cardibuddy.logic.commands.CommandTestUtil.DESC_DJANGO;
import static cardibuddy.logic.commands.CommandTestUtil.DESC_REACT;
import static cardibuddy.logic.commands.CommandTestUtil.VALID_TAG_HARD;
import static cardibuddy.logic.commands.CommandTestUtil.VALID_TITLE_DJANGO;
import static cardibuddy.testutil.TypicalIndexes.INDEX_FIRST_DECK;
import static cardibuddy.testutil.TypicalIndexes.INDEX_SECOND_DECK;
import static cardibuddy.testutil.TypicalDecks.getTypicalCardiBuddy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import cardibuddy.commons.core.index.Index;
import cardibuddy.commons.core.Messages;
import cardibuddy.logic.CommandHistory;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.CardiBuddy;
import cardibuddy.model.Model;
import cardibuddy.model.ModelManager;
import cardibuddy.model.UserPrefs;
import cardibuddy.testutil.DeckBuilder;
import cardibuddy.testutil.EditDeckDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditDeckCommandTest {

    private Model model = new ModelManager(getTypicalCardiBuddy(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Deck editedDeck = new DeckBuilder().build();
        EditDeckCommand.EditDeckDescriptor editDeckDescriptor = new EditDeckDescriptorBuilder(editedDeck).build();
        EditCommand editCommand = new EditDeckCommand(INDEX_FIRST_DECK, editDeckDescriptor);

        String expectedMessage = String.format(EditDeckCommand.MESSAGE_EDIT_DECK_SUCCESS, editedDeck);

        Model expectedModel = new ModelManager(new CardiBuddy(model.getCardiBuddy()), new UserPrefs());
        expectedModel.setDeck(model.getFilteredDeckList().get(0), editedDeck);
        expectedModel.commitCardiBuddy();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, commandHistory);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastDeck = Index.fromOneBased(model.getFilteredDeckList().size());
        Deck lastDeck = model.getFilteredDeckList().get(indexLastDeck.getZeroBased());

        DeckBuilder deckInList = new DeckBuilder(lastDeck);
        Deck editedDeck = deckInList.withTitle(VALID_TITLE_DJANGO).withTags(VALID_TAG_HARD)
                .build();

        EditDeckCommand.EditDeckDescriptor descriptor = new EditDeckDescriptorBuilder().withTitle(VALID_TITLE_DJANGO)
                .withTags(VALID_TAG_HARD).build();
        EditDeckCommand editCommand = new EditDeckCommand(indexLastDeck, descriptor);

        String expectedMessage = String.format(EditDeckCommand.MESSAGE_EDIT_DECK_SUCCESS, editedDeck);

        Model expectedModel = new ModelManager(new CardiBuddy(model.getCardiBuddy()), new UserPrefs());
        expectedModel.setDeck(lastDeck, editedDeck);
        expectedModel.commitCardiBuddy();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, commandHistory);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditDeckCommand editCommand = new EditDeckCommand(INDEX_FIRST_DECK, new EditDeckCommand.EditDeckDescriptor());
        Deck editedDeck = model.getFilteredDeckList().get(INDEX_FIRST_DECK.getZeroBased());

        String expectedMessage = String.format(EditDeckCommand.MESSAGE_EDIT_DECK_SUCCESS, editedDeck);

        Model expectedModel = new ModelManager(new CardiBuddy(model.getCardiBuddy()), new UserPrefs());
        expectedModel.commitCardiBuddy();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, commandHistory);
    }

    @Test
    public void execute_filteredList_success() {
        showDeckAtIndex(model, INDEX_FIRST_DECK);

        Deck deckInFilteredList = model.getFilteredDeckList().get(INDEX_FIRST_DECK.getZeroBased());
        Deck editedDeck = new DeckBuilder(deckInFilteredList).withTitle(VALID_TITLE_DJANGO).build();
        EditCommand editCommand = new EditDeckCommand(INDEX_FIRST_DECK,
                new EditDeckDescriptorBuilder().withTitle(VALID_TITLE_DJANGO).build());

        String expectedMessage = String.format(EditDeckCommand.MESSAGE_EDIT_DECK_SUCCESS, editedDeck);

        Model expectedModel = new ModelManager(new CardiBuddy(model.getCardiBuddy()), new UserPrefs());
        expectedModel.setDeck(model.getFilteredDeckList().get(0), editedDeck);
        expectedModel.commitCardiBuddy();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, commandHistory);
    }

    @Test
    public void execute_duplicateDeckUnfilteredList_failure() {
        Deck firstDeck = model.getFilteredDeckList().get(INDEX_FIRST_DECK.getZeroBased());
        EditDeckCommand.EditDeckDescriptor descriptor = new EditDeckDescriptorBuilder(firstDeck).build();
        EditDeckCommand editCommand = new EditDeckCommand(INDEX_SECOND_DECK, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditDeckCommand.MESSAGE_DUPLICATE_DECK);
    }

    @Test
    public void execute_duplicateDeckFilteredList_failure() {
        showDeckAtIndex(model, INDEX_SECOND_DECK);

        // edit deck in filtered list into a duplicate in cardibuddy
        Deck deckInList = model.getCardiBuddy().getDeckList().get(INDEX_SECOND_DECK.getZeroBased());
        EditDeckCommand editCommand = new EditDeckCommand(INDEX_FIRST_DECK,
                new EditDeckDescriptorBuilder(deckInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditDeckCommand.MESSAGE_DUPLICATE_DECK);
    }

    @Test
    public void execute_invalidDeckIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDeckList().size() + 1);
        EditDeckCommand.EditDeckDescriptor descriptor = new EditDeckDescriptorBuilder().withTitle(VALID_TITLE_DJANGO)
                .build();
        EditDeckCommand editCommand = new EditDeckCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of cardibuddy
     */
    @Test
    public void execute_invalidDeckIndexFilteredList_failure() {
        showDeckAtIndex(model, INDEX_FIRST_DECK);
        Index outOfBoundIndex = INDEX_SECOND_DECK;
        // ensures that outOfBoundIndex is still in bounds of cardibuddy list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCardiBuddy().getDeckList().size());

        EditDeckCommand editCommand = new EditDeckCommand(outOfBoundIndex,
                new EditDeckDescriptorBuilder().withTitle(VALID_TITLE_DJANGO).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDeckList().size() + 1);
        EditDeckCommand.EditDeckDescriptor descriptor = new EditDeckDescriptorBuilder().withTitle(VALID_TITLE_DJANGO).build();
        EditDeckCommand editCommand = new EditDeckCommand(outOfBoundIndex, descriptor);

        // execution failed -> cardibuddy state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);

        // single cardibuddy state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, Messages.MESSAGE_NOTHING_TO_UNDO);
        assertCommandFailure(new RedoCommand(), model, commandHistory, Messages.MESSAGE_NOTHING_TO_REDO);
    }

    @Test
    public void equals() {
        final EditDeckCommand standardCommand = new EditDeckCommand(INDEX_FIRST_DECK, DESC_DJANGO);

        // same values -> returns true
        EditDeckCommand.EditDeckDescriptor copyDescriptor = new EditDeckCommand.EditDeckDescriptor(DESC_DJANGO);
        EditDeckCommand commandWithSameValues = new EditDeckCommand(INDEX_FIRST_DECK, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditDeckCommand(INDEX_SECOND_DECK, DESC_DJANGO)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditDeckCommand(INDEX_FIRST_DECK, DESC_REACT)));
    }
}
