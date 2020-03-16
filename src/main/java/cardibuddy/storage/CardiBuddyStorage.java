package cardibuddy.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import cardibuddy.commons.exceptions.DataConversionException;
import cardibuddy.model.ReadOnlyCardiBuddy;

/**
 * Represents a storage for {@link cardibuddy.model.CardiBuddy}.
 */
public interface CardiBuddyStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCardiBuddyFilePath();

    /**
     * Returns CardiBuddy data as a {@link ReadOnlyCardiBuddy}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCardiBuddy> readCardiBuddy() throws DataConversionException, IOException;

    /**
     * @see #getCardiBuddyFilePath()
     */
    Optional<ReadOnlyCardiBuddy> readCardiBuddy(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCardiBuddy} to the storage.
     * @param cardiBuddy cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCardiBuddy(ReadOnlyCardiBuddy cardiBuddy) throws IOException;

    /**
     * @see #saveCardiBuddy(ReadOnlyCardiBuddy)
     */
    void saveCardiBuddy(ReadOnlyCardiBuddy cardiBuddy, Path filePath) throws IOException;

}
