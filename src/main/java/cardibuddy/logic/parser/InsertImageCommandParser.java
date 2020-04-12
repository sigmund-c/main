package cardibuddy.logic.parser;

import static cardibuddy.commons.core.Messages.MESSAGE_NOT_IN_DECK;
import static cardibuddy.commons.core.Messages.MESSAGE_WRONG_DECK;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_ANSWER;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_FLASHCARD;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_PATH;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_QUESTION;
import static java.util.Objects.requireNonNull;

import cardibuddy.commons.core.index.Index;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.AddCardCommand;
import cardibuddy.logic.commands.AddImagecardCommand;
import cardibuddy.logic.parser.exceptions.ParseException;
import cardibuddy.model.ReadOnlyCardiBuddy;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Imagecard;
import cardibuddy.model.flashcard.Question;
import javafx.collections.ObservableList;

/**
 * Parses input arguments and creates a new AddCardCommand object.
 */
public class InsertImageCommandParser implements Parser<AddCardCommand> {

    private LogicToUiManager logicToUiManager;
    private ReadOnlyCardiBuddy cardiBuddy;

    public InsertImageCommandParser(ReadOnlyCardiBuddy cardiBuddy, LogicToUiManager logicToUiManager) {
        this.logicToUiManager = logicToUiManager;
        this.cardiBuddy = cardiBuddy;
    }

    @Override
    public AddImagecardCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PATH, PREFIX_FLASHCARD,
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

        String path = argMultimap.getValue(PREFIX_PATH).get();
        Question modelQuestion = ParserUtil.parseQuestion(argMultimap.getValue(PREFIX_QUESTION).get());
        Answer modelAnswer = ParserUtil.parseAnswer(argMultimap.getValue(PREFIX_ANSWER).get());
        Imagecard imagecard = new Imagecard(deck, modelQuestion, modelAnswer, path);

        return new AddImagecardCommand(imagecard, logicToUiManager);
    }
}
