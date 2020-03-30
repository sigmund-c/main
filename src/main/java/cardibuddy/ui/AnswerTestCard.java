package cardibuddy.ui;

import cardibuddy.model.flashcard.Answer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * A class for the Test Session's card view.
 * Displays the question for the user to answer.
 *
 * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
 */
public class AnswerTestCard extends UiPart<Region> {

    private static final String FXML = "AnswerTestCard.fxml";

    public final Answer answer;

    @FXML
    private HBox cardPane;
    @FXML
    private Label content;

    public AnswerTestCard(Answer answer) {
        super(FXML);
        this.answer = answer;
        content.setText(answer.toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AnswerTestCard)) {
            return false;
        }

        // state check
        AnswerTestCard card = (AnswerTestCard) other;
        return answer.equals(card.answer)
                && content.getText().equals(card.content.getText());
    }

}
