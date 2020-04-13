package cardibuddy.logic.parser;

import static cardibuddy.commons.core.Messages.MESSAGE_NOT_IN_DECK;
import static cardibuddy.commons.core.Messages.MESSAGE_WRONG_DECK;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_ANSWER;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_FLASHCARD;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_QUESTION;
import static java.util.Objects.requireNonNull;

import cardibuddy.commons.core.index.Index;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.AddFlashcardCommand;
import cardibuddy.logic.parser.exceptions.ParseException;
import cardibuddy.model.ReadOnlyCardiBuddy;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Flashcard;
import cardibuddy.model.flashcard.Question;
import javafx.collections.ObservableList;

/**
 * Parses input arguments and creates a new AddFlashcardCommand object.
 */
public class AddFlashcardCommandParser extends AddCardCommandParser {

    private LogicToUiManager logicToUiManager;
    private ReadOnlyCardiBuddy cardiBuddy;

    public AddFlashcardCommandParser(ReadOnlyCardiBuddy cardiBuddy, LogicToUiManager logicToUiManager) {
        this.logicToUiManager = logicToUiManager;
        this.cardiBuddy = cardiBuddy;
    }

    @Override
    public AddFlashcardCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FLASHCARD,
                        PREFIX_QUESTION, PREFIX_ANSWER);

        //handleInputErrors(argMultimap);

        Index deckIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_FLASHCARD).get());

        int index = deckIndex.getZeroBased();
        ObservableList<Deck> deckObservableList = cardiBuddy.getDeckList();
        Deck deck = deckObservableList.get(index);

        if (!logicToUiManager.isInDeck()) {
            throw new ParseException(MESSAGE_NOT_IN_DECK);
        }
        if (!deck.equals(logicToUiManager.getDisplayedDeck())) {
            throw new ParseException(MESSAGE_WRONG_DECK);
        }

        Question modelQuestion = ParserUtil.parseQuestion(argMultimap.getValue(PREFIX_QUESTION).get());
        Answer modelAnswer = ParserUtil.parseAnswer(argMultimap.getValue(PREFIX_ANSWER).get());
        Flashcard flashcard = new Flashcard(deck, modelQuestion, modelAnswer, "");

        return new AddFlashcardCommand(flashcard, logicToUiManager);
    }
}
