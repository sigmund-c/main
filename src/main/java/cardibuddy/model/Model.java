package cardibuddy.model;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Flashcard;

import javafx.collections.transformation.FilteredList;

import java.util.function.Predicate;

public class Model {

    public boolean hasDeck(Deck deck) {
        return true;
    }

    public boolean hasCard(Flashcard card) {
        return true;
    }

    public void addDeck(Deck deck) {

    }

    public void addCard(Flashcard card) {

    }

    public void deleteDeck(Deck target) {

    }

    public void deleteCard(Flashcard target) {

    }
}
