package cardibuddy.logic;

import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Flashcard;
import cardibuddy.model.flashcard.Question;
import cardibuddy.model.testsession.Result;
import cardibuddy.model.testsession.TestResult;
import cardibuddy.ui.UiManager;

/**
 * Allows the Commands to control the change in view of the MainWindow panel.
 */
public class LogicToUiManager {

    protected UiManager ui;

    public LogicToUiManager(UiManager ui) {
        this.ui = ui;
    }

    public void openFlashcardPanel(int index) {
        ui.getMainWindow().fillInnerPartsWithCards(index);
    }

    public void showTestQuestion(Question question) {
        ui.getMainWindow().fillInnerPartsWithQuestion(question);
    }

    public void showTestAnswer(Answer answer) {
        ui.getMainWindow().fillInnerPartsWithAnswer(answer); // TODO: add this method
    }

    public void showTestResult(TestResult testResult) {
        ui.getMainWindow().fillInnerPartsWithResult(testResult);
    }

    public void showTestEnd() {
        ui.getMainWindow().fillInnerPartsWithDecks(); // TODO: add this method and create an FXML file for test end
    }

}
