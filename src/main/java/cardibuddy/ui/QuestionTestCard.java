package cardibuddy.ui;

import cardibuddy.model.flashcard.Question;
import cardibuddy.model.testsession.AnswerType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * A class for the Test Session's card view.
 * Displays the question for the user to answer.
 * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
 * As a consequence, UI elements' variable names cannot be set to such keywords
 * or an exception will be thrown by JavaFX during runtime.
 *
 * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
 */
public class QuestionTestCard extends UiPart<Region> {

    private static final String FXML = "QuestionTestCard.fxml";
    private Question question;

    @FXML
    private HBox cardPane;
    @FXML
    private Label content;
    @FXML
    private Label answerHint;

    public QuestionTestCard(Question question, AnswerType answerType) {
        super(FXML);
        this.question = question;
        content.setText("Question:" + "\n" + question.toString() + "\n\n");
        answerHint.setText(answerType.toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof QuestionTestCard)) {
            return false;
        }

        // state check
        QuestionTestCard card = (QuestionTestCard) other;
        return content.getText().equals(card.content.getText())
                && card.question.equals(this.question);
    }

}
