package cardibuddy.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import cardibuddy.commons.exceptions.IllegalValueException;
import cardibuddy.model.CardiBuddy;
import cardibuddy.model.ReadOnlyCardiBuddy;
import cardibuddy.model.deck.Deck;

/**
 * An Immutable CardiBuddy that is serializable to JSON format.
 */
@JsonRootName(value = "cardibuddy")
class JsonSerializableCardiBuddy {

    public static final String MESSAGE_DUPLICATE_DECK = "Decks list contains duplicate deck(s).";

    private final List<JsonAdaptedDeck> decks = new ArrayList<>();
    private final List<JsonAdaptedFlashcard> flashcards = new ArrayList<>();
    private final JsonAdaptedStatistic statistics;
    /**
     * Constructs a {@code JsonSerializableCardiBuddy} with the given flashcards.
     */
    @JsonCreator
    public JsonSerializableCardiBuddy(@JsonProperty("decks") List<JsonAdaptedDeck> decks,
                                      @JsonProperty("statistics") JsonAdaptedStatistic statistics) {
        this.decks.addAll(decks);
        this.statistics = statistics;
    }

    /**
     * Converts a given {@code ReadOnlyCardiBuddy} into this class for Jackson use.
     * @param source future changes to this will not affect the created {@code JsonSerializableCardiBuddy}.
     */
    public JsonSerializableCardiBuddy(ReadOnlyCardiBuddy source) {
        decks.addAll(source.getDeckList().stream()
            .map(JsonAdaptedDeck::new).collect(Collectors.toList()));
        statistics = new JsonAdaptedStatistic(source.getStatistics());
    }


    /**
    * Converts this address book into the model's {@code CardiBuddy} object.
    *
    * @throws IllegalValueException if there were any data constraints violated.
    * */
    public CardiBuddy toModelType() throws IllegalValueException {
        CardiBuddy cardibuddy = new CardiBuddy();
        for (JsonAdaptedDeck jsonAdaptedDeck : decks) {
            Deck deck = jsonAdaptedDeck.toModelType();
            if (cardibuddy.hasDeck(deck)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DECK);
            }
            cardibuddy.addDeck(deck);
        }
        cardibuddy.setStatistics(statistics.toModeltype());
        return cardibuddy;
    }
}
