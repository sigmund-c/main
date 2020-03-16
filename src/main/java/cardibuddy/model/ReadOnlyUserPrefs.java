package cardibuddy.model;

import java.nio.file.Path;

import cardibuddy.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getCardiBuddyFilePath();

}
