package cardibuddy.storage;

import static cardibuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import cardibuddy.commons.exceptions.IllegalValueException;
import cardibuddy.commons.util.JsonUtil;
import cardibuddy.model.CardiBuddy;
import cardibuddy.testutil.TypicalDecks;

public class JsonSerializableCardiBuddyTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableCardiBuddyTest");
    private static final Path TYPICAL_DECKS_FILE = TEST_DATA_FOLDER.resolve("typicalDecksCardiBuddy.json");
    private static final Path DUPLICATE_DECK_FILE = TEST_DATA_FOLDER.resolve("duplicateDeckCardiBuddy.json");

    @Test
    public void toModelType_typicalDecksFile_success() throws Exception {
        JsonSerializableCardiBuddy dataFromFile = JsonUtil.readJsonFile(TYPICAL_DECKS_FILE,
                JsonSerializableCardiBuddy.class).get();
        CardiBuddy cardiBuddyFromFile = dataFromFile.toModelType();
        CardiBuddy typicalDecksCardiBuddy = TypicalDecks.getTypicalCardiBuddy();
        assertEquals(cardiBuddyFromFile, typicalDecksCardiBuddy);
    }

    @Test
    public void toModelType_duplicateDecks_throwsIllegalValueException() throws Exception {
        JsonSerializableCardiBuddy dataFromFile = JsonUtil.readJsonFile(DUPLICATE_DECK_FILE,
                JsonSerializableCardiBuddy.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableCardiBuddy.MESSAGE_DUPLICATE_DECK,
                dataFromFile::toModelType);
    }

}
