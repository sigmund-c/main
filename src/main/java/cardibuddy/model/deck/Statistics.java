package cardibuddy.model.deck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import cardibuddy.commons.core.LogsCenter;
import cardibuddy.model.flashcard.Card;
import cardibuddy.model.testsession.TestResult;
import cardibuddy.model.testsession.TestSession;

/**
 * Represents a Statistics report in the cardibuddy application.
 */
public class Statistics {

    // Data fields
    private final List<TestSession> testHistory = new ArrayList<>();
    private final Logger logger = LogsCenter.getLogger(Statistics.class.getName());

    private int cardsAdded;
    private int cardsDeleted;
    private int decksAdded;
    private int decksDeleted;
    private int timesPlayed;
    private int cardsPlayed;
    private double avgCorrectPercentage;
    private double avgTriesToGetCorrect;
    // Keeps a list of correctPercentage for every recorded Deck
    private HashMap<Deck, List<Double>> correctPercentageHistory;
    /**
     * Initialize everything to 0
     */
    public Statistics() {
        cardsAdded = 0;
        cardsDeleted = 0;
        decksAdded = 0;
        decksDeleted = 0;
        timesPlayed = 0;
        cardsPlayed = 0;
        avgCorrectPercentage = 0.0;
        avgTriesToGetCorrect = 0.0;
        correctPercentageHistory = new HashMap<>();

        logger.info("Created Statistics");
    }

    // Edit commands ========================================================================

    public void trackCardAdded() {
        cardsAdded++;
    }

    public void trackCardDeleted() {
        cardsDeleted++;
    }

    public void trackDeckAdded() {
        decksAdded++;
    }

    public void trackDeckDeleted() {
        decksDeleted++;
    }

    /**
     * Stores the history of past TestSessions and increment both timesPlayed and cardsPlayed.
     * @param testSession history of testSession to record
     */
    public void recordHistory(TestSession testSession) {
        HashMap<Card, TestResult> testResults = testSession.getTestResults();

        int totalTries = 0;
        int numCorrect = 0;
        int noOfQuestions = testResults.size();
        for (Map.Entry<Card, TestResult> result : testResults.entrySet()) {
            cardsPlayed++;
            totalTries += result.getValue().getNumTries();
            if (result.getValue().getNumTries() == 1) {
                numCorrect++;
            }
            // change correctPercentage and avgTriesToGetCorrect
        }
        double triesToGetCorrect = ((double) totalTries) / noOfQuestions;
        double correctPercentage = ((double) numCorrect) / noOfQuestions;

        avgTriesToGetCorrect = (avgTriesToGetCorrect * timesPlayed + triesToGetCorrect) / (timesPlayed + 1);
        avgCorrectPercentage = (avgCorrectPercentage * timesPlayed + correctPercentage) / (timesPlayed + 1);

        if (correctPercentageHistory.get(testSession.getDeck()) == null) {
            correctPercentageHistory.put(testSession.getDeck(), new ArrayList<>());
        }
        correctPercentageHistory.get(testSession.getDeck()).add(correctPercentage);

        timesPlayed++;
        testHistory.add(testSession);
    }


    // Get commands ========================================================================

    public int getCardsAdded() {
        return cardsAdded;
    }

    public int getCardsDeleted() {
        return cardsDeleted;
    }

    public int getDecksAdded() {
        return decksAdded;
    }

    public int getDecksDeleted() {
        return decksDeleted;
    }

    public int getCardsPlayed() {
        return cardsPlayed;
    }

    public int getTimesPlayed() {
        return timesPlayed;
    }

    public double getAvgCorrectPercentage() {
        return avgCorrectPercentage;
    }

    public double getAvgTriesToGetCorrect() {
        return avgTriesToGetCorrect;
    }

    public HashMap<Deck, List<Double>> getCorrectPercentageHistory() {
        return correctPercentageHistory;
    }

    public Statistics getSessionStatistic(int index) {
        Statistics sessionStatistics = new Statistics();
        sessionStatistics.recordHistory(testHistory.get(index));

        return sessionStatistics;
    }
}
