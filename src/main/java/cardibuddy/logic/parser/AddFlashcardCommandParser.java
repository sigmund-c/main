package cardibuddy.logic.parser;

import static cardibuddy.logic.parser.CliSyntax.PREFIX_ANSWER;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_QUESTION;
import static java.util.Objects.requireNonNull;

import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.AddFlashcardCommand;
import cardibuddy.logic.parser.exceptions.ParseException;
import cardibuddy.model.ReadOnlyCardiBuddy;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Flashcard;
import cardibuddy.model.flashcard.Question;

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
                ArgumentTokenizer.tokenize(args,
                        PREFIX_QUESTION, PREFIX_ANSWER);

        //handleInputErrors(argMultimap);

        Deck deck = logicToUiManager.getDisplayedDeck();

        Question modelQuestion = ParserUtil.parseQuestion(argMultimap.getValue(PREFIX_QUESTION).get());
        Answer modelAnswer = ParserUtil.parseAnswer(argMultimap.getValue(PREFIX_ANSWER).get());
        Flashcard flashcard = new Flashcard(deck, modelQuestion, modelAnswer, "");

        return new AddFlashcardCommand(flashcard, logicToUiManager);
    }

    ///**
    // * Handles wrong input exceptions by user by throwing respective exceptions.
    // * @param argMultimap Multimap of arguments.
    // * @throws ParseException Respective exceptions.
    // */
    //private void handleInputErrors(ArgumentMultimap argMultimap) throws ParseException {
    //    if (arePrefixesPresent(argMultimap, PREFIX_DECK, PREFIX_FLASHCARD)) {
    //        // both PREFIX_DECK and PREFIX_FLASHCARD are present
    //        throw new DeckCannotBeCardException(String.format(MESSAGE_DECK_CANNOT_BE_FLASHCARD
    //                + "\n" + AddCommand.MESSAGE_USAGE));
    //
    //    } else if (arePrefixesPresent(argMultimap, PREFIX_DECK, PREFIX_QUESTION, PREFIX_ANSWER)
    //            | arePrefixesPresent(argMultimap, PREFIX_DECK, PREFIX_QUESTION)
    //            | arePrefixesPresent(argMultimap, PREFIX_DECK, PREFIX_ANSWER)) {
    //        //trying to add a deck with a question and/or an answer
    //        throw new InvalidDeckException(String.format(MESSAGE_INVALID_DECK + "\n"
    //                + AddCommand.MESSAGE_ADD_DECK));
    //
    //    } else if (arePrefixesPresent(argMultimap, PREFIX_FLASHCARD)) {
    //        if (!logicToUiManager.isInDeck()) {
    //            throw new NotInDeckException(String.format(MESSAGE_NOT_IN_DECK
    //                    + " You need to open a deck first. \n" + OpenCommand.MESSAGE_USAGE));
    //        }
    //
    //        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_FLASHCARD).get());
    //        if (!title.toString().toLowerCase().equals(logicToUiManager.getOpenedDeck())) {
    //            throw new WrongDeckException(String.format(MESSAGE_WRONG_DECK));
    //        }
    //
    //        if (!arePrefixesPresent(argMultimap, PREFIX_FLASHCARD, PREFIX_QUESTION, PREFIX_ANSWER)) {
    //            throw new InvalidFlashcardException(String.format(MESSAGE_INVALID_FLASHCARD + "\n"
    //                    + AddCommand.MESSAGE_ADD_FLASHCARD));
    //        }
    //
    //        if (arePrefixesPresent(argMultimap, PREFIX_FLASHCARD, PREFIX_TAG)) {
    //            throw new InvalidFlashcardException(String.format(MESSAGE_INVALID_FLASHCARD + "\n"
    //                    + AddCommand.MESSAGE_ADD_FLASHCARD));
    //        }
    //    }
    //}
}
