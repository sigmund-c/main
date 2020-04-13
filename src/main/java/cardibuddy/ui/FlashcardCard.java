package cardibuddy.ui;

import java.util.List;

import cardibuddy.model.flashcard.Card;
import cardibuddy.model.flashcard.Flashcard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

/**
 * An UI component that displays information of a {@code Flashcard}.
 */
public class FlashcardCard extends CardUi {

    private static final String FXML = "FlashcardListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Flashcard flashcard;

    @FXML
    private HBox cardPane;
    @FXML
    private Label question;
    @FXML
    private Label answer;
    @FXML
    private Label id;
    @FXML
    private FlowPane tags;

    public FlashcardCard(Card card, int displayedIndex) {
        super(FXML);
        Flashcard flashcard = new Flashcard(card.getDeck(), card.getQuestion(), card.getAnswer(), "");
        this.flashcard = flashcard;
        id.setText(displayedIndex + ". ");
        question.setText(flashcard.getQuestion().toString());
        question.setWrapText(true);
        if (flashcard.getAnswer().getAnswerList() != null) {
            List answerList = flashcard.getAnswer().getAnswerList();
            String ans = answerList.get(0) + "\n" + answerList.get(1)
                    + "\n" + answerList.get(2) + "\n" + flashcard.getAnswer().toString().charAt(0);
            answer.setText(ans);
        } else {
            answer.setText(flashcard.getAnswer().toString());
        }
        answer.setWrapText(true);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FlashcardCard)) {
            return false;
        }

        // state check
        FlashcardCard card = (FlashcardCard) other;
        return id.getText().equals(card.id.getText())
                && flashcard.equals(card.flashcard);
    }
}
