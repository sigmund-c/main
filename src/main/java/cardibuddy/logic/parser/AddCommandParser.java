package cardibuddy.logic.parser;

import static cardibuddy.commons.core.Messages.MESSAGE_DECK_CANNOT_BE_FLASHCARD;
import static cardibuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static cardibuddy.commons.core.Messages.MESSAGE_INVALID_DECK;
import static cardibuddy.commons.core.Messages.MESSAGE_INVALID_FLASHCARD;
import static cardibuddy.commons.core.Messages.MESSAGE_NOT_IN_DECK;
import static cardibuddy.commons.core.Messages.MESSAGE_WRONG_DECK;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_ANSWER;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_DECK;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_FLASHCARD;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_QUESTION;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.AddCommand;
import cardibuddy.logic.commands.OpenCommand;
import cardibuddy.logic.parser.exceptions.ParseException;
import cardibuddy.model.ReadOnlyCardiBuddy;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Title;
import cardibuddy.model.deck.exceptions.DeckCannotBeCardException;
import cardibuddy.model.deck.exceptions.InvalidDeckException;
import cardibuddy.model.deck.exceptions.NotInDeckException;
import cardibuddy.model.deck.exceptions.WrongDeckException;
import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Flashcard;
import cardibuddy.model.flashcard.Question;
import cardibuddy.model.flashcard.exceptions.InvalidFlashcardException;
import cardibuddy.model.tag.Tag;
//import javafx.beans.Observable;
import javafx.collections.ObservableList;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {
    private static Object toAdd;
    private ReadOnlyCardiBuddy cardiBuddy;
    private LogicToUiManager logicToUiManager;

    public AddCommandParser(ReadOnlyCardiBuddy cardiBuddy, LogicToUiManager logicToUiManager) {
        this.cardiBuddy = cardiBuddy;
        this.logicToUiManager = logicToUiManager;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DECK, PREFIX_FLASHCARD, PREFIX_TAG,
                        PREFIX_QUESTION, PREFIX_ANSWER);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        handleInputErrors(argMultimap);

        if (argMultimap.containsKey(PREFIX_DECK)) {
            toAdd = addDeck(argMultimap);
            return new AddCommand((Deck) toAdd);
        } else if (argMultimap.containsKey(PREFIX_FLASHCARD)) {
            toAdd = addCard(argMultimap);
            return new AddCommand((Flashcard) toAdd, logicToUiManager);
        }
        return null;
    }

    /**
     * Handles wrong input commands by user by throwing respective exceptions.
     * @param argMultimap Multimap of arguments.
     * @throws ParseException Respective exceptions.
     */
    private void handleInputErrors(ArgumentMultimap argMultimap) throws ParseException {
        if (arePrefixesPresent(argMultimap, PREFIX_DECK, PREFIX_FLASHCARD)) {
            // both PREFIX_DECK and PREFIX_FLASHCARD are present
            throw new DeckCannotBeCardException(String.format(MESSAGE_DECK_CANNOT_BE_FLASHCARD
                    + "\n" + AddCommand.MESSAGE_USAGE));

        } else if (arePrefixesPresent(argMultimap, PREFIX_DECK, PREFIX_QUESTION, PREFIX_ANSWER)
                | arePrefixesPresent(argMultimap, PREFIX_DECK, PREFIX_QUESTION)
                | arePrefixesPresent(argMultimap, PREFIX_DECK, PREFIX_ANSWER)) {
            //trying to add a deck with a question and/or an answer
            throw new InvalidDeckException(String.format(MESSAGE_INVALID_DECK + "\n"
                    + AddCommand.MESSAGE_ADD_DECK));

        } else if (arePrefixesPresent(argMultimap, PREFIX_FLASHCARD)) {
            if (!logicToUiManager.isInDeck()) {
                throw new NotInDeckException(String.format(MESSAGE_NOT_IN_DECK
                        + " You need to open a deck first. \n" + OpenCommand.MESSAGE_USAGE));
            }

            Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_FLASHCARD).get());
            if (!title.toString().toLowerCase().equals(logicToUiManager.getOpenedDeck())) {
                throw new WrongDeckException(String.format(MESSAGE_WRONG_DECK));
            }

            if (!arePrefixesPresent(argMultimap, PREFIX_FLASHCARD, PREFIX_QUESTION, PREFIX_ANSWER)) {
                throw new InvalidFlashcardException(String.format(MESSAGE_INVALID_FLASHCARD + "\n"
                        + AddCommand.MESSAGE_ADD_FLASHCARD));
            }

            if (arePrefixesPresent(argMultimap, PREFIX_FLASHCARD, PREFIX_TAG)) {
                throw new InvalidFlashcardException(String.format(MESSAGE_INVALID_FLASHCARD + "\n"
                        + AddCommand.MESSAGE_ADD_FLASHCARD));
            }
        }
    }

    /**
     * Adds a deck to CardiBuddy.
     * @param argMultimap Multimap of arguments.
     * @return New deck created.
     * @throws ParseException if there is an error parsing the arguments.
     */
    private Deck addDeck(ArgumentMultimap argMultimap) throws ParseException {
        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_DECK).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        List<Flashcard> flashcards = new ArrayList<>();
        return new Deck(title, tagList, flashcards);
    }

    /**
     * Adds a flashcard to a deck in CardiBuddy.
     * @param argMultimap Multimap of arguments.
     * @return New flashcard created.
     * @throws ParseException if there is an error parsing the arguments.
     */
    private Flashcard addCard(ArgumentMultimap argMultimap) throws ParseException {
        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_FLASHCARD).get());
        // Search for the deck with matching title
        Deck deck = new Deck();
        int deckIndex = 0;
        ObservableList<Deck> deckObservableList = cardiBuddy.getDeckList();

        for (int i = 0; i < cardiBuddy.getDeckList().size(); i++) {
            Deck d = deckObservableList.get(i);
            if (d.getTitle().equals(title)) {
                deck = d;
                deckIndex = i;
                break;
            }
        }

        Question modelQuestion = ParserUtil.parseQuestion(argMultimap.getValue(PREFIX_QUESTION).get());
        Answer modelAnswer = ParserUtil.parseAnswer(argMultimap.getValue(PREFIX_ANSWER).get());
        Flashcard flashcard = new Flashcard(deck, modelQuestion, modelAnswer);
        deck.addFlashcard(flashcard);

        logicToUiManager.openFlashcardPanel(deckIndex);

        return flashcard;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
