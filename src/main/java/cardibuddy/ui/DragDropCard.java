package cardibuddy.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that for users to drag and drop images.
 */
public class DragDropCard extends UiPart<Region> {

    private static final String FXML = "DragAndDropCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @javafx.fxml.FXML
    private HBox cardPane;


    @javafx.fxml.FXML
    private ImageView imageView;


    public DragDropCard() {
        super(FXML);
        imageView = new ImageView();
    }

    public void setImage(Image image) {
        imageView.setImage(image);
    }

    public void setCache(boolean bool) {
        imageView.setCache(bool);
    }
}
