package cardibuddy.ui;

import java.util.Comparator;

import cardibuddy.model.deck.Deck;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code deck}.
 */
public class DeckCard extends UiPart<Region> {

    private static final String FXML = "DeckListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Deck deck;
    private MainWindow mainWindow;

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private FlowPane tags;


    public DeckCard(Deck deck, int displayedIndex) {
        super(FXML);
        this.deck = deck;
        id.setText(displayedIndex + ". ");
        title.setText(deck.getTitle().toString());
        deck.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Opens selected deck.
     */
    @FXML
    public void openDeck() {

        //resultDisplay.setFeedbackToUser("Selected deck is opened");
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeckCard)) {
            return false;
        }

        // state check
        DeckCard card = (DeckCard) other;
        return id.getText().equals(card.id.getText())
                && deck.equals(card.deck);
    }
}
