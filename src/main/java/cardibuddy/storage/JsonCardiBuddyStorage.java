package cardibuddy.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import cardibuddy.commons.core.LogsCenter;
import cardibuddy.commons.exceptions.DataConversionException;
import cardibuddy.commons.exceptions.IllegalValueException;
import cardibuddy.commons.util.FileUtil;
import cardibuddy.commons.util.JsonUtil;
import cardibuddy.model.ReadOnlyCardiBuddy;

/**
 * A class to access CardiBuddy data stored as a json file on the hard disk.
 */
public class JsonCardiBuddyStorage implements CardiBuddyStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCardiBuddyStorage.class);

    private Path filePath;

    public JsonCardiBuddyStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getCardiBuddyFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCardiBuddy> readCardiBuddy() throws DataConversionException {
        return readCardiBuddy(filePath);
    }

    /**
     * Similar to {@link #readCardiBuddy()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCardiBuddy> readCardiBuddy(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableCardiBuddy> jsonCardiBuddy = JsonUtil.readJsonFile(
                filePath, JsonSerializableCardiBuddy.class);
        if (!jsonCardiBuddy.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCardiBuddy.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCardiBuddy(ReadOnlyCardiBuddy cardiBuddy) throws IOException {
        saveCardiBuddy(cardiBuddy, filePath);
    }

    /**
     * Similar to {@link #saveCardiBuddy(ReadOnlyCardiBuddy)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveCardiBuddy(ReadOnlyCardiBuddy cardiBuddy, Path filePath) throws IOException {
        requireNonNull(cardiBuddy);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new cardibuddy.storage.JsonSerializableCardiBuddy(cardiBuddy), filePath);
    }

}
