package cardibuddy.model.testsession;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Card;
import cardibuddy.model.flashcard.Question;
import cardibuddy.model.flashcard.UniqueFlashcardList;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DeckStub extends Deck {
    List<Card> flashcards;

    public DeckStub(int option) {
        flashcards = new ArrayList<>();
        switch(option){
        case 0:
            break;
        case 1:
            flashcards.add(new CardStub(0));
        case 2:
            flashcards.add(new CardStub(1));
            flashcards.add(new CardStub(2));
            flashcards.add(new CardStub(3));
            break;
        case 3: // more cards
            flashcards.add(new CardStub(0));
            flashcards.add(new CardStub(1));
            flashcards.add(new CardStub(2));
            flashcards.add(new CardStub(3));
            flashcards.add(new CardStub())
        }
    }

    public LinkedList<Card> getExpectedTestQueue() {
        return new LinkedList<>(flashcards);
    }

    public Card getFirstExpectedCard() {
        return flashcards.get(0);
    }

    public List<Card> getFlashcardArrayList() {
        return flashcards;
    }

    @Override
    public ObservableList<Card> getFlashcardList() {
        UniqueFlashcardList flashcardList =  new UniqueFlashcardList();
        flashcardList.setFlashcards(flashcards);
        return flashcardList.asUnmodifiableObservableList();
    }
}
