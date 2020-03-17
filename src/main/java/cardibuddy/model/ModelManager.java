package cardibuddy.model;

import static cardibuddy.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import cardibuddy.commons.core.GuiSettings;
import cardibuddy.commons.core.LogsCenter;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Flashcard;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;


/**
 * Represents the in-memory model of the cardibuddy data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(cardibuddy.model.ModelManager.class);

    private final CardiBuddy cardiBuddy;
    private final UserPrefs userPrefs;
    private final FilteredList<Flashcard> filteredFlashcards;
    private final FilteredList<Deck> filteredDecks;

    /**
     * Initializes a ModelManager with the given cardiBuddy and userPrefs.
     */
    public ModelManager(ReadOnlyCardiBuddy cardiBuddy, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(cardiBuddy, userPrefs);

        logger.fine("Initializing with CardiBuddy: " + cardiBuddy + " and user prefs " + userPrefs);

        this.cardiBuddy = new CardiBuddy(cardiBuddy);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredFlashcards = new FilteredList<>(this.cardiBuddy.getFlashcardList());
        filteredDecks = new FilteredList<>(this.cardiBuddy.getDeckList());
    }

    public ModelManager() {
        this(new CardiBuddy(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getCardiBuddyFilePath() {
        return userPrefs.getCardiBuddyFilePath();
    }

    @Override
    public void setCardiBuddyFilePath(Path cardiBuddyFilePath) {
        requireNonNull(cardiBuddyFilePath);
        userPrefs.setCardiBuddyFilePath(cardiBuddyFilePath);
    }

    //=========== CardiBuddy ================================================================================

    @Override
    public void setCardiBuddy(ReadOnlyCardiBuddy cardiBuddy) {
        this.cardiBuddy.resetData(cardiBuddy);
    }

    @Override
    public ReadOnlyCardiBuddy getCardiBuddy() {
        return cardiBuddy;
    }

    @Override
    public boolean hasDeck(Deck deck) {
        requireNonNull(deck);
        return cardiBuddy.hasDeck(deck);
    }

    @Override
    public void deleteDeck(Deck target) {
        cardiBuddy.removeDeck(target);
    }

    @Override
    public void addDeck(Deck deck) {
        cardiBuddy.addDeck(deck);
        updateFilteredDeckList(PREDICATE_SHOW_ALL_DECKS);
    }

    @Override
    public void setDeck(Deck target, Deck editedDeck) {
        cardiBuddy.setDeck(target, editedDeck);
    }

    @Override
    public boolean hasFlashcard(Flashcard flashcard) {
        requireNonNull(flashcard);
        return cardiBuddy.hasFlashcard(flashcard);
    }

    @Override
    public void deleteFlashcard(Flashcard target) {
        cardiBuddy.removeFlashcard(target);
    }

    /**
     * Adds Flashcard to a Deck.
     * @param flashcard new card.
     */
    @Override
    public void addFlashcard(Flashcard flashcard) {
        cardiBuddy.addFlashcard(flashcard);
        updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
    }

    @Override
    public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
        requireAllNonNull(target, editedFlashcard);

        cardiBuddy.setFlashcard(target, editedFlashcard);
    }

    /**
     * Starts a test session // TODO see how to update the list
     * @param deck the deck to be tested
     */
    @Override
    public void testDeck(Deck deck) {
    }

    //=========== Filtered Flashcard List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Deck} backed by the internal list of
     * {@code versionedCardiBuddy}
     */
    @Override
    public ObservableList<Deck> getFilteredDeckList() {
        return filteredDecks;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Flashcard} backed by the internal list of
     * {@code versionedCardiBuddy}
     */
    @Override
    public ObservableList<Flashcard> getFilteredFlashcardList() {
        return filteredFlashcards;
    }

    @Override
    public void updateFilteredDeckList(Predicate<Deck> predicate) {
        requireNonNull(predicate);
        filteredDecks.setPredicate(predicate);
    }

    @Override
    public void updateFilteredFlashcardList(Predicate<Flashcard> predicate) {
        requireNonNull(predicate);
        filteredFlashcards.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof cardibuddy.model.ModelManager)) {
            return false;
        }

        // state check
        cardibuddy.model.ModelManager other = (cardibuddy.model.ModelManager) obj;
        return cardiBuddy.equals(other.cardiBuddy)
                && userPrefs.equals(other.userPrefs)
                && filteredDecks.equals(other.filteredDecks)
                && filteredFlashcards.equals(other.filteredFlashcards);
    }

}
