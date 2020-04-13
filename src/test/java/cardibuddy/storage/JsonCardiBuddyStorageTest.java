package cardibuddy.storage;

import static cardibuddy.testutil.Assert.assertThrows;
import static cardibuddy.testutil.TypicalDecks.ASYNCHRONOUS;
import static cardibuddy.testutil.TypicalDecks.HOON;
import static cardibuddy.testutil.TypicalDecks.IDA;
import static cardibuddy.testutil.TypicalDecks.getTypicalCardiBuddy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import cardibuddy.commons.exceptions.DataConversionException;
import cardibuddy.model.CardiBuddy;
import cardibuddy.model.ReadOnlyCardiBuddy;

public class JsonCardiBuddyStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonCardiBuddyStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readCardiBuddyNullFilePathThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCardiBuddy(null));
    }

    private Optional<ReadOnlyCardiBuddy> readCardiBuddy(String filePath) throws Exception {
        return new JsonCardiBuddyStorage(Paths.get(filePath)).readCardiBuddy(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void readMissingFileEmptyResult() throws Exception {
        assertFalse(readCardiBuddy("NonExistentFile.json").isPresent());
    }

    @Test
    public void readNotJsonFormatExceptionThrown() {
        assertThrows(DataConversionException.class, () -> readCardiBuddy("notJsonFormatCardiBuddy.json"));
    }

    @Test
    public void readCardiBuddyInvalidDeckCardiBuddyThrowDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCardiBuddy("invalidDeckCardiBuddy.json"));
    }

    @Test
    public void readAndSaveCardiBuddyAllInOrderSuccess() throws Exception {
        Path filePath = testFolder.resolve("TempCardiBuddy.json");
        CardiBuddy original = getTypicalCardiBuddy();
        JsonCardiBuddyStorage jsonCardiBuddyStorage = new JsonCardiBuddyStorage(filePath);

        // Save in new file and read back
        jsonCardiBuddyStorage.saveCardiBuddy(original, filePath);
        ReadOnlyCardiBuddy readBack = jsonCardiBuddyStorage.readCardiBuddy(filePath).get();
        assertEquals(original, new CardiBuddy(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addDeck(HOON);
        original.removeDeck(ASYNCHRONOUS);
        jsonCardiBuddyStorage.saveCardiBuddy(original, filePath);
        readBack = jsonCardiBuddyStorage.readCardiBuddy(filePath).get();
        assertEquals(original, new CardiBuddy(readBack));

        // Save and read without specifying file path
        original.addDeck(IDA);
        jsonCardiBuddyStorage.saveCardiBuddy(original); // file path not specified
        readBack = jsonCardiBuddyStorage.readCardiBuddy().get(); // file path not specified
        assertEquals(original, new CardiBuddy(readBack));

    }

    @Test
    public void saveCardiBuddyNullCardiBuddyThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCardiBuddy(null, "SomeFile.json"));
    }

    /**
     * Saves {@code cardiBuddy} at the specified {@code filePath}.
     */
    private void saveCardiBuddy(ReadOnlyCardiBuddy cardiBuddy, String filePath) {
        try {
            new JsonCardiBuddyStorage(Paths.get(filePath))
                    .saveCardiBuddy(cardiBuddy, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveCardiBuddyNullFilePathThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCardiBuddy(new CardiBuddy(), null));
    }
}

