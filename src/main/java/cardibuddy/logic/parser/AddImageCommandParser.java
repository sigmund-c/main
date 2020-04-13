package cardibuddy.logic.parser;

import static cardibuddy.logic.parser.CliSyntax.PREFIX_ANSWER;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_PATH;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_QUESTION;
import static java.util.Objects.requireNonNull;

import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.AddCardCommand;
import cardibuddy.logic.commands.AddImageCardCommand;
import cardibuddy.logic.parser.exceptions.ParseException;
import cardibuddy.model.ReadOnlyCardiBuddy;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Imagecard;
import cardibuddy.model.flashcard.Question;

/**
 * Parses input arguments and creates a new AddCardCommand object.
 */
public class AddImageCommandParser implements Parser<AddCardCommand> {

    private LogicToUiManager logicToUiManager;
    private ReadOnlyCardiBuddy cardiBuddy;

    public AddImageCommandParser(ReadOnlyCardiBuddy cardiBuddy, LogicToUiManager logicToUiManager) {
        this.logicToUiManager = logicToUiManager;
        this.cardiBuddy = cardiBuddy;
    }

    @Override
    public AddImageCardCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PATH,
                        PREFIX_QUESTION, PREFIX_ANSWER);

        //handleInputErrors(argMultimap);

        Deck deck = logicToUiManager.getDisplayedDeck();

        String path = argMultimap.getValue(PREFIX_PATH).get();
        Question modelQuestion = ParserUtil.parseQuestion(argMultimap.getValue(PREFIX_QUESTION).get());
        Answer modelAnswer = ParserUtil.parseAnswer(argMultimap.getValue(PREFIX_ANSWER).get());
        Imagecard imagecard = new Imagecard(deck, modelQuestion, modelAnswer, path);

        return new AddImageCardCommand(imagecard, logicToUiManager);
    }
}
