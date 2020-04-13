package cardibuddy.ui;

import java.util.logging.Logger;

import cardibuddy.commons.core.LogsCenter;
import cardibuddy.model.flashcard.Card;
import cardibuddy.model.flashcard.CardType;
import cardibuddy.model.flashcard.Flashcard;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of flashcards.
 */
public class FlashcardListPanel extends UiPart<Region> {
    private static final String FXML = "FlashcardListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FlashcardListPanel.class);

    @FXML
    private ListView<Card> flashcardListView;

    public FlashcardListPanel(ObservableList<Card> flashcardList) {
        super(FXML);
        flashcardListView.setItems(flashcardList);
        flashcardListView.setCellFactory(listView -> new FlashcardListViewCell());
    }

    public FlashcardListPanel() {
        super(FXML);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Flashcard} using a {@code FlashcardCard}
     */
    class FlashcardListViewCell extends ListCell<Card> {
        @Override
        protected void updateItem(Card flashcard, boolean empty) {
            super.updateItem(flashcard, empty);

            if (empty || flashcard == null) {
                setGraphic(null);
                setText(null);
            } else if (flashcard.getCardType() == CardType.FLASHCARD) {
                FlashcardCard card = new FlashcardCard(flashcard, getIndex() + 1);
                setGraphic(card.getRoot());
            } else {
                try {
                    ImagecardCard card = new ImagecardCard(flashcard, getIndex() + 1);
                    setGraphic(card.getRoot());
                } catch (IllegalArgumentException exception) {
                    Flashcard newCard = new Flashcard(flashcard.getDeck(), flashcard.getQuestion(),
                            flashcard.getAnswer(), "");
                    FlashcardCard newDisplayCard = new FlashcardCard(newCard, getIndex() + 1);
                    setGraphic(newDisplayCard.getRoot());
                    logger.info("Illegal values found in image path:" + exception.getMessage());
                }
            }
        }
    }
}
