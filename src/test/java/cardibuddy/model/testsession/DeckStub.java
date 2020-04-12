package cardibuddy.model.testsession;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Title;
import cardibuddy.model.flashcard.Card;
import cardibuddy.model.flashcard.UniqueFlashcardList;
import cardibuddy.model.tag.Tag;
import javafx.collections.ObservableList;

/**
 * A stub class for Deck.
 */
public class DeckStub extends Deck {
    private List<Card> flashcards;

    public DeckStub(Title title, Set<Tag> tags) {
        super(title, tags);
        flashcards = new ArrayList<>();
    }

    @Override
    public List<Card> addCard(Card card) {
        flashcards.add(card);
        return flashcards;
    }

    @Override
    public ObservableList<Card> getFlashcardList() {
        UniqueFlashcardList flashcardList = new UniqueFlashcardList();
        flashcardList.setFlashcards(flashcards);
        return flashcardList.asUnmodifiableObservableList();
    }
}
