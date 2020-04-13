package cardibuddy.ui;

import java.io.File;
import java.util.logging.Logger;

import cardibuddy.commons.core.GuiSettings;
import cardibuddy.commons.core.LogsCenter;
import cardibuddy.logic.Logic;
import cardibuddy.logic.commands.CommandResult;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.logic.parser.exceptions.ParseException;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.exceptions.DeckCannotBeCardException;
import cardibuddy.model.deck.exceptions.InvalidDeckException;
import cardibuddy.model.deck.exceptions.NotInDeckException;
import cardibuddy.model.deck.exceptions.WrongDeckException;
import cardibuddy.model.flashcard.Question;
import cardibuddy.model.flashcard.exceptions.InvalidFlashcardException;
import cardibuddy.model.flashcard.exceptions.WrongMcqAnswerTypeException;
import cardibuddy.model.flashcard.exceptions.WrongTfAnswerTypeException;
import cardibuddy.model.testsession.AnswerType;
import cardibuddy.model.testsession.TestResult;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private DeckListPanel deckListPanel;
    private FlashcardListPanel flashcardListPanel;
    private ResultDisplay resultDisplay;
    private StatisticsPanel statisticsPanel;
    private HelpWindow helpWindow;
    private CommandBox commandBox;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane deckListPanelPlaceholder;

    @FXML
    private StackPane flashcardListPanelPlaceholder;

    @FXML
    private StackPane testCardPanelPlaceholder;

    @FXML
    private StackPane dragDropPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane dd;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public void setCommandBox() {
        CommandBox commandBox = new CommandBox(this::executeCommand);
        this.commandBox = commandBox;
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    public void setText(String message) {
        resultDisplay.setFeedbackToUser(message);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window with Decks.
     */
    public void fillInnerPartsWithDecks() {
        deckListPanel = new DeckListPanel(logic.getFilteredDeckList());
        deckListPanelPlaceholder.getChildren().add(deckListPanel.getRoot());

        flashcardListPanel = new FlashcardListPanel();
        //flashcardListPanelPlaceholder.getChildren().add(flashcardListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getCardiBuddyFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        this.commandBox = commandBox;
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Fills up all the placeholders of this window with Flashcards.
     */
    public void fillInnerPartsWithCards(int deckIndex) {
        flashcardListPanel = new FlashcardListPanel(logic.getFilteredDeckList()
                .get(deckIndex)
                .getFlashcardList());
        flashcardListPanelPlaceholder.getChildren().addAll(flashcardListPanel.getRoot());
    }

    /**
     * Updates the flashcard view in the Main Window.
     *
     * @param deck currently opened deck.
     */
    public void updateCards(Deck deck) {
        flashcardListPanel = new FlashcardListPanel(deck.getFilteredFlashcardList());
        flashcardListPanelPlaceholder.getChildren().add(flashcardListPanel.getRoot());
    }

    /**
     * Removes the flashcards in the Main Window.
     */
    public void removeFlashcards() {
        flashcardListPanel = new FlashcardListPanel(null);
        flashcardListPanelPlaceholder.getChildren().add(flashcardListPanel.getRoot());
    }

    /**
     * Fills up the flashcard placeholder with a Statistics report.
     */
    public void fillInnerPartsWithStatistic() {
        statisticsPanel = new StatisticsPanel(logic.getStatistics());
        flashcardListPanelPlaceholder.getChildren().setAll(statisticsPanel.getRoot());
    }

    /**
     * Fills up the flashcard placeholder with a Statistics report of a specific Deck.
     */
    public void fillInnerPartsWithStatistic(int deckIndex) {
        statisticsPanel = new StatisticsPanel(logic.getFilteredDeckList().get(deckIndex).getStatistics());
        flashcardListPanelPlaceholder.getChildren().setAll(statisticsPanel.getRoot());
    }

    /**
     * Fills up the flashcard placeholder with a Statistics report of a specific TestSession of a specific Deck.
     */
    public void fillInnerPartsWithStatistic(int deckIndex, int sessionIndex) {
        statisticsPanel = new StatisticsPanel(
                logic.getFilteredDeckList().get(deckIndex).getStatistics().getSessionStatistic(sessionIndex));
        flashcardListPanelPlaceholder.getChildren().setAll(statisticsPanel.getRoot());
    }

    public void clearFlashcardListPanel() {
        flashcardListPanelPlaceholder.getChildren().clear();
    }

    public void clearDeckListPanel() {
        deckListPanelPlaceholder.getChildren().clear();
    }
    // =================================== TEST SESSION ================================================================

    /**
     * Fills the placeholder of the left panel with the Question of the current flashcard being tested.
     *
     * @param question the question belonging to the current flashcard tested
     */
    public void fillInnerPartsWithQuestion(Question question, AnswerType answerType) {
        clearDeckListPanel();
        clearFlashcardListPanel();
        QuestionTestCard questionCard = new QuestionTestCard(question, answerType);
        deckListPanelPlaceholder.getChildren().add(questionCard.getRoot());
    }

    /**
     * Fills the placeholder of the left panel
     * with the Question of the current flashcard being tested, with the added image.
     *
     * @param question the question belonging to the current flashcard tested
     */
    public void fillInnerPartsWithQuestionAndImage(Question question, AnswerType answerType, String path) {
        clearDeckListPanel();
        clearFlashcardListPanel();
        QuestionTestCardWithImage questionTestCardWithImage = new QuestionTestCardWithImage(question, answerType, path);
        deckListPanelPlaceholder.getChildren().add(questionTestCardWithImage.getRoot());
    }

    /**
     * Replaces the flashcardListPlaceholder with the countdown as well as other test session status messages.
     *
     * @param testQueueSize the number of remaining flashcards in the {@code testQueue}
     */
    public void showTestStatus(int testQueueSize) {
        TestStatusCard testStatusCard = new TestStatusCard(testQueueSize);
        clearFlashcardListPanel();
        flashcardListPanelPlaceholder.getChildren().add(testStatusCard.getRoot());
    }

    /**
     * Fills the placeholder of this window with the Result
     * of the user's answer input against the current flashcard being tested.
     */
    public void fillInnerPartsWithResult(TestResult testResult) {
        clearDeckListPanel();
        ResultCard resultCard = new ResultCard(testResult); // TODO: Create result card + FXML file
        deckListPanelPlaceholder.getChildren().add(resultCard.getRoot());
    }
    // =================================================================================================================

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Inserts an image into the flashcard.
     */
    @FXML
    public void handleInsert() {
        resultDisplay.setFeedbackToUser("Choose an image to insert into your flashcard.");
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imgFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(imgFilter);
        File file = fileChooser.showOpenDialog(primaryStage);

        boolean hasInput = false;

        if (file != null) {
            resultDisplay.setFeedbackToUser("Type in the question and answer to be associated"
                    + " with this image.\nParameters: q/QUESTION a/ANSWER\n\nNote: A deck to be added into"
                    + " should be currently open for the command to work.");
            try {
                EventHandler<KeyEvent> getQa = event -> {
                    if (event.getCode() == KeyCode.ENTER) {
                        String qA = commandBox.getCommandTextField().getText();
                        try {
                            commandBox.getCommandTextField().setText("");
                            commandBox.getCommandExecutor().execute("add p/" + file.toURI().toString()
                                    + " " + qA);
                            event.consume();
                            setCommandBox();
                        } catch (Exception e) {
                            resultDisplay.setFeedbackToUser("Add image command aborted!");
                            setCommandBox();
                        }
                    }
                };
                commandBox.getCommandTextField().setOnKeyPressed(getQa);
            } catch (Exception e) {
                resultDisplay.setFeedbackToUser("Invalid format to add a flashcard!\n"
                        + "Parameters: q/QUESTION a/ANSWER");
            }
        } else {
            resultDisplay.setFeedbackToUser("Please attach a valid file. "
                    + "Only image files ending with .png or .jpg are accepted.");
        }
    }

    /**
     * Opens the test window.
     */
    @FXML
    public void handleTest() {
        resultDisplay.setFeedbackToUser("Enter the index of the deck you want to be tested on.");
        commandBox.getCommandTextField().setOnAction(actionEvent -> {
            String deckNumber = commandBox.getCommandTextField().getText();
            try {
                commandBox.getCommandTextField().setText("");
                actionEvent.consume();
                commandBox.getCommandExecutor().execute("test " + deckNumber);
            } catch (CommandException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public DeckListPanel getdeckListPanel() {
        return deckListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see cardibuddy.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isTest()) {
                handleTest();
            }

            if (commandResult.isInsert()) {
                handleInsert();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (WrongTfAnswerTypeException e) {
            resultDisplay.setFeedbackToUser("This card has a T/F answer! Please type T or F instead of "
                    + e.getMessage() + ".");
            throw e;
        } catch (WrongMcqAnswerTypeException e) {
            resultDisplay.setFeedbackToUser("There needs to be three options for MCQ! Please type in capital letters as"
                    + " well. E.g. A)... B)... C)...");
            throw e;
        } catch (CommandException | ParseException | DeckCannotBeCardException | InvalidDeckException
                | InvalidFlashcardException | NotInDeckException | WrongDeckException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
