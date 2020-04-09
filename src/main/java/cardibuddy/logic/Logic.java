package cardibuddy.logic;

import java.nio.file.Path;

import cardibuddy.commons.core.GuiSettings;
import cardibuddy.logic.commands.CommandResult;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.logic.parser.exceptions.ParseException;
import cardibuddy.model.ReadOnlyCardiBuddy;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Statistics;
import cardibuddy.model.flashcard.Card;
import javafx.collections.ObservableList;


/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see cardibuddy.model.Model#getCardiBuddy()
     */
    ReadOnlyCardiBuddy getCardiBuddy();

    /** Returns an unmodifiable view of the filtered list of decks */
    ObservableList<Deck> getFilteredDeckList();

    /** Returns an unmodifiable view of the filtered list of flashcards */
    ObservableList<Card> getFilteredFlashcardList();

    /**
     * Returns the Statistics of the Model.
     */
    Statistics getStatistics();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getCardiBuddyFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    void setLogicToUiManager(LogicToUiManager logicToUiManager);
}
