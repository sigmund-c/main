package cardibuddy.ui;

import cardibuddy.commons.core.LogsCenter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class DragAndDrop extends UiPart<Region> {
    private static final String FXML = "DragAndDropPanel.fxml";
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);

    ImageView imageView;
    StackPane contentPane;
    BorderPane layout;

    public DragAndDrop() {
        super(FXML);
        contentPane = new StackPane();

        contentPane.setOnDragOver(new EventHandler() {
            @Override
            public void handle(final Event event) {
                mouseDragOver((DragEvent)event);
            }
        });

        contentPane.setOnDragDropped(new EventHandler() {
            @Override
            public void handle(final Event event) {
                mouseDragDropped((DragEvent)event);
            }
        });

        contentPane.setOnDragExited(new EventHandler() {
            @Override
            public void handle(final Event event) {
                contentPane.setStyle("-fx-border-color: #C6C6C6;");
            }
        });
    }

    void addImage(Image i, StackPane pane){

        imageView = new ImageView();
        imageView.setImage(i);
        pane.getChildren().add(imageView);
    }

    private void mouseDragDropped(final DragEvent e) {
        final Dragboard db = e.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            // Only get the first file from the list
            final File file = db.getFiles().get(0);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    System.out.println(file.getAbsolutePath());
                    try {
                        if(!contentPane.getChildren().isEmpty()){
                            contentPane.getChildren().remove(0);
                        }
                        Image img = new Image(new FileInputStream(file.getAbsolutePath()));

                        addImage(img, contentPane);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(DragAndDrop.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
        e.setDropCompleted(success);
        e.consume();
    }

    private  void mouseDragOver(final DragEvent e) {
        final Dragboard db = e.getDragboard();

        final boolean isAccepted = db.getFiles().get(0).getName().toLowerCase().endsWith(".png")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".jpeg")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".jpg");

        if (db.hasFiles()) {
            if (isAccepted) {
                contentPane.setStyle("-fx-border-color: red;"
                        + "-fx-border-width: 5;"
                        + "-fx-background-color: #C6C6C6;"
                        + "-fx-border-style: solid;");
                e.acceptTransferModes(TransferMode.COPY);
            }
        } else {
            e.consume();
        }
    }

}
