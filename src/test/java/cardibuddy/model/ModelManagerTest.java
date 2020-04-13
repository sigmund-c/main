package cardibuddy.model;

import static cardibuddy.model.Model.PREDICATE_SHOW_ALL_DECKS;
import static cardibuddy.testutil.Assert.assertThrows;
import static cardibuddy.testutil.TypicalDecks.ASYNCHRONOUS;
import static cardibuddy.testutil.TypicalDecks.POSTGRESQL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import cardibuddy.commons.core.GuiSettings;
import cardibuddy.model.deck.SearchDeckKeywordsPredicate;
import cardibuddy.testutil.CardiBuddyBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new CardiBuddy(), new CardiBuddy(modelManager.getCardiBuddy()));
    }

    @Test
    public void setUserPrefsNullUserPrefsThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefsValidUserPrefsCopiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setCardiBuddyFilePath(Paths.get("cardi/buddy/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setCardiBuddyFilePath(Paths.get("new/cardi/buddy/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettingsNullGuiSettingsThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettingsValidGuiSettingsSetsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setCardiBuddyFilePathNullPathThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setCardiBuddyFilePath(null));
    }

    @Test
    public void setCardiBuddyFilePathValidPathSetsCardiBuddyFilePath() {
        Path path = Paths.get("cardi/buddy/file/path");
        modelManager.setCardiBuddyFilePath(path);
        assertEquals(path, modelManager.getCardiBuddyFilePath());
    }

    @Test
    public void hasDeckNullDeckThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasDeck(null));
    }

    @Test
    public void hasDeckDeckNotInCardiBuddyReturnsFalse() {
        assertFalse(modelManager.hasDeck(ASYNCHRONOUS));
    }

    @Test
    public void hasDeckDeckInCardiBuddyReturnsTrue() {
        modelManager.addDeck(ASYNCHRONOUS);
        assertTrue(modelManager.hasDeck(ASYNCHRONOUS));
    }

    @Test
    public void getFilteredDeckListModifyListThrowsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredDeckList().remove(0));
    }

    @Test
    public void equals() {
        CardiBuddy cardiBuddy = new CardiBuddyBuilder().withDeck(ASYNCHRONOUS).withDeck(POSTGRESQL).build();
        CardiBuddy differentCardiBuddy = new CardiBuddy();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(cardiBuddy, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(cardiBuddy, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different cardiBuddy -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentCardiBuddy, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ASYNCHRONOUS.getTitle().toString().split("\\s+");
        modelManager.updateFilteredDeckList(new SearchDeckKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(cardiBuddy, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredDeckList(PREDICATE_SHOW_ALL_DECKS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setCardiBuddyFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(cardiBuddy, differentUserPrefs)));
    }
}

