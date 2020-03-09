package cardibuddy.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import cardibuddy.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path cardibuddyFilePath = Paths.get("data" , "cardibuddy.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setCardiBuddyFilePath(newUserPrefs.getCardiBuddyFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getCardiBuddyFilePath() {
        return cardibuddyFilePath;
    }

    public void setCardiBuddyFilePath(Path cardibuddyFilePath) {
        requireNonNull(cardibuddyFilePath);
        this.cardibuddyFilePath = cardibuddyFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof cardibuddy.model.UserPrefs)) { //this handles null as well.
            return false;
        }

        cardibuddy.model.UserPrefs o = (cardibuddy.model.UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && cardibuddyFilePath.equals(o.cardibuddyFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, cardibuddyFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + cardibuddyFilePath);
        return sb.toString();
    }

}
