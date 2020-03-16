package cardibuddy.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import cardibuddy.commons.exceptions.DataConversionException;
import cardibuddy.model.ReadOnlyCardiBuddy;
import cardibuddy.model.ReadOnlyUserPrefs;
import cardibuddy.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends CardiBuddyStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getCardiBuddyFilePath();

    @Override
    Optional<ReadOnlyCardiBuddy> readCardiBuddy() throws DataConversionException, IOException;

    @Override
    void saveCardiBuddy(ReadOnlyCardiBuddy cardiBuddy) throws IOException;

}
