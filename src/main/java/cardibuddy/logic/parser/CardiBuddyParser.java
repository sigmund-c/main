package cardibuddy.logic.parser;

import static cardibuddy.commons.core.Messages.MESSAGE_DECK_OR_FLASHCARD_PREFIX;
import static cardibuddy.commons.core.Messages.MESSAGE_INCOMPLETE_COMMAND;
import static cardibuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static cardibuddy.commons.core.Messages.MESSAGE_INVALID_TWO_WORD_COMMAND;
import static cardibuddy.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cardibuddy.logic.LogicToUiManager;
import cardibuddy.logic.commands.AddCommand;
import cardibuddy.logic.commands.AddDeckCommand;
import cardibuddy.logic.commands.AddFlashcardCommand;
import cardibuddy.logic.commands.AddImageCardCommand;
import cardibuddy.logic.commands.ClearCommand;
import cardibuddy.logic.commands.Command;
import cardibuddy.logic.commands.DeleteCardCommand;
import cardibuddy.logic.commands.DeleteCommand;
import cardibuddy.logic.commands.DeleteDeckCommand;
import cardibuddy.logic.commands.EditCardCommand;
import cardibuddy.logic.commands.EditCommand;
import cardibuddy.logic.commands.EditDeckCommand;
import cardibuddy.logic.commands.ExitCommand;
import cardibuddy.logic.commands.FilterCommand;
import cardibuddy.logic.commands.HelpCommand;
import cardibuddy.logic.commands.HistoryCommand;
import cardibuddy.logic.commands.ListCommand;
import cardibuddy.logic.commands.OpenCommand;
import cardibuddy.logic.commands.RedoCommand;
import cardibuddy.logic.commands.SearchCardCommand;
import cardibuddy.logic.commands.SearchCommand;
import cardibuddy.logic.commands.SearchDeckCommand;
import cardibuddy.logic.commands.StatisticsCommand;
import cardibuddy.logic.commands.TestCommand;
import cardibuddy.logic.commands.UndoCommand;
import cardibuddy.logic.commands.testsession.AnswerCommand;
import cardibuddy.logic.commands.testsession.ForceCommand;
import cardibuddy.logic.commands.testsession.NextCommand;
import cardibuddy.logic.commands.testsession.QuitCommand;
import cardibuddy.logic.commands.testsession.SkipCommand;
import cardibuddy.logic.parser.exceptions.ParseException;
import cardibuddy.model.ReadOnlyCardiBuddy;

/**
 * Parses user input.
 */
public class CardiBuddyParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private ReadOnlyCardiBuddy cardiBuddy;
    private LogicToUiManager logicToUiManager;

    public CardiBuddyParser(ReadOnlyCardiBuddy cardiBuddy) {
        this.cardiBuddy = cardiBuddy;
    }

    public void setLogicToUiManager(LogicToUiManager logicToUiManager) {
        this.logicToUiManager = logicToUiManager;
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case OpenCommand.COMMAND_WORD:
            return new OpenCommandParser(logicToUiManager).parse(arguments);

        case AddCommand.COMMAND_WORD:
            try {
                switch (arguments.substring(1, 2)) {

                case AddDeckCommand.COMMAND_WORD:
                    System.out.println(arguments);
                    return new AddDeckCommandParser().parse(arguments);

                case AddFlashcardCommand.COMMAND_WORD:
                    return new AddFlashcardCommandParser(cardiBuddy, logicToUiManager).parse(arguments);

                case AddImageCardCommand.COMMAND_WORD:
                    return new AddImageCommandParser(cardiBuddy, logicToUiManager).parse(arguments);

                default:
                    throw new ParseException(MESSAGE_UNKNOWN_COMMAND);

                }
            } catch (StringIndexOutOfBoundsException e) {
                throw new ParseException(MESSAGE_INCOMPLETE_COMMAND + MESSAGE_DECK_OR_FLASHCARD_PREFIX);
            }

        case AddImageCardCommand.COMMAND_WORD:
            return new AddImageCommandParser(cardiBuddy, logicToUiManager).parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            try {
                switch (arguments.substring(1, 5)) {

                case DeleteDeckCommand.COMMAND_WORD:
                    return new DeleteDeckCommandParser(logicToUiManager).parse(arguments.substring(5));

                case DeleteCardCommand.COMMAND_WORD:
                    return new DeleteCardCommandParser(logicToUiManager).parse(arguments.substring(5));

                default:
                    throw new ParseException(MESSAGE_UNKNOWN_COMMAND + "\n" + MESSAGE_INVALID_TWO_WORD_COMMAND);

                }
            } catch (StringIndexOutOfBoundsException e) {
                throw new ParseException(MESSAGE_INCOMPLETE_COMMAND + MESSAGE_INVALID_TWO_WORD_COMMAND);
            }

        case EditCommand.COMMAND_WORD:
            try {
                switch (arguments.substring(1, 5)) {
                case EditDeckCommand.COMMAND_WORD: //deck
                    return new EditDeckCommandParser().parse(arguments.substring(5));

                case EditCardCommand.COMMAND_WORD: //card
                    return new EditCardCommandParser(logicToUiManager).parse(arguments.substring(5));

                default:
                    throw new ParseException(MESSAGE_UNKNOWN_COMMAND + "\n" + MESSAGE_INVALID_TWO_WORD_COMMAND);
                }
            } catch (StringIndexOutOfBoundsException e) {
                throw new ParseException(MESSAGE_INCOMPLETE_COMMAND + MESSAGE_INVALID_TWO_WORD_COMMAND);
            }

        case TestCommand.COMMAND_WORD: // test session command
            return new TestCommandParser(logicToUiManager).parse(arguments);

        case AnswerCommand.COMMAND_WORD: // test session command
            return new AnswerCommand(logicToUiManager, arguments.trim());

        case NextCommand.COMMAND_WORD: // test session command
            return new NextCommand(logicToUiManager);

        case QuitCommand.COMMAND_WORD: // test session command
            return new QuitCommand(logicToUiManager);

        case ForceCommand.COMMAND_WORD: // test session command
            return new ForceCommand();

        case SkipCommand.COMMAND_WORD:
            return new SkipCommand(logicToUiManager);

        case ClearCommand.COMMAND_WORD:
            logicToUiManager.removeFlashcards();
            return new ClearCommand();

        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);

        case SearchCommand.COMMAND_WORD:
            try {
                switch (arguments.substring(1, 5)) {

                case SearchDeckCommand.COMMAND_WORD:
                    return new SearchDeckCommandParser().parse(arguments.substring(5));

                case SearchCardCommand.COMMAND_WORD:
                    return new SearchCardCommandParser(logicToUiManager).parse(arguments.substring(5));

                default:
                    throw new ParseException(MESSAGE_UNKNOWN_COMMAND + " " + MESSAGE_INVALID_TWO_WORD_COMMAND);

                }
            } catch (StringIndexOutOfBoundsException e) {
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }

        case StatisticsCommand.COMMAND_WORD:
            return new StatisticsCommandParser(logicToUiManager).parse(arguments);

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ListCommand.COMMAND_WORD:
            return new ListCommand(logicToUiManager);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
