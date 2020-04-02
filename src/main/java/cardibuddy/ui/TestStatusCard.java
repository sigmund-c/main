package cardibuddy.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * A class for the Test Session's status view.
 * Shows the user the number of flashcards left in the {@code testQueue}, as well as an encouraging message.
 * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
 * * As a consequence, UI elements' variable names cannot be set to such keywords
 * * or an exception will be thrown by JavaFX during runtime.
 */
public class TestStatusCard extends UiPart<Region> {

    public static final String MESSAGE_COUNTDOWN = "Flashcards in queue: %s\n";
    public static final String MESSAGE_ENDING_SOON = "Finishing soon hang in there!";
    public static final String MESSAGE_LETS_GO = "Let's get it!";
    private static final String FXML = "TestStatusCard.fxml";


    @FXML
    private HBox cardPane;
    @FXML
    private Label testQueueCountdown;
    @FXML
    private Label messageToUser;

    public TestStatusCard(int testQueueSize) {
        super(FXML);
        testQueueCountdown.setText(String.format(MESSAGE_COUNTDOWN, testQueueSize));
        if (testQueueSize <= 2) {
            messageToUser.setText(MESSAGE_ENDING_SOON);
        } else {
            messageToUser.setText(String.format(MESSAGE_COUNTDOWN, MESSAGE_LETS_GO));
        }
    }
}
