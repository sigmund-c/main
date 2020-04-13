package cardibuddy.ui;

import static cardibuddy.commons.core.Messages.MESSAGE_FLASHCARD_ANSWER;
import static cardibuddy.commons.core.Messages.MESSAGE_USER_ANSWER;

import cardibuddy.model.testsession.TestResult;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * A class for the Test Session's results view.
 * Shows the user the results of their answer, when tested against the flashcard's provided answer.
 * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
 * * As a consequence, UI elements' variable names cannot be set to such keywords
 * * or an exception will be thrown by JavaFX during runtime.
 */
public class ResultCard extends UiPart<Region> {

    private static final String FXML = "ResultCard.fxml";

    public final TestResult testResult;


    @FXML
    private HBox cardPane;
    @FXML
    private Label result;
    @FXML
    private Label userAnswer;
    @FXML
    private Label flashcardAnswer;

    public ResultCard(TestResult testResult) {
        super(FXML);
        this.testResult = testResult;
        result.setText(testResult.getResult().toString());
        userAnswer.setText(String.format(
                MESSAGE_USER_ANSWER,
                testResult.getUserAnswer()));
        flashcardAnswer.setText(String.format(
                MESSAGE_FLASHCARD_ANSWER,
                testResult.getFlashcardAnswer().getCorrectAnswer()));
    }
}
