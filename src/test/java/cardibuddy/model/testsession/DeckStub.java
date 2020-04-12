package cardibuddy.model.testsession;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Title;
import cardibuddy.model.flashcard.*;
import cardibuddy.model.tag.Tag;
import javafx.collections.ObservableList;

import java.util.*;

public class DeckStub extends Deck {
    List<Card> flashcards;

    public DeckStub(Title title, Set<Tag> tags) {
        super(title, tags);
        flashcards = new ArrayList<>();
    }

    @Override
    public List<Card> addFlashcard(Flashcard card) {
        flashcards.add(card);
        return flashcards;
    }

    @Override
    public ObservableList<Card> getFlashcardList() {
        UniqueFlashcardList flashcardList =  new UniqueFlashcardList();
        flashcardList.setFlashcards(flashcards);
        return flashcardList.asUnmodifiableObservableList();
    }
}
