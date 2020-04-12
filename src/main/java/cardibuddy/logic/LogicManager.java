package cardibuddy.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import cardibuddy.commons.core.GuiSettings;
import cardibuddy.commons.core.LogsCenter;
import cardibuddy.logic.commands.Command;
import cardibuddy.logic.commands.CommandResult;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.logic.parser.CardiBuddyParser;
import cardibuddy.logic.parser.exceptions.ParseException;
import cardibuddy.model.Model;
import cardibuddy.model.ReadOnlyCardiBuddy;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Statistics;
import cardibuddy.model.flashcard.Card;
import cardibuddy.storage.Storage;
import javafx.collections.ObservableList;


/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CardiBuddyParser cardiBuddyParser;
    private final CommandHistory commandHistory;
    private boolean cardiBuddyModified;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        commandHistory = new CommandHistory();
        cardiBuddyParser = new CardiBuddyParser(model.getCardiBuddy());
    }

    public void setLogicToUiManager(LogicToUiManager logicToUiManager) {
        cardiBuddyParser.setLogicToUiManager(logicToUiManager);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        cardiBuddyModified = true;

        CommandResult commandResult;

        try {
            Command command = cardiBuddyParser.parseCommand(commandText);
            commandResult = command.execute(model, commandHistory);
        } finally {
            commandHistory.add(commandText);
        }

        if (cardiBuddyModified) {
            logger.info("CardiBuddy has been modified, saving to file.");
            try {
                storage.saveCardiBuddy(model.getCardiBuddy());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    @Override
    public ReadOnlyCardiBuddy getCardiBuddy() {
        return model.getCardiBuddy();
    }

    @Override
    public ObservableList<Deck> getFilteredDeckList() {
        return model.getFilteredDeckList();
    }

    @Override
    public ObservableList<Card> getFilteredFlashcardList() {
        return model.getFilteredFlashcardList();
    }

    @Override
    public Statistics getStatistics() {
        return model.getStatistics();
    }

    @Override
    public Path getCardiBuddyFilePath() {
        return model.getCardiBuddyFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
