package cardibuddy.ui;

import cardibuddy.model.flashcard.Card;
import cardibuddy.model.flashcard.Imagecard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * An UI component that displays information of a {@code Flashcard}.
 */
public class ImagecardCard extends CardUi {

    private static final String FXML = "ImagecardListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Imagecard flashcard;

    @FXML
    private HBox cardPane;
    @FXML
    private VBox whitePane;
    @FXML
    private Label question;
    @FXML
    private Label answer;
    @FXML
    private Label id;
    @FXML
    private ImageView imageView;
    @FXML
    private FlowPane tags;

    public ImagecardCard(Card card, int displayedIndex) {
        super(FXML);
        Imagecard flashcard = new Imagecard(card.getDeck(), card.getQuestion(), card.getAnswer(), card.getPath());
        this.flashcard = flashcard;
        id.setText(displayedIndex + ". ");
        question.setText(flashcard.getQuestion().toString());
        question.setWrapText(true);
        imageView.setImage(new Image(card.getPath()));
        imageView.setFitWidth(360);
        imageView.setPreserveRatio(true);
        answer.setText(flashcard.getAnswer().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ImagecardCard)) {
            return false;
        }

        // state check
        ImagecardCard card = (ImagecardCard) other;
        return id.getText().equals(card.id.getText())
                && flashcard.equals(card.flashcard);
    }
}
