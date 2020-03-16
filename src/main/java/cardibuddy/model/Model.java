package cardibuddy.model;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Flashcard;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import cardibuddy.commons.core.GuiSettings;
import cardibuddy.model.flashcard.Flashcard;


/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Flashcard> PREDICATE_SHOW_ALL_FLASHCARDS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getCardiBuddyFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setCardiBuddyFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setCardiBuddy(ReadOnlyCardiBuddy addressBook);

    /** Returns the CardiBuddy */
    ReadOnlyCardiBuddy getCardiBuddy();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasDeck(Deck deck);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deleteDeck(Deck target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addDeck(Deck person);

    /**
     * Replaces the given person {@code target} with {@code editedFlashcard}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedFlashcard} must not be the same as another existing person in the address book.
     */
    void setDeck(Flashcard target, Flashcard editedFlashcard);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Flashcard> getFilteredFlashcardList();

    ObservableList<Flashcard> getFilteredDeckList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFlashcardList(Predicate<Flashcard> predicate);
}

