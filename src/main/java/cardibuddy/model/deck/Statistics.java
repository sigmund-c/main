package cardibuddy.model.deck;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import cardibuddy.commons.core.LogsCenter;
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
    private int timesPlayed;
    private int cardsPlayed;
    private double correctPercentage;
    private double avgTriesToGetCorrect;
    private List<Double> correctPercentageHistory;

    /**
     * Initialize everything to 0
     */
    public Statistics() {
        cardsAdded = 0;
        cardsDeleted = 0;
        timesPlayed = 0;
        cardsPlayed = 0;
        correctPercentage = 0.0;
        avgTriesToGetCorrect = 0.0;
        correctPercentageHistory = new ArrayList<>();

        logger.info("Created Statistics");
    }

    // Edit commands ========================================================================

    public void trackCardAdded() {
        cardsAdded++;
    }

    public void trackCardDeleted() {
        cardsDeleted++;
    }

    /**
     * Stores the history of past TestSessions and increment both timesPlayed and cardsPlayed.
     * @param testSession history of testSession to record
     */
    public void recordHistory(TestSession testSession) {
        timesPlayed++;
        testHistory.add(testSession);

        //TODO: Finish tracking functionality after testSession is done
        /*
        HashMap<Flashcard, TestResult> testResults = testSession.getResults();

        for (Map.Entry<Flashcard, TestResult> result : testResults.entrySet()) {
            cardsPlayed++;
            // change correctPercentage and avgTriesToGetCorrect
        }*/
    }


    // Get commands ========================================================================

    public int getCardsAdded() {
        return cardsAdded;
    }

    public int getCardsDeleted() {
        return cardsDeleted;
    }

    public int getTimesPlayed() {
        return timesPlayed;
    }

    public double getCorrectPercentage() {
        return correctPercentage;
    }

    public double getAvgTriesToGetCorrect() {
        return avgTriesToGetCorrect;
    }

    public List<Double> getCorrectPercentageHistory() {
        return correctPercentageHistory;
    }
}
