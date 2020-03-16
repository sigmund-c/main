package cardibuddy.ui;

import java.util.logging.Logger;

import cardibuddy.commons.core.LogsCenter;
import cardibuddy.model.deck.Deck;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;


/**
 * Panel containing the list of decks.
 */
public class DeckListPanel extends UiPart<Region> {
    private static final String FXML = "DeckListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DeckListPanel.class);

    @FXML
    private ListView<Deck> deckListView;

    public DeckListPanel(ObservableList<Deck> deckList) {
        super(FXML);
        deckListView.setItems(deckList);
        deckListView.setCellFactory(listView -> new DeckListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code deck} using a {@code deckCard}.
     */
    class DeckListViewCell extends ListCell<Deck> {
        @Override
        protected void updateItem(Deck deck, boolean empty) {
            super.updateItem(deck, empty);

            if (empty || deck == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DeckCard(deck, getIndex() + 1).getRoot());
            }
        }
    }

}
