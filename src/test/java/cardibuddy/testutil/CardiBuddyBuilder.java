package cardibuddy.testutil;

import cardibuddy.model.CardiBuddy;
import cardibuddy.model.deck.Deck;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code CardiBuddy ab = new CardiBuddyBuilder().withDeck("John", "Doe").build();}
 */
public class CardiBuddyBuilder {

    private CardiBuddy cardiBuddy;

    public CardiBuddyBuilder() {
        cardiBuddy = new CardiBuddy();
    }

    public CardiBuddyBuilder(CardiBuddy cardiBuddy) {
        this.cardiBuddy = cardiBuddy;
    }

    /**
     * Adds a new {@code Deck} to the {@code CardiBuddy} that we are building.
     */
    public CardiBuddyBuilder withDeck(Deck deck) {
        cardiBuddy.addDeck(deck);
        return this;
    }

    public CardiBuddy build() {
        return cardiBuddy;
    }
}
