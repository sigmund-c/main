package cardibuddy.storage;

import static cardibuddy.testutil.TypicalDecks.getTypicalDecks;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import cardibuddy.commons.core.GuiSettings;
import cardibuddy.model.CardiBuddy;
import cardibuddy.model.ReadOnlyCardiBuddy;
import cardibuddy.model.UserPrefs;
import cardibuddy.model.deck.Deck;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonCardiBuddyStorage cardiBuddyStorage = new JsonCardiBuddyStorage(getTempFilePath("cb"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(cardiBuddyStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        // Note: This is to make sure StorageManager is properly wired to the {@link JsonUserPrefsStorage} class.
        // More extensive testing of UserPrefs reading/saving is done in {@link JsonUserPrefsStorageTest}
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void cardiBuddyReadSave() throws Exception {
        // Note: This is to make sure StorageManager is properly wired to the {@link JsonCardiBuddyStorage} class.
        // More extensive testing of CardiBuddy reading/saving is done in {@link JsonCardiBuddyStorageTest}
        CardiBuddy original = new CardiBuddy();
        for (Deck deck: getTypicalDecks()) {
            original.addDeck(deck);
        }
        storageManager.saveCardiBuddy(original);
        ReadOnlyCardiBuddy retrieved = storageManager.readCardiBuddy().get();
        assertEquals(original, new CardiBuddy(retrieved));
    }
}
