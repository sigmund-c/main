package cardibuddy.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import cardibuddy.commons.core.LogsCenter;
import cardibuddy.commons.exceptions.DataConversionException;
import cardibuddy.model.ReadOnlyCardiBuddy;
import cardibuddy.model.ReadOnlyUserPrefs;
import cardibuddy.model.UserPrefs;

/**
 * Manages storage of CardiBuddy data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(cardibuddy.storage.StorageManager.class);
    private CardiBuddyStorage cardiBuddyStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(CardiBuddyStorage cardiBuddyStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.cardiBuddyStorage = cardiBuddyStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ CardiBuddy methods ==============================

    @Override
    public Path getCardiBuddyFilePath() {
        return cardiBuddyStorage.getCardiBuddyFilePath();
    }

    @Override
    public Optional<ReadOnlyCardiBuddy> readCardiBuddy() throws DataConversionException, IOException {
        return readCardiBuddy(cardiBuddyStorage.getCardiBuddyFilePath());
    }

    @Override
    public Optional<ReadOnlyCardiBuddy> readCardiBuddy(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return cardiBuddyStorage.readCardiBuddy(filePath);
    }

    @Override
    public void saveCardiBuddy(ReadOnlyCardiBuddy cardiBuddy) throws IOException {
        saveCardiBuddy(cardiBuddy, cardiBuddyStorage.getCardiBuddyFilePath());
    }

    @Override
    public void saveCardiBuddy(ReadOnlyCardiBuddy cardiBuddy, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        cardiBuddyStorage.saveCardiBuddy(cardiBuddy, filePath);
    }

}
